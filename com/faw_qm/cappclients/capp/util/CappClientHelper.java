/** 生成程序 CappClientHelper.java    1.0    2003/09/05
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * SS1 变速箱资源清单一览表 zhaoqiuying 2013-01-23
 * SS2 添加文件服务器传输逻辑 jiahx 2013-12-23
 * SS3 轴齿工艺派生文件 pante 2014-02-19
 * SS4 轴齿工艺文件变更单 pante 2014-03-26
 * SS5 轴齿工艺派生文件中不显示零件材料牌号 pante 2014-05-12
 * SS6 轴齿工艺文件变更单表头调整 pante 2014-05-12
 * SS7 变速箱资源清单一览表模版更改调整。 liunan 2014-7-22
 * SS8 新增“万能量具清单”和“工位器具清单”两个一览表。 liunan 2014-7-28
 * SS9 变速箱资源一览表多页时，总是默认选中第一页和第二页，修改的话会将两页都修改。 liunan 2014-7-30
 * SS10 轴齿中心新需求，增加刀辅具一览表 pante 2014-09-10
 * SS11 长特增加工装明细，设备清单，模具清单。 guoxiaoliang 2014-08-22
 * SS12 轴齿派生文件多页时，无法生成，因为图片不支持clone，改为插入图片方式。 liunan 2015-3-10
 * SS13 平台问题A034-2015-0222。轴齿派生文件类别中去掉开头的轴齿和后缀的一览表明细表 pante 2015-03-20
 * SS14 升级poi到3.6，注释//cell.setEncoding(cell.ENCODING_UTF_16); liunan 2016-4-11
 */
package com.faw_qm.cappclients.capp.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;
import java.io.FileInputStream;

import javax.swing.JOptionPane;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.faw_qm.capp.ejb.standardService.StandardCappService;
import com.faw_qm.capp.model.QMFawTechnicsIfc;
import com.faw_qm.capp.model.QMFawTechnicsInfo;
import com.faw_qm.capp.model.QMProcedureInfo;
import com.faw_qm.capp.model.QMTechnicsIfc;
import com.faw_qm.capp.model.QMTechnicsInfo;
import com.faw_qm.cappclients.capp.controller.TechnicsAction;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.util.ContentClientHelper;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.domain.util.DomainHelper;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.QMRemoteException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.remote.RequestServer;
import com.faw_qm.framework.remote.RequestServerFactory;
import com.faw_qm.framework.remote.ServiceRequestInfo;
import com.faw_qm.framework.remote.StaticMethodRequestInfo;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractContextualValueDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.jview.chrset.SpeCharaterInfo;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.util.PartServiceHelper;
import com.faw_qm.resource.support.model.SpecialCharInfo;
import com.faw_qm.units.util.MeasurementSystemDefaultView;
import com.faw_qm.users.ejb.entity.Actor;
import com.faw_qm.users.ejb.entity.User;
import com.faw_qm.users.model.GroupIfc;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.zczx.util.ZCZXUtil;

//CCBegin SS7
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
//CCEnd SS7

/**
 * <p>Title:为客户端提供服务。 </p>
 * <p>Description:本类提供了一系列静态方法，以方便客户端调用服务 </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 */

public class CappClientHelper
{
    /**代码测试变量*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**
     * 缓存的所有特殊符号
     */
    private static HashMap allSpechar;

    //CCBegin SS2
    static boolean fileVaultUsed = (RemoteProperty.getProperty(
            "registryFileVaultStoreMode", "true")).equals("true");
    //CCEnd SS2
    
    /**
     * 构造函数
     */
    public CappClientHelper()
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
    public static Object useServiceMethod(String serviceName, String methodName,
                                          Class[] paraClass,
                                          Object[] paraObject)
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientHelper.useServiceMethod() begin...");
        }
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
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientHelper.useServiceMethod() end...return : " +
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
    public static BaseValueInfo refresh(BaseValueIfc baseValueIfc)
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientHelper.refresh(1) begin...");
            //参数类型的集合
        }
        Class[] paraClass =
                {BaseValueIfc.class};
        //参数值的集合
        Object[] objs =
                {baseValueIfc};
        BaseValueInfo obj = (BaseValueInfo) useServiceMethod("PersistService",
                "refreshInfo", paraClass, objs);
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientHelper.refresh(1) end...return : " +
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
    public static BaseValueInfo refresh(String bsoID)
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientHelper.refresh(1) begin...");
            //参数类型的集合
        }
        Class[] paraClass =
                {String.class};
        //参数值的集合
        Object[] objs =
                {bsoID};
        BaseValueInfo obj = (BaseValueInfo) useServiceMethod("PersistService",
                "refreshInfo", paraClass, objs);
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientHelper.refresh(1) end...return : " +
                    obj);
        }
        return obj;
    }


    /**
     * 根据输入的工艺卡值对象参数，对数据库进行更新，返回更新后的工艺卡值对象。
     * @param technics QMFawTechnicsInfo 刷新前的工艺值对象
     * @return QMFawTechnicsInfo 刷新前的工艺值对象
     * @throws QMRemoteException
     */
    public static QMFawTechnicsInfo refresh(QMFawTechnicsInfo technics)
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientHelper.refresh(2) begin...");
            //参数类型的集合
        }
        Class[] paraClass =
                {BaseValueIfc.class};
        //参数值的集合
        Object[] objs =
                {technics};
        QMFawTechnicsInfo obj = (QMFawTechnicsInfo) useServiceMethod(
                "PersistService", "refreshInfo", paraClass, objs);
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientHelper.refresh(2) end...return : " +
                    obj);
        }
        return obj;
    }


    /**
     * 删除业务对象。
     * @param info 要删除的业务对象
     * @exception QMRemoteException
     */
    public static void deleteBaseValue(BaseValueIfc info)
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientHelper.deleteBaseValue() begin...");
            //参数类型的集合
        }
        Class[] paraClass =
                {BaseValueIfc.class};
        //参数值的集合
        Object[] objs =
                {info};
        useServiceMethod("PersistService", "deleteValueInfo", paraClass, objs);
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientHelper.deleteBaseValue() end...return is void");
        }
    }


    /**
     * 根据输入的时间戳参数，截取其"年-月-日"部分。
     * @param timestamp 输入的时间戳
     * @return String 字符串格式的"年-月-日"
     */
    public static String getDate(Timestamp timestamp)
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientHelper.getDate() begin...");
        }
        if (timestamp == null)
        {
            return "";
        }
        String timeString = timestamp.toString().trim();
        String resultString = "";
        for (int i = 0; i < timeString.length(); i++)
        {
            if (timeString.substring(i, i + 1).equals(" "))
            {
                break;
            }
            else
            {
                resultString = resultString + timeString.substring(i, i + 1);
            }
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientHelper.getDate() end...return: " +
                    resultString);
        }
        return resultString;
    }


    /**
     * 获取指定类里面的指定属性的最大长度。
     * @param class1 指定的类
     * @param s 指定类的指定属性
     * @return int 最大长度
     */
    public static int getMaxLength(Class class1, String s)
    {
        int i = 0;

        return i;
    }


    /**
     * 判断当前用户是否具有访问权限
     * @param className String
     * @param permission String
     * @return boolean
     * @throws QMRemoteException
     */
    public static Boolean hasPermission(String className, String permission)
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientHelper.hasPermission() begin...");
        }
        RequestServer server = RequestServerFactory.getRequestServer();
        //调用会话服务获得当前用户
        ServiceRequestInfo info1 = new ServiceRequestInfo();
        info1.setServiceName("SessionService");
        info1.setMethodName("getCurUser");
        //参数类型的集合
        Class[] paraClass =
                {};
        info1.setParaClasses(paraClass);
        //参数值的集合
        Object[] objs =
                {};
        info1.setParaValues(objs);
        User user = null;
        try
        {
            user = (User) server.request(info1);
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            throw e;
        }

        //调用访问控制服务判断当前用户对指定的类、指定的域、
        //指定的生命周期状态是否具有指定的访问控制权限
        ServiceRequestInfo info2 = new ServiceRequestInfo();
        info2.setServiceName("AccessControlService");
        info2.setMethodName("hasAccess");
        //参数类型的集合
        Class[] paraClass1 =
                {Actor.class,
                String.class,
                String.class,
                LifeCycleState.class,
                String.class
        };
        info2.setParaClasses(paraClass);
        //参数值的集合
        Object[] objs1 =
                {user, //用户
                className, //类名
                DomainHelper.SYSTEM_DOMAIN, //域
                null, //状态
                permission //访问控制权限
        };
        info2.setParaValues(objs);
        Boolean flag = null;
        try
        {
            flag = (Boolean) server.request(info1);
        }
        catch (QMRemoteException e)
        {
            e.printStackTrace();
            throw e;
        }
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientHelper.hasPermission() end...return: " +
                    flag);
        }
        return flag;
    }


    /**
     *查寻工序，工步使用的资源关联
     *@param  bsoID 对象的bsoId
     *@return 使用资源关联的集合
     */
    public static Collection getUsageResources(String bsoID)
            throws QMRemoteException
    {
        Class[] paraclass =
                {String.class, Boolean.TYPE};
        Object[] paraobj =
                {bsoID, Boolean.FALSE};
        Collection c = (Collection) useServiceMethod("StandardCappService",
                "getUsageResources", paraclass, paraobj);
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
     */
    public static HashMap getSpechar()
    {
        if (allSpechar != null)
        {
            return allSpechar;
        }
        else
        {
            allSpechar = new HashMap();
            HashMap table = null;
            try
            {
                table = (HashMap) useServiceMethod("StandardCappService",
                        "getAllSpechar", null,
                        null);
            }
            catch (QMRemoteException ex)
            {
                ex.printStackTrace();
            }
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
  //CCBegin SS1
    /**
     * 生成派生文档
     * @param techID
     * @param totleType
     * @return
     * 
     * DocInfo
     * @throws IOException 
     * @throws IOException 
     * @throws QMRemoteException 
     */
   public static boolean createExcel(String techID, String[] totleType, HashMap titleMap) throws QMException, IOException
   {
	   //CCBegin SS3
	   String templePath = "";
	   String clientPath = "";
	   if(isBsxGroupUser()){
		   templePath = RemoteProperty.getProperty("bsx_technics_templePath");
		   clientPath = RemoteProperty.getProperty("bsx_schedule_tempPath");
	   }
	   if(getUserFromCompany().equals("zczx")){
		   templePath = RemoteProperty.getProperty("bsx_technics_templePath");
		   clientPath = RemoteProperty.getProperty("bsx_schedule_tempPath");
	   }
	   //CCEnd SS3
	   
	  //CCBegin SS11
	   if(getUserFromCompany().equals("ct")){
		   templePath = RemoteProperty.getProperty("bsx_technics_templePath");
		   clientPath = RemoteProperty.getProperty("bsx_schedule_tempPath");
	   }
	   //CCEnd SS11
	   
	   Class[] paraclass = {String.class, String[].class};
	   Object[] paraobj = {techID, totleType};
	   DocInfo doc =null;
	   HashMap map = (HashMap)useServiceMethod("StandardCappService", "ResouceTotle", paraclass, paraobj);
	   if(map == null || map.size() == 0)
	   {
		   System.out.println("ResouceTotle汇总结果为空!");
		   return false;
	   }
	   HSSFWorkbook wbook = null;

	   for(int i = 0;i < totleType.length;i++)
	   {
		   wbook = null;
		   Vector record = (Vector)map.get(totleType[i]);
		   //CCBegin SS3
		   String num = "";
		   if(isBsxGroupUser()){
			   num = getSerialNum(techID,totleType[i]);
			   titleMap.put("文档编号", num);
		   }
		   if(getUserFromCompany().equals("zczx")){
			   num = "PS-" +titleMap.get("工艺编号").toString()+"-"+totleType[i]+"-"+titleMap.get("版次").toString();
			   titleMap.put("文档编号", num);
		   }
		 //CCEnd SS3
		 //CCBegin SS11
		   if(getUserFromCompany().equals("ct")){
			   num = "PS-" +titleMap.get("工艺编号").toString()+"-"+totleType[i]+"-"+titleMap.get("版次").toString();
			   titleMap.put("文档编号", num);
		   }
		   
		   //CCEnd SS11
		   if(totleType[i].equals("夹具明细表"))
		   {
			   wbook = JJMXB(templePath, record, titleMap);
		   }else if(totleType[i].equals("万能工具清单"))
		   {
			   //CCBegin SS8
			   //wbook = WNGJQD(templePath, record, titleMap);
			   wbook = WNGJQD(templePath, record, titleMap, totleType[i]);
                
       }else if(totleType[i].equals("万能量具清单"))
       {
       	 wbook = WNGJQD(templePath, record, titleMap, totleType[i]);
       }else if(totleType[i].equals("工位器具清单"))
       {
       	 wbook = WNGJQD(templePath, record, titleMap, totleType[i]);
       	 //CCEnd SS8
		   }else if(totleType[i].equals("设备清单"))
		   {
			   //                Vector record = (Vector)map.get(totleType[i]);
			   wbook = SBQD(templePath, record, titleMap);

		   }else if(totleType[i].equals("磨具一览表"))
		   {
			   //                Vector record = (Vector)map.get(totleType[i]);
			   wbook = MJ(templePath, record, titleMap);

		   }else if(totleType[i].equals("刀具一览表"))
		   {
			   //                Vector record = (Vector)map.get(totleType[i]);
			   wbook = DJ(templePath, record, titleMap,totleType[i]);

		   }else if(totleType[i].equals("夹辅具一览表"))
		   {
			   //                Vector record = (Vector)map.get(totleType[i]);
			   //CCBegin SS7
			   //wbook = DJ(templePath, record, titleMap,totleType[i]);
			   wbook = JJMXB(templePath, record, titleMap);
			   //CCEnd SS7

		   }else if(totleType[i].equals("装配工具一览表"))
		   {
			   //                Vector record = (Vector)map.get(totleType[i]);
			   wbook = DJ(templePath, record, titleMap,totleType[i]);
		   }
		   //CCBegin SS3
		   else if(totleType[i].equals("轴齿磨具一览表"))
		   {
		   	//CCBegin SS5
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }else if(totleType[i].equals("轴齿刀具一览表"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }
		   //		   CCBegin SS10
		   else if(totleType[i].equals("轴齿刀辅具一览表"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }
//		   CCEnd SS10
		   else if(totleType[i].equals("轴齿夹辅具一览表"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }else if(totleType[i].equals("轴齿量具一览表"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }else if(totleType[i].equals("轴齿检辅具一览表"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }
		   else if(totleType[i].equals("轴齿夹具明细表"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }
		   else if(totleType[i].equals("轴齿万能量具明细表"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }
		   else if(totleType[i].equals("轴齿万能工具明细表"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }
		   else if(totleType[i].equals("轴齿检具明细表"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
			   //CCEnd SS5
		   }else if(totleType[i].equals("轴齿设备清单"))
		   {
			   wbook = SB(templePath, record, titleMap);
		   }
		   //CCEnd SS3
		   	//CCBegin SS11
		   else if(totleType[i].equals("长特工装明细表"))
		   {
			   wbook = ctToolMingXi(templePath, record, titleMap);
		   }else if(totleType[i].equals("长特设备清单"))
		   {
			   wbook = ctEqList(templePath, record, titleMap);
			   
		   }else if(totleType[i].equals("长特模具清单"))
		   {
			   wbook = ctMoJuList(templePath, record, titleMap);
		   }
		   
		   //CCEnd SS11
		   if(wbook != null)
		   {
			   // 检测路径是否存在，不存在则创建
			   String filePName = clientPath + "/"+totleType[i] + ".xls";
			   System.out.println("filePName======"+filePName);
			   File file = new File(clientPath);
			   // 如果文件夹不存在则创建
			   if(!file.exists())
			   {
				   file.mkdirs();
			   }
			   file = new File(filePName);
			   if(file.exists())
			   {
				   file.delete();
			   }

			   file.createNewFile();
			   // 创建派生文件
			   FileOutputStream cout = new FileOutputStream(filePName);
			   wbook.write(cout);
			   cout.close();

			   //CCBegin SS2
			   if(fileVaultUsed)
			   {
				   ContentClientHelper helper = new ContentClientHelper();
				   ApplicationDataInfo appDataInfo = helper.requestUpload(file);

				   Class[] para= {String.class, String.class,ApplicationDataInfo.class,String.class};
				   Object[] obj = {techID, totleType[i],appDataInfo,num};
				   doc = (DocInfo)useServiceMethod("StandardCappService", "createFile", para, obj);
			   }
			   else
			   {
				   // 构造ApplicationDataInfo
				   ApplicationDataInfo appDataInfo = new ApplicationDataInfo();
				   FileInputStream input = null;
				   byte[] filebyte = null;

				   try
				   {
					   input = new FileInputStream(file);
				   }catch(FileNotFoundException e)
				   {
					   JOptionPane.showConfirmDialog(null, file+"正被另一应用程序使用，请关闭文件","提示", JOptionPane.INFORMATION_MESSAGE);
					   e.printStackTrace();
				   }

				   appDataInfo.setFileName(file.getName());
				   appDataInfo.setUploadPath(file.getPath());

				   ByteArrayOutputStream out = new ByteArrayOutputStream();
				   //  一次读取1024字节 
				   byte[] bytes = new byte[1024];
				   while(input.read(bytes)!=-1){
					   out.write(bytes);
					   out.flush();
				   }

				   input.close();
				   out.close();
				   // 生成文件流b
				   filebyte = out.toByteArray();
				   appDataInfo.setFileSize(filebyte.length);
				   // 创建文档
				   Class[] para= {String.class, String.class,ApplicationDataInfo.class,byte[].class, String.class};
				   Object[] obj = {techID, totleType[i],appDataInfo,filebyte,num};
				   doc = (DocInfo)useServiceMethod("StandardCappService", "createFile", para, obj);
			   }
			   //CCEnd SS2
		   }
	   }
	   if(doc == null)
		   return false;
	   return true;
   }
   public static String getSerialNum(String techID, String totleType) throws QMException
   {
       Class[] para= {String.class, String.class};
       Object[] obj = {techID, totleType};
       String docNum = (String)useServiceMethod("StandardCappService", "getDocNum", para, obj);
       return docNum;
   }
   /**
    * 刀具、装配工具一览表
    * @param templePath
    * @param record
    * @param map
    * @return
    * @throws IOException
    * 
    * HSSFWorkbook
    */
      public static HSSFWorkbook DJ(String templePath, Vector record, HashMap map,String type) throws IOException
       {
           String url = templePath + "/" + type+".xls";
           InputStream stream = null;
           if(record == null || record.size() == 0)
               return null;
           URL aurl = new URL(url);
           stream = aurl.openStream();

           POIFSFileSystem fs = null;
           HSSFWorkbook wb = null;

           fs = new POIFSFileSystem(stream);
           wb = new HSSFWorkbook(fs);
           int psize = record.size();
           int pageNumber = 0;
           pageNumber = (psize % 17 == 0) ? (psize / 17) : (1 + (psize / 17));
           HSSFSheet sheet = wb.getSheetAt(0); // 获得第一个页面
           sheet.setSelected(true);
           HSSFRow row;
           HSSFCell cell; // 第1行第0列
           // 表头

           row = sheet.getRow(2);
           // 零部件编号
           //CCBegin SS7
           //cell = row.getCell((short)6);
           cell = row.getCell((short)9);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("零件号") != null)
           {
               cell.setCellValue(map.get("零件号").toString());
               System.out.println("cappclienthelp中 零件号 = " + map.get("零件号").toString());
           }
           // 零部件名称
           //cell = row.getCell((short)8);
           cell = row.getCell((short)12);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("零件名称") != null)
               cell.setCellValue(map.get("零件名称").toString());
           // 车间
           /*cell = row.getCell((short)9);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("车间") != null)
               cell.setCellValue(map.get("车间").toString());*/
           
           row = sheet.getRow(3);
           // 第号
              cell = row.getCell((short)3);
              //cell.setEncoding(cell.ENCODING_UTF_16);
              if(map.get("文档编号") != null)
                  cell.setCellValue("第  " + map.get("文档编号").toString() + "  号");
              
           row = sheet.getRow(4);
           // 共页
           cell = row.getCell((short)12);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("共  " + pageNumber + "  页");
           
           row = sheet.getRow(5);
           // 版次
           cell = row.getCell((short)3);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("版次") != null)
               cell.setCellValue(map.get("版次").toString());
           // 车间
           cell = row.getCell((short)5);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("车间") != null)
               cell.setCellValue(map.get("车间").toString());
           // 产品平台
           cell = row.getCell((short)7);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("产品平台") != null)
               cell.setCellValue(map.get("产品平台").toString());
           // 每车数量
           cell = row.getCell((short)9);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("每车数量") != null)
               cell.setCellValue(map.get("每车数量").toString());
           // 共页
           /*cell = row.getCell((short)9);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("共  " + pageNumber + "  页");*/

           HSSFSheet sheet1 = null; // 缓存空页面
            //if(pageNumber > 1)
            //sheet1 = wb.cloneSheet(0); // 缓存空页面


           
           // 处理汇总结果
           int begin = 0;
           int end = 17;
           String[] line;
           for(int i = 1;i <= pageNumber;i++)
           {
System.out.println(i+"==========="+pageNumber);
               if(pageNumber > 1 && i < pageNumber)
                   sheet1 = wb.cloneSheet(i - 1);// 缓存空页面
               /*if(i==1)
               {
               }
               else if(pageNumber > 1)
               {
               	 sheet = sheet1;
               	 wb.setSheetName(i - 1, "Sheet" + i);
               	 if(i < pageNumber)
               	 {
               	 	 sheet1 = wb.cloneSheet(i - 1);// 缓存空页面
               	 }
               }*/
               end = i * 17;
               //row = sheet.getRow(4);
               row = sheet.getRow(5);
               //cell = row.getCell((short)9);
               cell = row.getCell((short)12);
               //cell.setEncoding(cell.ENCODING_UTF_16);
               cell.setCellValue("第  " + i + "  页");
               
               
           //添加图标  由于模版带图片会出现异常，因此采用添加图片方法处理
            String urlroot = "http://" +
               RemoteProperty.getProperty("server.hostName", "") +
               RemoteProperty.getProperty("routeListTemplate",
                                          "/PhosphorPDM/template/");
           ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
           try
           {
           	 BufferedImage bufferImg = ImageIO.read(new URL(urlroot+"jf.jpg"));
           	 ImageIO.write(bufferImg,"jpg",byteArrayOut);
           }
           catch (IOException e) 
           {
             e.printStackTrace();
           }          
           HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
           /* 参数　　说明  第1个单元格 指的是图片左上点，第2个单元格指的是图片右下点
              dx1 	第1个单元格中x轴的偏移量  相对比率
              dy1 	第1个单元格中y轴的偏移量  相对比率
              dx2 	第2个单元格中x轴的偏移量  相对比率
              dy2 	第2个单元格中y轴的偏移量  相对比率
              col1 	第1个单元格的列号
              row1 	第1个单元格的行号
              col2 	第2个单元格的列号
              row2 	第2个单元格的行号
           */
           HSSFClientAnchor anchor = new HSSFClientAnchor(0,125,650,125,(short) 4,1,(short)4,2); 
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
           //图片添加结束
           
               
               for(int j = begin;j < end;j++)
               {
                   if(j >= record.size())
                       break;
                   line = (String[])record.get(j);
                   //row = sheet.getRow((j - begin) + 7);
                   row = sheet.getRow((j - begin) + 8);
                   for(int k = 0;k < line.length;k++)
                   {
                       //cell = row.getCell((short)k);
                       if(k==0||k==1)
                       {  
                       	   cell = row.getCell((short)k);
                       }
                       else if(k==9)
                       {
                       	   cell = row.getCell((short)13);
                       }
                       else
                       {
                       	   cell = row.getCell((short)(k+1));
                       }
           //CCEnd SS7
                       //cell.setEncoding(cell.ENCODING_UTF_16);
                    //System.out.println("cell=="+cell.getCellNum()+"  and   line["+k+"]==="+line[k]);
                       cell.setCellValue(line[k]);
                   }
               }
               begin = end;
               sheet = sheet1;
               //CCBegin SS9
               if(sheet!=null)
               sheet.setSelected(false);
               //CCEnd SS9
               wb.setSheetName(i - 1, "Sheet" + i);

           }
           return wb;

       }
      //CCBegin SS3
      //CCBegin SS5
      public static HSSFWorkbook YLB(String templePath, Vector record, HashMap map,String type,String techID) throws IOException
      {
      	//CCEnd SS5
      	//CCBegin SS12
      	HSSFWorkbook wb = null;
      	try
      	{
      	//CCEnd SS12
    	  String url = null;
    	  if(type.contains("一览表"))
    		  url = templePath + "/轴齿工具一览表.xls";
    	  if(type.contains("明细表"))
    		  url = templePath + "/轴齿工具明细表.xls";
    	  InputStream stream = null;
    	  if(record == null || record.size() == 0)
    		  return null;
    	  URL aurl = new URL(url);
    	  stream = aurl.openStream();

    	  POIFSFileSystem fs = null;
    	  //CCBegin SS12
    	  //HSSFWorkbook wb = null;
    	  //CCEnd SS12

    	  fs = new POIFSFileSystem(stream);
    	  wb = new HSSFWorkbook(fs);
    	  int psize = record.size();
    	  int pageNumber = 0;
    	  pageNumber = (psize % 18 == 0) ? (psize / 18) : (1 + (psize / 18));

    	  HSSFSheet sheet = wb.getSheetAt(0); // 获得第一个页面
    	  HSSFRow row;
    	  HSSFCell cell; // 第1行第0列
    	  // 表头

    	  row = sheet.getRow(2);
    	  // 工艺编号
    	  cell = row.getCell((short)2);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("工艺编号") != null)
    	  {
    		  cell.setCellValue("根据   "+map.get("工艺编号").toString()+"   号");
    	  }
    	  // 零部件名称
    	  cell = row.getCell((short)6);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("零件名称") != null){
    		  cell.setCellValue(map.get("零件名称").toString());
    	  }
    	  // 零部件图号
    	  cell = row.getCell((short)8);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("零件图号") != null){
    		  cell.setCellValue(map.get("零件图号").toString());
    	  }
    	  row = sheet.getRow(3);
    	  // 车间
    	  cell = row.getCell((short)2);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("车间") != null){
    		  cell.setCellValue(map.get("车间").toString()+"  车间");
    	  }
    	  // 材料
    	  //CCBegin SS5
    	  String mt = "";
    	  try{
    		  Class[] paraclass1 = {String.class};
    		  Object[] paraobj1 = {techID};
    		  QMTechnicsIfc tech = (QMTechnicsIfc) useServiceMethod("PersistService", "refreshInfo", paraclass1,paraobj1);
    		  Class[] paraclass2 = {QMTechnicsIfc.class};
    		  Object[] paraobj2 = {tech};
    		  QMPartIfc mainPart = (QMPartIfc) useServiceMethod("StandardCappService", "getQMPart", paraclass2,paraobj2);
    		  Class[] paraclass3 = {IBAHolderIfc.class, Object.class, Locale.class, MeasurementSystemDefaultView.class};
    		  Object[] paraobj3 = {mainPart, null, null, null};
    		  IBAHolderIfc iba = (IBAHolderIfc) useServiceMethod("IBAValueService", "refreshAttributeContainer", paraclass3,paraobj3);
    		  DefaultAttributeContainer container = (DefaultAttributeContainer)iba.getAttributeContainer();
    		  if(container == null)
    			  container = new DefaultAttributeContainer();
    		  AbstractValueView[] values = container.getAttributeValues();
    		  
    		  for(int i = 0 ; i < values.length; i++){
    			  AbstractValueView value = values[i];
        	      AttributeDefDefaultView definition = value.getDefinition();
    			  if(definition.getDisplayName().equals("材料牌号")){
    				  mt = ((AbstractContextualValueDefaultView)value).getValueAsString();
    			  }
    		  }
    	  }
    	  catch(Exception e){
    		  e.printStackTrace();
    	  }
    	  cell = row.getCell((short)9);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  cell.setCellValue(mt);
    	  //CCEnd SS5
    	  //          if(map.get("材料") != null){
    	  //        	  cell.setCellValue(map.get("材料").toString());
    	  //        	  System.out.println("材料 = " + map.get("材料").toString());
    	  //          }

    	  row = sheet.getRow(4);
    	  // 类别
    	  cell = row.getCell((short)1);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
//    	  CCBegin SS13
    	  if(type.contains("明细表"))
    		  type = type.replace("明细表", "");
    	  if(type.contains("一览表"))
    		  type = type.replace("一览表", "");
    	  if(type.contains("清单"))
    		  type = type.replace("清单", "");
    	  type = type.replace("轴齿", "");
//    	  CCEnd SS13
    	  cell.setCellValue(type);
    	  // 共页
    	  cell = row.getCell((short)8);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  cell.setCellValue("共  " + pageNumber + "  页");

    	  row = sheet.getRow(5);
    	  // 版次
    	  cell = row.getCell((short)2);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("版次") != null)
    		  cell.setCellValue(map.get("版次").toString());
    	  //编制日期
    	  cell = row.getCell((short)3);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("编制日期") != null)
    		  cell.setCellValue(map.get("编制日期").toString());
    	  //总成型号
    	  cell = row.getCell((short)4);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("总成型号") != null)
    		  cell.setCellValue(map.get("总成型号").toString());
    	  //每车数量
    	  cell = row.getCell((short)6);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("每车数量") != null)
    		  cell.setCellValue(map.get("每车数量").toString());

    	  HSSFSheet sheet1 = null; // 缓存空页面
    	  // if(pageNumber > 1)
    	  // sheet1 = wb.cloneSheet(0); // 缓存空页面

    	  // 处理汇总结果
    	  int begin = 0;
    	  int end = 18;
    	  String[] line;
    	  for(int i = 1;i <= pageNumber;i++)
    	  {
    		  if(pageNumber > 1 && i < pageNumber)
    			  sheet1 = wb.cloneSheet(i - 1);// 缓存空页面
    		  end = i * 17;
    		  row = sheet.getRow(5);
    		  cell = row.getCell((short)8);
    		  //cell.setEncoding(cell.ENCODING_UTF_16);
    		  cell.setCellValue("第  " + i + "  页");
    		  
          //CCBegin SS12
           //添加图标  由于模版带图片会出现异常，因此采用添加图片方法处理
           String urlroot = "http://" +
               RemoteProperty.getProperty("server.hostName", "") +
               RemoteProperty.getProperty("routeListTemplate",
                                          "/PhosphorPDM/template/");
           ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
           ByteArrayOutputStream byteArrayOut1 = new ByteArrayOutputStream();
           try
           {
           	 BufferedImage bufferImg = ImageIO.read(new URL(urlroot+"轴齿中心厂标.jpg"));
           	 ImageIO.write(bufferImg,"jpg",byteArrayOut);
           	 
           	 BufferedImage bufferImg1 = ImageIO.read(new URL(urlroot+"轴齿中心名称.jpg"));
           	 ImageIO.write(bufferImg1,"jpg",byteArrayOut1);
           }
           catch (IOException e) 
           {
             e.printStackTrace();
           }          
           HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
           /* 参数　　说明  第1个单元格 指的是图片左上点，第2个单元格指的是图片右下点
              dx1 	第1个单元格中x轴的偏移量  相对比率
              dy1 	第1个单元格中y轴的偏移量  相对比率
              dx2 	第2个单元格中x轴的偏移量  相对比率
              dy2 	第2个单元格中y轴的偏移量  相对比率
              col1 	第1个单元格的列号
              row1 	第1个单元格的行号
              col2 	第2个单元格的列号
              row2 	第2个单元格的行号
           */
          if(type.contains("一览表"))
          {
           HSSFClientAnchor anchor = new HSSFClientAnchor(360,5,785,240,(short) 0,1,(short)1,2);
           HSSFClientAnchor anchor1 = new HSSFClientAnchor(550,240,500,245,(short) 0,2,(short)1,3);
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
           patriarch.createPicture(anchor1 , wb.addPicture(byteArrayOut1.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
          }
          else if(type.contains("明细表"))
          {
           HSSFClientAnchor anchor = new HSSFClientAnchor(110,80,880,110,(short) 1,1,(short)1,2);
           HSSFClientAnchor anchor1 = new HSSFClientAnchor(0,80,1020,110,(short) 0,1,(short)0,2);
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
           patriarch.createPicture(anchor1 , wb.addPicture(byteArrayOut1.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
          }
           //图片添加结束
          //CCEnd SS12
            
    		  for(int j = begin;j < end;j++)
    		  {
    			  if(j >= record.size())
    				  break;
    			  line = (String[])record.get(j);
    			  row = sheet.getRow((j - begin) + 8);
    			  for(int k = 0;k < line.length;k++)
    			  {
    				  cell = row.getCell((short)k);
    				  //cell.setEncoding(cell.ENCODING_UTF_16);
    				  cell.setCellValue(line[k]);
    			  }
    		  }
    		  begin = end;

    		  sheet = sheet1;
               //CCBegin SS9
               if(sheet!=null)
               sheet.setSelected(false);
               //CCEnd SS9
    		  wb.setSheetName(i - 1, "Sheet" + i);

    	  }
    	  //CCBegin SS12
      }catch(Exception e)
      {
      	e.printStackTrace();
      }
      //CCEnd SS12
    	  return wb;
      }
      
      public static HSSFWorkbook SB(String templePath, Vector record, HashMap titlemap) throws IOException
      {
    	  String url = templePath + "/" + "轴齿设备清单.xls";
    	  InputStream stream = null;
    	  if(record == null || record.size() == 0)
    		  return null;
    	  URL aurl = new URL(url);
    	  stream = aurl.openStream();

    	  POIFSFileSystem fs = null;
    	  HSSFWorkbook wb = null;

    	  fs = new POIFSFileSystem(stream);
    	  wb = new HSSFWorkbook(fs);
    	  int psize = record.size();
    	  int pageNumber = 0;
    	  pageNumber = (psize % 17 == 0) ? (psize / 17) : (1 + (psize / 17));

    	  HSSFSheet sheet = wb.getSheetAt(0); // 获得第一个页面
    	  HSSFRow row;
    	  HSSFCell cell; // 第1行第0列
    	  // 表头

    	  row = sheet.getRow(1);
    	  // 总成型号
    	  cell = row.getCell((short)9);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(titlemap.get("总成型号") != null)
    	  {
    		  cell.setCellValue(titlemap.get("总成型号").toString());
    	  }
    	  row = sheet.getRow(2);
    	  // 零部件编号
    	  cell = row.getCell((short)9);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(titlemap.get("零件图号") != null)
    	  {
    		  cell.setCellValue(titlemap.get("零件图号").toString());
    	  }
    	  //制表日期
    	  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    	  String a1=formatter.format(new Date());
    	  cell = row.getCell((short)10);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  cell.setCellValue(a1);
    	  // 共页
    	  cell = row.getCell((short)11);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  cell.setCellValue("共  " + pageNumber + "  页");
    	  
    	  row = sheet.getRow(3);
    	// 车间
    	  cell = row.getCell((short)2);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(titlemap.get("车间") != null)
    		  cell.setCellValue(titlemap.get("车间").toString());
    	  // 零部件名称
    	  cell = row.getCell((short)9);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(titlemap.get("零件名称") != null)
    		  cell.setCellValue(titlemap.get("零件名称").toString());
    	  
    	  HSSFSheet sheet1 = null; // 缓存空页面
    	  // if(pageNumber > 1)
    	  // sheet1 = wb.cloneSheet(0); // 缓存空页面

    	  // 处理汇总结果
    	  int begin = 0;
    	  int end = 21;
    	  String[] line;
    	  for(int i = 1;i <= pageNumber;i++)
    	  {
    		  if(pageNumber > 1 && i < pageNumber)
    			  sheet1 = wb.cloneSheet(i - 1);// 缓存空页面
    		  end = i * 21;
    		  row = sheet.getRow(1);
    		  cell = row.getCell((short)11);
    		  //cell.setEncoding(cell.ENCODING_UTF_16);
    		  cell.setCellValue("第  " + i + "  页");
    		  
          //CCBegin SS12
           //添加图标  由于模版带图片会出现异常，因此采用添加图片方法处理
           String urlroot = "http://" +
               RemoteProperty.getProperty("server.hostName", "") +
               RemoteProperty.getProperty("routeListTemplate",
                                          "/PhosphorPDM/template/");
           ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
           ByteArrayOutputStream byteArrayOut1 = new ByteArrayOutputStream();
           try
           {
           	 BufferedImage bufferImg = ImageIO.read(new URL(urlroot+"轴齿中心厂标.jpg"));
           	 ImageIO.write(bufferImg,"jpg",byteArrayOut);
           	 
           	 BufferedImage bufferImg1 = ImageIO.read(new URL(urlroot+"轴齿中心名称.jpg"));
           	 ImageIO.write(bufferImg1,"jpg",byteArrayOut1);
           }
           catch (IOException e) 
           {
             e.printStackTrace();
           }          
           HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
           /* 参数　　说明  第1个单元格 指的是图片左上点，第2个单元格指的是图片右下点
              dx1 	第1个单元格中x轴的偏移量  相对比率
              dy1 	第1个单元格中y轴的偏移量  相对比率
              dx2 	第2个单元格中x轴的偏移量  相对比率
              dy2 	第2个单元格中y轴的偏移量  相对比率
              col1 	第1个单元格的列号
              row1 	第1个单元格的行号
              col2 	第2个单元格的列号
              row2 	第2个单元格的行号
           */
           HSSFClientAnchor anchor = new HSSFClientAnchor(30,80,880,180,(short) 1,1,(short)2,2);
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
          
           //图片添加结束
          //CCEnd SS12
          
    		  for(int j = begin;j < end;j++)
    		  {
    			  if(j >= record.size())
    				  break;
    			  line = (String[])record.get(j);
    			  row = sheet.getRow((j - begin) + 6);
    			  for(int k = 0;k < line.length;k++)
    			  {
    				  cell = row.getCell((short)(k+1));
    				  //cell.setEncoding(cell.ENCODING_UTF_16);
    				  cell.setCellValue(line[k]);
    			  }
    		  }
    		  begin = end;

    		  sheet = sheet1;
               //CCBegin SS9
               if(sheet!=null)
               sheet.setSelected(false);
               //CCEnd SS9
    		  wb.setSheetName(i - 1, "Sheet" + i);

    	  }
    	  return wb;
      }
      //CCEnd SS3
   /**
    * ?? 磨具一览表
    * @param templePath
    * @param record
    * @param map
    * @return
    * @throws IOException
    * 
    * HSSFWorkbook
    */
      public static HSSFWorkbook MJ(String templePath, Vector record, HashMap map) throws IOException
       {
           String url = templePath + "/" + "磨具一览表.xls";
           InputStream stream = null;
           if(record == null || record.size() == 0)
               return null;
           URL aurl = new URL(url);
           stream = aurl.openStream();

           POIFSFileSystem fs = null;
           HSSFWorkbook wb = null;

           fs = new POIFSFileSystem(stream);
           wb = new HSSFWorkbook(fs);
           int psize = record.size();
           int pageNumber = 0;
           pageNumber = (psize % 17 == 0) ? (psize / 17) : (1 + (psize / 17));

           HSSFSheet sheet = wb.getSheetAt(0); // 获得第一个页面
           HSSFRow row;
           HSSFCell cell; // 第1行第0列
           // 表头

           row = sheet.getRow(2);
           // 零部件编号
           //CCBegin SS7
           //cell = row.getCell((short)7);
           cell = row.getCell((short)12);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("零件号") != null)
           {
               cell.setCellValue(map.get("零件号").toString());
               System.out.println("cappclienthelp中 零件号 = " + map.get("零件号").toString());
           }
           // 零部件名称
           //cell = row.getCell((short)9);
           cell = row.getCell((short)15);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("零件名称") != null)
               cell.setCellValue(map.get("零件名称").toString());
           // 车间
           /*cell = row.getCell((short)11);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("车间") != null)
               cell.setCellValue(map.get("车间").toString());*/
           
           //row = sheet.getRow(3);
           // 第号
              /*cell = row.getCell((short)3);
              //cell.setEncoding(cell.ENCODING_UTF_16);
              if(map.get("文档编号") != null)
                  cell.setCellValue("第  " + map.get("文档编号").toString() + "  号");*/
              
           row = sheet.getRow(4);
           // 共页
           cell = row.getCell((short)15);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("共  " + pageNumber + "  页");
           
           row = sheet.getRow(5);
           // 版次
           cell = row.getCell((short)0);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("版次") != null)
               cell.setCellValue(map.get("版次").toString());
           // 车间
           cell = row.getCell((short)5);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("车间") != null)
               cell.setCellValue(map.get("车间").toString());
           // 产品平台
           cell = row.getCell((short)9);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("产品平台") != null)
               cell.setCellValue(map.get("产品平台").toString());
           // 每车数量
           cell = row.getCell((short)12);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("每车数量") != null)
               cell.setCellValue(map.get("每车数量").toString());
           // 共页
           /*cell = row.getCell((short)11);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("共  " + pageNumber + "  页");*/

           HSSFSheet sheet1 = null; // 缓存空页面
           // if(pageNumber > 1)
           // sheet1 = wb.cloneSheet(0); // 缓存空页面

           // 处理汇总结果
           int begin = 0;
           int end = 17;
           String[] line;
           for(int i = 1;i <= pageNumber;i++)
           {
               if(pageNumber > 1 && i < pageNumber)
                   sheet1 = wb.cloneSheet(i - 1);// 缓存空页面
               end = i * 17;
               //row = sheet.getRow(4);
               row = sheet.getRow(5);
               //cell = row.getCell((short)11);
               cell = row.getCell((short)15);
               //cell.setEncoding(cell.ENCODING_UTF_16);
               cell.setCellValue("第  " + i + "  页");
               
               
           //添加图标  由于模版带图片会出现异常，因此采用添加图片方法处理
            String urlroot = "http://" +
               RemoteProperty.getProperty("server.hostName", "") +
               RemoteProperty.getProperty("routeListTemplate",
                                          "/PhosphorPDM/template/");
           ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
           try
           {
           	 BufferedImage bufferImg = ImageIO.read(new URL(urlroot+"jf.jpg"));
           	 ImageIO.write(bufferImg,"jpg",byteArrayOut);
           }
           catch (IOException e) 
           {
             e.printStackTrace();
           }          
           HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
           /* 参数　　说明  第1个单元格 指的是图片左上点，第2个单元格指的是图片右下点
              dx1 	第1个单元格中x轴的偏移量  相对比率
              dy1 	第1个单元格中y轴的偏移量  相对比率
              dx2 	第2个单元格中x轴的偏移量  相对比率
              dy2 	第2个单元格中y轴的偏移量  相对比率
              col1 	第1个单元格的列号
              row1 	第1个单元格的行号
              col2 	第2个单元格的列号
              row2 	第2个单元格的行号
           */
           HSSFClientAnchor anchor = new HSSFClientAnchor(700,125,750,125,(short) 5,1,(short)6,2); 
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
           //图片添加结束
           
               for(int j = begin;j < end;j++)
               {
                   if(j >= record.size())
                       break;
                   line = (String[])record.get(j);
                   //row = sheet.getRow((j - begin) + 7);
                   row = sheet.getRow((j - begin) + 8);
                   for(int k = 0;k < line.length;k++)
                   {
                       //cell = row.getCell((short)k);
                       if(k==0||k==1)
                       {  
                       	   cell = row.getCell((short)k);
                       }
                       else
                       {
                       	   cell = row.getCell((short)(k+1));
                       }
           //CCEnd SS7
                       //cell.setEncoding(cell.ENCODING_UTF_16);
                       cell.setCellValue(line[k]);
                   }
               }
               begin = end;

               sheet = sheet1;
               //CCBegin SS9
               if(sheet!=null)
               sheet.setSelected(false);
               //CCEnd SS9
               wb.setSheetName(i - 1, "Sheet" + i);

           }
           return wb;

       }
   /**
    * 设备清单
    * @param templePath
    * @param record
    * @param titlemap
    * @return
    * @throws IOException
    * 
    * HSSFWorkbook
    */
   public static HSSFWorkbook SBQD(String templePath, Vector record, HashMap titlemap) throws IOException
   {
       String url = templePath + "/" + "设备清单.xls";
       InputStream stream = null;
       if(record == null || record.size() == 0)
           return null;
       URL aurl = new URL(url);
       stream = aurl.openStream();

       POIFSFileSystem fs = null;
       HSSFWorkbook wb = null;

       fs = new POIFSFileSystem(stream);
       wb = new HSSFWorkbook(fs);
       int psize = record.size();
       int pageNumber = 0;
       //CCBegin SS7
       //pageNumber = (psize % 17 == 0) ? (psize / 17) : (1 + (psize / 17));
       pageNumber = (psize % 15 == 0) ? (psize / 15) : (1 + (psize / 15));

       HSSFSheet sheet = wb.getSheetAt(0); // 获得第一个页面
       HSSFRow row;
       HSSFCell cell; // 第1行第0列
       // 表头

       row = sheet.getRow(2);
       // 零部件编号
       //cell = row.getCell((short)5);
       cell = row.getCell((short)8);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(titlemap.get("零件号") != null)
       {
           cell.setCellValue(titlemap.get("零件号").toString());
           System.out.println("cappclienthelp中 零件号 = " + titlemap.get("零件号").toString());
       }
       // 零部件名称
       //cell = row.getCell((short)6);
       cell = row.getCell((short)10);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(titlemap.get("零件名称") != null)
           cell.setCellValue(titlemap.get("零件名称").toString());
       // 车间
       /*cell = row.getCell((short)7);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(titlemap.get("车间") != null)
           cell.setCellValue(titlemap.get("车间").toString());*/

       //row = sheet.getRow(3);
       // 第号
          /*cell = row.getCell((short)2);
          //cell.setEncoding(cell.ENCODING_UTF_16);
          if(titlemap.get("文档编号") != null)
              cell.setCellValue("第  " + titlemap.get("文档编号").toString() + "  号");*/
          
       row = sheet.getRow(4);
       // 共页
       cell = row.getCell((short)10);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       cell.setCellValue("共  " + pageNumber + "  页");
       
       row = sheet.getRow(5);
       
       // 版次
       cell = row.getCell((short)0);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(titlemap.get("版次") != null)
         cell.setCellValue(titlemap.get("版次").toString());
       
       // 车间
       cell = row.getCell((short)2);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(titlemap.get("车间") != null)
           cell.setCellValue(titlemap.get("车间").toString());
       // 产品平台
       cell = row.getCell((short)5);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(titlemap.get("产品平台") != null)
           cell.setCellValue(titlemap.get("产品平台").toString());
       // 每车数量
       cell = row.getCell((short)8);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(titlemap.get("每车数量") != null)
           cell.setCellValue(titlemap.get("每车数量").toString());
       // 共页
       /*cell = row.getCell((short)6);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       cell.setCellValue("共  " + pageNumber + "  页");*/

       HSSFSheet sheet1 = null; // 缓存空页面
       // if(pageNumber > 1)
       // sheet1 = wb.cloneSheet(0); // 缓存空页面

       // 处理汇总结果
       int begin = 0;
       //int end = 17;
       int end = 15;
       String[] line;
       for(int i = 1;i <= pageNumber;i++)
       {
           if(pageNumber > 1 && i < pageNumber)
               sheet1 = wb.cloneSheet(i - 1);// 缓存空页面
           //end = i * 17;
           end = i * 15;
           //row = sheet.getRow(4);
           row = sheet.getRow(5);
           //cell = row.getCell((short)6);
           cell = row.getCell((short)10);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("第  " + i + "  页");
           
           //添加图标  由于模版带图片会出现异常，因此采用添加图片方法处理
            String urlroot = "http://" +
               RemoteProperty.getProperty("server.hostName", "") +
               RemoteProperty.getProperty("routeListTemplate",
                                          "/PhosphorPDM/template/");
           ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
           try
           {
           	 BufferedImage bufferImg = ImageIO.read(new URL(urlroot+"jf.jpg"));
           	 ImageIO.write(bufferImg,"jpg",byteArrayOut);
           }
           catch (IOException e) 
           {
             e.printStackTrace();
           }          
           HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
           /* 参数　　说明  第1个单元格 指的是图片左上点，第2个单元格指的是图片右下点
              dx1 	第1个单元格中x轴的偏移量  相对比率
              dy1 	第1个单元格中y轴的偏移量  相对比率
              dx2 	第2个单元格中x轴的偏移量  相对比率
              dy2 	第2个单元格中y轴的偏移量  相对比率
              col1 	第1个单元格的列号
              row1 	第1个单元格的行号
              col2 	第2个单元格的列号
              row2 	第2个单元格的行号
           */
           HSSFClientAnchor anchor = new HSSFClientAnchor(260,165,1023,253,(short) 3,1,(short)3,2); 
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
           //图片添加结束
           
           for(int j = begin;j < end;j++)
           {
               if(j >= record.size())
                   break;
               line = (String[])record.get(j);
               //row = sheet.getRow((j - begin) + 7);
               row = sheet.getRow((j - begin) + 8);
               for(int k = 0;k < 6;k++)
               {
                   //cell = row.getCell((short)k);
                    if(k==5)
                    {
                    	cell = row.getCell((short)6);
                    }
                    else
                    {
                    	cell = row.getCell((short)k);
                    }
       //CCEnd SS7
                   //cell.setEncoding(cell.ENCODING_UTF_16);
                   cell.setCellValue(line[k]);
               }
           }
           begin = end;

           sheet = sheet1;
               //CCBegin SS9
               if(sheet!=null)
               sheet.setSelected(false);
               //CCEnd SS9
           wb.setSheetName(i - 1, "Sheet" + i);

       }
       return wb;

   }
/**
 * 夹具明细表
 * @param templePath
 * @param record
 * @param map
 * @return
 * @throws IOException
 * 
 * HSSFWorkbook
 */
   public static HSSFWorkbook JJMXB(String templePath, Vector record, HashMap map) throws IOException
    {System.out.println("in helper jjmxb!!! templePath=="+templePath);
        HSSFWorkbook wb = null;
        try{
        String url = templePath + "/" + "夹具明细表.xls";
        InputStream stream = null;
        if(record == null || record.size() == 0)
            return null;
        URL aurl = new URL(url);
        stream = aurl.openStream();

        POIFSFileSystem fs = null;

        fs = new POIFSFileSystem(stream);
        wb = new HSSFWorkbook(fs);
        int psize = record.size();
        int pageNumber = 0;
        //CCBegin SS7
        //pageNumber = (psize % 17 == 0) ? (psize / 17) : (1 + (psize / 17));
        pageNumber = (psize % 15 == 0) ? (psize / 15) : (1 + (psize / 15));

        HSSFSheet sheet = wb.getSheetAt(0); // 获得第一个页面
        HSSFRow row;
        HSSFCell cell; // 第1行第0列
        // 表头

        row = sheet.getRow(2);
        // 零部件编号
        //cell = row.getCell((short)5);
        cell = row.getCell((short)9);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        if(map.get("零件号") != null)
        {
            cell.setCellValue(map.get("零件号").toString());
            System.out.println("cappclienthelp中 零件号 = " + map.get("零件号").toString());
        }
        // 零部件名称
        //cell = row.getCell((short)6);
        cell = row.getCell((short)12);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        if(map.get("零件名称") != null)
            cell.setCellValue(map.get("零件名称").toString());
        
        // 车间
        /*cell = row.getCell((short)8);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        if(map.get("车间") != null)
            cell.setCellValue(map.get("车间").toString());*/
        
        row = sheet.getRow(3);
        // 第号
           /*cell = row.getCell((short)2);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("文档编号") != null)
               cell.setCellValue("第  " + map.get("文档编号").toString() + "  号");*/
        
        row = sheet.getRow(4);
        // 共页
        cell = row.getCell((short)12);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        cell.setCellValue("共  " + pageNumber + "  页");
        
        row = sheet.getRow(5);
    	  // 版次
    	  cell = row.getCell((short)0);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("版次") != null)
    		  cell.setCellValue(map.get("版次").toString());
        // 车间
        cell = row.getCell((short)4);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        if(map.get("车间") != null)
            cell.setCellValue(map.get("车间").toString());
        
        // 产品平台
        cell = row.getCell((short)7);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        if(map.get("产品平台") != null)
            cell.setCellValue(map.get("产品平台").toString());
        // 每车数量
        cell = row.getCell((short)9);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        if(map.get("每车数量") != null)
            cell.setCellValue(map.get("每车数量").toString());
        
        // 共页
        /*cell = row.getCell((short)7);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        cell.setCellValue("共  " + pageNumber + "  页");*/

        HSSFSheet sheet1 = null; // 缓存空页面
        // if(pageNumber > 1)
        // sheet1 = wb.cloneSheet(0); // 缓存空页面

        // 处理汇总结果
        int begin = 0;
        //int end = 17;
        int end = 15;// 有15行明细
        String[] line;
        for(int i = 1;i <= pageNumber;i++)
        {
            if(pageNumber > 1 && i < pageNumber)
                sheet1 = wb.cloneSheet(i - 1);// 缓存空页面
            //end = i * 17;
            end = i * 15;
            //row = sheet.getRow(4);
            //cell = row.getCell((short)7);
            row = sheet.getRow(5);
            cell = row.getCell((short)12);
            //cell.setEncoding(cell.ENCODING_UTF_16);
            cell.setCellValue("第  " + i + "  页");
            
            
           //添加图标  由于模版带图片会出现异常，因此采用添加图片方法处理
            String urlroot = "http://" +
               RemoteProperty.getProperty("server.hostName", "") +
               RemoteProperty.getProperty("routeListTemplate",
                                          "/PhosphorPDM/template/");
           ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
           try
           {
           	 BufferedImage bufferImg = ImageIO.read(new URL(urlroot+"jf.jpg"));
           	 ImageIO.write(bufferImg,"jpg",byteArrayOut);
           }
           catch (IOException e) 
           {
             e.printStackTrace();
           }          
           HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
           /* 参数　　说明  第1个单元格 指的是图片左上点，第2个单元格指的是图片右下点
              dx1 	第1个单元格中x轴的偏移量  相对比率
              dy1 	第1个单元格中y轴的偏移量  相对比率
              dx2 	第2个单元格中x轴的偏移量  相对比率
              dy2 	第2个单元格中y轴的偏移量  相对比率
              col1 	第1个单元格的列号
              row1 	第1个单元格的行号
              col2 	第2个单元格的列号
              row2 	第2个单元格的行号
           */
           HSSFClientAnchor anchor = new HSSFClientAnchor(0,170,700,200,(short) 5,1,(short)5,2); 
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
           //图片添加结束
           
            for(int j = begin;j < end;j++)
            {
                if(j >= record.size())
                    break;
                line = (String[])record.get(j);
                //row = sheet.getRow((j - begin) + 7);
                row = sheet.getRow((j - begin) + 8);
                //for(int k = 0;k < 9;k++)
                for(int k = 0;k < 11;k++)
                {
                    //cell = row.getCell((short)k);
                    if(k==0)
                    {
                    	cell = row.getCell((short)0);
                    }
                    else if(k==1)
                    {
                    	cell = row.getCell((short)1);
                    }
                    else if(k==10)
                    {
                    	cell = row.getCell((short)14);
                    }
                    else
                    {
                    	cell = row.getCell((short)(k+1));
                    }
        //CCEnd SS7
                    //cell.setEncoding(cell.ENCODING_UTF_16);
                    //System.out.println("cell=="+cell.getCellNum()+"  and   line["+k+"]==="+line[k]);
                    cell.setCellValue(line[k]);
                }
            }
            begin = end;

            sheet = sheet1;
               //CCBegin SS9
               if(sheet!=null)
               sheet.setSelected(false);
               //CCEnd SS9
            wb.setSheetName(i - 1, "Sheet" + i);

        }
      }catch(Exception e)
      {
      	e.printStackTrace();
      }
        System.out.println("out helper jjmxb!!!");
        return wb;

    }
     //CCBegin SS11
   /**
    * 长特模具清单
    */
   public static HSSFWorkbook ctMoJuList(String templePath, Vector record, HashMap map) throws IOException{
	   
	   String url = templePath + "长特模具清单.xls";
       InputStream stream = null;
       if(record == null || record.size() == 0)
           return null;
       URL aurl = new URL(url);
       stream = aurl.openStream();

       POIFSFileSystem fs = null;
       HSSFWorkbook wb = null;

       fs = new POIFSFileSystem(stream);
       wb = new HSSFWorkbook(fs);
       int psize = record.size();
       int pageNumber = 0;
       pageNumber = (psize % 18 == 0) ? (psize / 18) : (1 + (psize / 18));

       HSSFSheet sheet = wb.getSheetAt(0); // 获得第一个页面
       HSSFRow row;
       HSSFCell cell; // 第1行第0列
       // 表头

       row = sheet.getRow(2);
       // 零部件编号
       cell = row.getCell((short)9);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("零件编号") != null)
       {
           cell.setCellValue(map.get("零件编号").toString());
           System.out.println("cappclienthelp中 零件号 = " + map.get("零件编号").toString());
       }
       // 零部件名称
       row = sheet.getRow(2);
       cell = row.getCell((short)6);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("零件名称") != null)
           cell.setCellValue(map.get("零件名称").toString());
       // 材料
       row = sheet.getRow(3);
       cell = row.getCell((short)9);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("材料编号") != null&&map.get("材料名称") != null)
           cell.setCellValue("材料:"+map.get("材料编号").toString()+"/"+map.get("材料名称").toString());
       
          
       row = sheet.getRow(4);
       // 版次
       cell = row.getCell((short)3);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("版次") != null)
           cell.setCellValue(map.get("版次").toString());
       // 日期
       cell = row.getCell((short)6);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("发放日期") != null)
           cell.setCellValue(map.get("发放日期").toString());
       
       // 共页
       cell = row.getCell((short)9);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       cell.setCellValue("共  " + pageNumber + "  页");

       HSSFSheet sheet1 = null; // 缓存空页面
       System.out.println("pageNumber=========================="+pageNumber);
       // 处理汇总结果
       int begin = 0;
       int end = 18;
       String[] line;
       for(int i = 1;i <= pageNumber;i++)
       {
           if(pageNumber > 1 && i < pageNumber)
               sheet1 = wb.cloneSheet(i - 1);// 缓存空页面
           end = i * 18;
           
           row = sheet.getRow(4);
           cell = row.getCell((short)10);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("第  " + i + "  页");
           for(int j = begin;j < end;j++)
           {
               if(j >= record.size())
                   break;
               line = (String[])record.get(j);
               row = sheet.getRow((j - begin) + 7);

               if(row!=null){
                  for(int k = 0;k < 9;k++)
                  {
                   cell = row.getCell((short)k);
                   //cell.setEncoding(cell.ENCODING_UTF_16);
                   cell.setCellValue(line[k]);
                  }
               }
           }
           begin = end;

           sheet = sheet1;
           wb.setSheetName(i - 1, "Sheet" + i);

       }
       return wb;
   }
   
   /**
    * 长特设备清单
    */
   public static HSSFWorkbook  ctEqList(String templePath, Vector record, HashMap map) throws IOException{
	   
	   String url = templePath + "长特设备清单.xls";
       InputStream stream = null;
       if(record == null || record.size() == 0)
           return null;
       URL aurl = new URL(url);
       stream = aurl.openStream();

       POIFSFileSystem fs = null;
       HSSFWorkbook wb = null;

       fs = new POIFSFileSystem(stream);
       wb = new HSSFWorkbook(fs);
       int psize = record.size();
       int pageNumber = 0;
       pageNumber = (psize % 16 == 0) ? (psize / 16) : (1 + (psize / 16));

       HSSFSheet sheet = wb.getSheetAt(0); // 获得第一个页面
       HSSFRow row;
       HSSFCell cell; // 第1行第0列
       // 表头

    // 零部件名称
       row = sheet.getRow(1);
       cell = row.getCell((short)7);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("零件名称") != null)
           cell.setCellValue(map.get("零件名称").toString());
       
       
       row = sheet.getRow(2);
       // 零部件编号
       cell = row.getCell((short)7);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("零件编号") != null)
       {
           cell.setCellValue(map.get("零件编号").toString());
           System.out.println("cappclienthelp中 零件号 = " + map.get("零件编号").toString());
       }
       
       // 车间
       row = sheet.getRow(3);
       cell = row.getCell((short)7);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("车间") != null)
           cell.setCellValue(map.get("车间").toString());
       
          
       row = sheet.getRow(4);
       // 生产线
       cell = row.getCell((short)7);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("生产线") != null)
           cell.setCellValue("生产线");
       row = sheet.getRow(5);
//       // 种类
//       cell = row.getCell((short)1);
//       //cell.setEncoding(cell.ENCODING_UTF_16);
//       if(map.get("种类") != null)
//           cell.setCellValue(map.get("种类").toString());
       //版次
       cell = row.getCell((short)2);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("版次") != null)
           cell.setCellValue(map.get("版次").toString());
       //发放日期
       cell = row.getCell((short)6);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("发放日期") != null)
           cell.setCellValue(map.get("发放日期").toString());
       
       // 共页
       cell = row.getCell((short)7);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       cell.setCellValue("共  " + pageNumber + "  页");

       HSSFSheet sheet1 = null; // 缓存空页面
 
       // 处理汇总结果
       int begin = 0;
       int end = 16;
       String[] line;
       for(int i = 1;i <= pageNumber;i++)
       {
           if(pageNumber > 1 && i < pageNumber)
               sheet1 = wb.cloneSheet(i - 1);// 缓存空页面
           end = i * 16;
           
           row = sheet.getRow(6);
           cell = row.getCell((short)7);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("第  " + i + "  页");
           for(int j = begin;j < end;j++)
           {
               if(j >= record.size())
                   break;
               line = (String[])record.get(j);
               row = sheet.getRow((j - begin) + 8);
               
               
               if(row!=null){
                  for(int k = 0;k < 7;k++)
                  {
                   cell = row.getCell((short)k);
                   //cell.setEncoding(cell.ENCODING_UTF_16);
                   cell.setCellValue(line[k]);
                  }
               }
           }
           begin = end;

           sheet = sheet1;
           wb.setSheetName(i - 1, "Sheet" + i);

       }
       return wb;
	   
   }
   
   /**
    * 长特工装明细
    * @param templePath
    * @param record
    * @param map
    * @return
    * @throws IOException
    */
   public static HSSFWorkbook ctToolMingXi(String templePath, Vector record, HashMap map) throws IOException{

	   
	   String url = templePath + "长特工装明细表.xls";
       InputStream stream = null;
       if(record == null || record.size() == 0)
           return null;
       URL aurl = new URL(url);
       stream = aurl.openStream();

       POIFSFileSystem fs = null;
       HSSFWorkbook wb = null;

       fs = new POIFSFileSystem(stream);
       wb = new HSSFWorkbook(fs);
       int psize = record.size();
       int pageNumber = 0;
       pageNumber = (psize % 14 == 0) ? (psize / 14) : (1 + (psize / 14));

       HSSFSheet sheet = wb.getSheetAt(0); // 获得第一个页面
       HSSFRow row;
       HSSFCell cell; // 第1行第0列
       // 表头

       row = sheet.getRow(1);
       // 零部件编号
       cell = row.getCell((short)12);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("零件编号") != null)
       {
           cell.setCellValue(map.get("零件编号").toString());
           System.out.println("cappclienthelp中 零件号 = " + map.get("零件编号").toString());
       }
       // 零部件名称
       row = sheet.getRow(2);
       cell = row.getCell((short)12);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("零件名称") != null)
           cell.setCellValue(map.get("零件名称").toString());
       // 车间
       row = sheet.getRow(3);
       cell = row.getCell((short)12);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("车间") != null)
           cell.setCellValue(map.get("车间").toString());
       
          
       row = sheet.getRow(4);
       // 生产线
       cell = row.getCell((short)12);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("生产线") != null)
           cell.setCellValue("生产线");
       // 种类
       row = sheet.getRow(5);
       cell = row.getCell((short)1);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("种类") != null)
           cell.setCellValue(map.get("种类").toString());
       //版次
       cell = row.getCell((short)5);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("版次") != null)
           cell.setCellValue(map.get("版次").toString());
       //发放日期
       cell = row.getCell((short)9);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("发放日期") != null)
           cell.setCellValue(map.get("发放日期").toString());
       
       // 共页
       cell = row.getCell((short)12);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       cell.setCellValue("共  " + pageNumber + "  页");

       HSSFSheet sheet1 = null; // 缓存空页面
 
       // 处理汇总结果
       int begin = 0;
       int end = 14;
       String[] line;
       for(int i = 1;i <= pageNumber;i++)
       {
           if(pageNumber > 1 && i < pageNumber)
               sheet1 = wb.cloneSheet(i - 1);// 缓存空页面
           end = i * 14;
           
           row = sheet.getRow(6);
           cell = row.getCell((short)12);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("第  " + i + "  页");
           for(int j = begin;j < end;j++)
           {
               if(j >= record.size())
                   break;
               line = (String[])record.get(j);
               row = sheet.getRow((j - begin) + 9);

               if(row!=null){
                  for(int k = 0;k < 10;k++)
                  {
                   cell = row.getCell((short)k);
                   //cell.setEncoding(cell.ENCODING_UTF_16);
                   cell.setCellValue(line[k]);
                  }
               }
           }
           begin = end;

           sheet = sheet1;
           wb.setSheetName(i - 1, "Sheet" + i);

       }
       return wb;
	   
	   
   }
   
   
   //CCEnd SS11
   /**
    * 万能工具清单
    * @param templePath
    * @param record
    * @param map
    * @return
    * @throws IOException
    * 
    * HSSFWorkbook
    */
   //CCBegin SS8
   //public static HSSFWorkbook WNGJQD(String templePath, Vector record, HashMap map) throws IOException
   public static HSSFWorkbook WNGJQD(String templePath, Vector record, HashMap map, String type) throws IOException
   {
   	System.out.println("WNGJQD  type===="+type);
       //String url = templePath + "/" + "万能工具清单.xls";
       String url = templePath + "/" + type+".xls";
   //CCEnd SS8
       InputStream stream = null;
       if(record == null || record.size() == 0)
           return null;
       URL aurl = new URL(url);
       stream = aurl.openStream();

       POIFSFileSystem fs = null;
       HSSFWorkbook wb = null;

       fs = new POIFSFileSystem(stream);
       wb = new HSSFWorkbook(fs);
       int psize = record.size();
       int pageNumber = 0;
       pageNumber = (psize % 17 == 0) ? (psize / 17) : (1 + (psize / 17));

       HSSFSheet sheet = wb.getSheetAt(0); // 获得第一个页面
       HSSFRow row;
       HSSFCell cell; // 第1行第0列
       // 表头

       row = sheet.getRow(2);
       // 零部件编号
       //CCBegin SS7
       //cell = row.getCell((short)5);
       cell = row.getCell((short)10);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("零件号") != null)
       {
           cell.setCellValue(map.get("零件号").toString());
           System.out.println("cappclienthelp中 零件号 = " + map.get("零件号").toString());
       }
       // 零部件名称
       //cell = row.getCell((short)7);
       cell = row.getCell((short)13);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("零件名称") != null)
           cell.setCellValue(map.get("零件名称").toString());
       // 车间
       /*cell = row.getCell((short)8);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("车间") != null)
           cell.setCellValue(map.get("车间").toString());*/
       
       //row = sheet.getRow(3);
       // 第号
          /*cell = row.getCell((short)2);
          //cell.setEncoding(cell.ENCODING_UTF_16);
          if(map.get("文档编号") != null)
              cell.setCellValue("第  " + map.get("文档编号").toString() + "  号");*/
          
       row = sheet.getRow(4);
       // 共页
       cell = row.getCell((short)13);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       cell.setCellValue("共  " + pageNumber + "  页");
       
       row = sheet.getRow(5);
       
       // 版次
       cell = row.getCell((short)3);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("版次") != null)
         cell.setCellValue(map.get("版次").toString());
       
       // 车间
       cell = row.getCell((short)6);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("车间") != null)
           cell.setCellValue(map.get("车间").toString());
           
       // 产品平台
       //cell = row.getCell((short)5);
       cell = row.getCell((short)8);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("产品平台") != null)
           cell.setCellValue(map.get("产品平台").toString());
       // 每车数量
       //cell = row.getCell((short)7);
       cell = row.getCell((short)10);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("每车数量") != null)
           cell.setCellValue(map.get("每车数量").toString());
       // 共页
       /*cell = row.getCell((short)8);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       cell.setCellValue("共  " + pageNumber + "  页");*/

       HSSFSheet sheet1 = null; // 缓存空页面
 
       // 处理汇总结果
       int begin = 0;
       int end = 17;
       String[] line;
       for(int i = 1;i <= pageNumber;i++)
       {
           if(pageNumber > 1 && i < pageNumber)
               sheet1 = wb.cloneSheet(i - 1);// 缓存空页面
           end = i * 17;
           //row = sheet.getRow(4);
           row = sheet.getRow(5);
           //cell = row.getCell((short)8);
           cell = row.getCell((short)13);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("第  " + i + "  页");
           
           //添加图标  由于模版带图片会出现异常，因此采用添加图片方法处理
            String urlroot = "http://" +
               RemoteProperty.getProperty("server.hostName", "") +
               RemoteProperty.getProperty("routeListTemplate",
                                          "/PhosphorPDM/template/");
           ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
           try
           {
           	 BufferedImage bufferImg = ImageIO.read(new URL(urlroot+"jf.jpg"));
           	 ImageIO.write(bufferImg,"jpg",byteArrayOut);
           }
           catch (IOException e) 
           {
             e.printStackTrace();
           }          
           HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
           /* 参数　　说明  第1个单元格 指的是图片左上点，第2个单元格指的是图片右下点
              dx1 	第1个单元格中x轴的偏移量  相对比率
              dy1 	第1个单元格中y轴的偏移量  相对比率
              dx2 	第2个单元格中x轴的偏移量  相对比率
              dy2 	第2个单元格中y轴的偏移量  相对比率
              col1 	第1个单元格的列号
              row1 	第1个单元格的行号
              col2 	第2个单元格的列号
              row2 	第2个单元格的行号
           */
           HSSFClientAnchor anchor = new HSSFClientAnchor(250,125,1023,125,(short) 4,1,(short)4,2); 
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
           //图片添加结束
           
           for(int j = begin;j < end;j++)
           {
               if(j >= record.size())
                   break;
               line = (String[])record.get(j);
               //row = sheet.getRow((j - begin) + 7);
               row = sheet.getRow((j - begin) + 8);
               //for(int k = 0;k < 9;k++)
               for(int k = 0;k < 8;k++)
               {
                   //cell = row.getCell((short)k);
                   if(k==0)
                   {
                   	cell = row.getCell((short)0);
                   }
                   else if(k==1)
                   {
                   	cell = row.getCell((short)1);
                   }
                   else if(k==2)
                   {
                   	cell = row.getCell((short)3);
                   }
                   else if(k==3)
                   {
                   	cell = row.getCell((short)5);
                   }
                   else if(k==4)
                   {
                   	cell = row.getCell((short)6);
                   }
                   else if(k==5)
                   {
                   	cell = row.getCell((short)7);
                   }
                   else if(k==6)
                   {
                   	cell = row.getCell((short)8);
                   }
                   else if(k==7)
                   {
                   	cell = row.getCell((short)13);
                   }
       //CCEnd SS7
                   //cell.setEncoding(cell.ENCODING_UTF_16);
                   cell.setCellValue(line[k]);
               }
           }
           begin = end;

           sheet = sheet1;
               //CCBegin SS9
               if(sheet!=null)
               sheet.setSelected(false);
               //CCEnd SS9
           wb.setSheetName(i - 1, "Sheet" + i);

       }
       return wb;

   }
   //CCEnd SS1
   //CCBegin SS3
   public static UserIfc getCurrentUser()
           throws QMRemoteException
   {
       UserIfc sysUser = (UserIfc) TechnicsAction.useServiceMethod("SessionService",
               "getCurUserInfo", null, null);
       return sysUser;
   }
   
   public static boolean isBsxGroupUser() throws QMException 
 	{
 		Vector groupVec = new Vector();  		
 		UserIfc sysUser = getCurrentUser();
 		Class[] paraclass1 = {UserInfo.class, boolean.class};
 		Object[] paraobj1 = {(UserInfo)sysUser, Boolean.TRUE};
 		Enumeration groups = (Enumeration) TechnicsAction.useServiceMethod("UsersService","userMembers", paraclass1,paraobj1);
 		for (; groups.hasMoreElements();) 
 		{			
 			GroupIfc group = (GroupIfc) groups.nextElement();
 			String groupName = group.getUsersName();
 			if (groupName.equals("变速箱用户组")) 
 			{
 				return true;
 			}
 		}
 		return false;
 	}

   /**
    * 判断用户所属公司
    * @return String 获得用户所属公司
    * @author wenl
    */
   public static String getUserFromCompany() throws QMException {
		String returnStr = "";
		RequestServer server = RequestServerFactory.getRequestServer();
		StaticMethodRequestInfo info = new StaticMethodRequestInfo();
		info.setClassName("com.faw_qm.doc.util.DocServiceHelper");
		info.setMethodName("getUserFromCompany");
		Class[] paraClass = {};
		info.setParaClasses(paraClass);
		Object[] obj = {};
		info.setParaValues(obj);
		boolean flag = false;
		try {
			returnStr = ((String) server.request(info));
		} catch (QMRemoteException e) {
			throw new QMException(e);
		}
		return returnStr;
	}
   //CCEnd SS3
   //CCBegin SS4
   public static void createCompare(Vector vc,QMFawTechnicsIfc tech) throws IOException, QMException{
	   String templePath = RemoteProperty.getProperty("bsx_technics_templePath");
	   String url = templePath+"gywjbgtzd.xls";;
	   InputStream stream = null;
	   HSSFWorkbook wbook = null;
	   String num = null;
	   Class[] paraclass1 = {String.class,String.class};
	   Object[] paraobj1 = {tech.getBsoID(),"工艺文件变更通知单"};
	   //CCBegin SS6
//	   ZCZXUtil zc = new ZCZXUtil();
//	   num = zc.getSerialNum(tech.getBsoID(), "工艺文件变更通知单");
		//CCEnd SS6
	   num = (String) useServiceMethod("StandardCappService", "getDocNum", paraclass1,paraobj1);
	   if(vc != null || vc.size() != 0){
		   URL aurl = new URL(url);
		   stream = aurl.openStream();

		   POIFSFileSystem fs = null;
		   HSSFWorkbook wb = null;

		   fs = new POIFSFileSystem(stream);
		   wb = new HSSFWorkbook(fs);
		   int psize = vc.size();
		   int pageNumber = 0;
		   pageNumber = (psize % 14 == 0) ? (psize / 14) : (1 + (psize / 14));

		   HSSFSheet sheet = wb.getSheetAt(0); // 获得第一个页面
		   HSSFRow row;
		   HSSFCell cell; // 第1行第0列
		   // 表头

		   row = sheet.getRow(1);
		   cell = row.getCell((short)9);
		   //cell.setEncoding(cell.ENCODING_UTF_16);
		   cell.setCellValue(num);
		   
		   row = sheet.getRow(2);
		   //CCBegin SS6
		   UserIfc user = (UserIfc) TechnicsAction.useServiceMethod("SessionService",
	                "getCurUserInfo", null, null);
		   
		   cell = row.getCell((short)9);
		   //cell.setEncoding(cell.ENCODING_UTF_16);
		   cell.setCellValue(user.getUsersDesc());
		   
		   SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		   String a1=formatter.format(new Date());
		   
		   cell = row.getCell((short)14);
		   //cell.setEncoding(cell.ENCODING_UTF_16);
		   cell.setCellValue(a1);
			//CCBegin SS6
		   cell = row.getCell((short)15);
		   //cell.setEncoding(cell.ENCODING_UTF_16);
		   cell.setCellValue("共  " + pageNumber + "  页");
		   //CCBegin SS6
		   row = sheet.getRow(3);

		   Class[] paraclass2 = {QMTechnicsIfc.class};
		   Object[] paraobj2 = {tech};
		   QMPartIfc mainPart = (QMPartIfc) useServiceMethod("StandardCappService", "getQMPart", paraclass2,paraobj2);
		   
		   if(mainPart != null){
			   cell = row.getCell((short)2);
			   //cell.setEncoding(cell.ENCODING_UTF_16);
			   cell.setCellValue(mainPart.getPartNumber());

			   cell = row.getCell((short)6);
			   //cell.setEncoding(cell.ENCODING_UTF_16);
			   cell.setCellValue(mainPart.getPartName());
		   }
		   //CCEnd SS6
		   row = sheet.getRow(4);

		   cell = row.getCell((short)3);
		   //cell.setEncoding(cell.ENCODING_UTF_16);
		   cell.setCellValue(tech.getTechnicsNumber());

		   HSSFSheet sheet1 = null; // 缓存空页面

		   int begin = 0;
		   int end = 14;
		   String[] line;
		   for(int i = 1;i <= pageNumber;i++)
		   {
			   if(pageNumber > 1 && i < pageNumber)
				   sheet1 = wb.cloneSheet(i - 1);// 缓存空页面
			   end = i * 14;

			   row = sheet.getRow(1);
			   cell = row.getCell((short)15);
			   //cell.setEncoding(cell.ENCODING_UTF_16);
			   cell.setCellValue("第  " + i + "  页");

			   for(int j = begin;j < end;j++)
			   {
				   if(j >= vc.size())
					   break;
				   line = (String[])vc.get(j);
				   row = sheet.getRow((j - begin) + 9);
				   for(int k = 0;k < line.length;k++)
				   {
					   if(k==0){
						   cell = row.getCell((short)k);
						   //cell.setEncoding(cell.ENCODING_UTF_16);
						   cell.setCellValue(j+1);
						   cell = row.getCell((short)1);
						   //cell.setEncoding(cell.ENCODING_UTF_16);
						   cell.setCellValue(line[k]);
					   }
					   if(k==1){
						   cell = row.getCell((short)3);
						   //cell.setEncoding(cell.ENCODING_UTF_16);
						   cell.setCellValue(line[k]);
					   }
					   if(k==2){
						   cell = row.getCell((short)7);
						   //cell.setEncoding(cell.ENCODING_UTF_16);
						   cell.setCellValue(line[k]);
					   }
				   }
			   }
			   begin = end;

			   sheet = sheet1;
			   wb.setSheetName(i - 1, "Sheet" + i);
		   }
		   wbook = wb;
	   }
	   String clientPath = RemoteProperty.getProperty("bsx_schedule_tempPath");
	   //		String clientPath = "/opt/pdmv4r3/productfactory/phosphor/cpdm/classes/com/faw_qm/zczx/util";
	   if(wbook != null)
	   {
		   // 检测路径是否存在，不存在则创建
		   String filePName = clientPath + "/工艺文件变更单.xls";
		   File file = new File(clientPath);
		   // 如果文件夹不存在则创建
		   if(!file.exists())
		   {
			   file.mkdirs();
		   }
		   file = new File(filePName);
		   if(file.exists())
		   {
			   file.delete();
		   }
		   file.createNewFile();
		   // 创建派生文件
		   FileOutputStream cout = null;
		   cout = new FileOutputStream(filePName);
		   wbook.write(cout);
		   cout.flush();
		   cout.close();

		   DocInfo doc =null;
		   if(fileVaultUsed)
		   {
			   ContentClientHelper helper = new ContentClientHelper();
			   ApplicationDataInfo appDataInfo = helper.requestUpload(file);
			   Class[] paraclass = {String.class,String.class,ApplicationDataInfo.class,String.class};
			   Object[] paraobj = {tech.getBsoID(),"工艺文件变更通知单",appDataInfo,num};
			   doc = (DocInfo) useServiceMethod("StandardCappService", "createFile", paraclass,paraobj);
		   }
		   else
		   {
			   // 构造ApplicationDataInfo
			   ApplicationDataInfo appDataInfo = new ApplicationDataInfo();
			   FileInputStream input = null;
			   byte[] filebyte = null;

			   try
			   {
				   input = new FileInputStream(file);
			   }catch(FileNotFoundException e)
			   {
				   JOptionPane.showConfirmDialog(null, file+"正被另一应用程序使用，请关闭文件","提示", JOptionPane.INFORMATION_MESSAGE);
				   e.printStackTrace();
			   }

			   appDataInfo.setFileName(file.getName());
			   appDataInfo.setUploadPath(file.getPath());

			   ByteArrayOutputStream out = new ByteArrayOutputStream();
			   //  一次读取1024字节 
			   byte[] bytes = new byte[1024];
			   try {
				   while(input.read(bytes)!=-1){
					   out.write(bytes);
					   out.flush();
				   }
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }

			   try {
				   input.close();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			   try {
				   out.close();
			   } catch (IOException e) {
				   // TODO Auto-generated catch block
				   e.printStackTrace();
			   }
			   // 生成文件流b
			   filebyte = out.toByteArray();
			   appDataInfo.setFileSize(filebyte.length);
			   // 创建文档
			   StandardCappService scs = null;
			   Class[] paraclass = {String.class,String.class,ApplicationDataInfo.class,byte[].class,String.class};
			   Object[] paraobj = {tech.getBsoID(),"工艺文件变更通知单",appDataInfo,filebyte,num};
			   doc = (DocInfo) useServiceMethod("StandardCappService", "createFile", paraclass,paraobj);
		   }
	   }
   }
   //CCEnd SS4
}
