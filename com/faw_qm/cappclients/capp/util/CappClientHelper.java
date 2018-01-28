/** ���ɳ��� CappClientHelper.java    1.0    2003/09/05
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ��������Դ�嵥һ���� zhaoqiuying 2013-01-23
 * SS2 ����ļ������������߼� jiahx 2013-12-23
 * SS3 ��ݹ��������ļ� pante 2014-02-19
 * SS4 ��ݹ����ļ������ pante 2014-03-26
 * SS5 ��ݹ��������ļ��в���ʾ��������ƺ� pante 2014-05-12
 * SS6 ��ݹ����ļ��������ͷ���� pante 2014-05-12
 * SS7 ��������Դ�嵥һ����ģ����ĵ����� liunan 2014-7-22
 * SS8 ���������������嵥���͡���λ�����嵥������һ���� liunan 2014-7-28
 * SS9 ��������Դһ�����ҳʱ������Ĭ��ѡ�е�һҳ�͵ڶ�ҳ���޸ĵĻ��Ὣ��ҳ���޸ġ� liunan 2014-7-30
 * SS10 ����������������ӵ�����һ���� pante 2014-09-10
 * SS11 �������ӹ�װ��ϸ���豸�嵥��ģ���嵥�� guoxiaoliang 2014-08-22
 * SS12 ��������ļ���ҳʱ���޷����ɣ���ΪͼƬ��֧��clone����Ϊ����ͼƬ��ʽ�� liunan 2015-3-10
 * SS13 ƽ̨����A034-2015-0222����������ļ������ȥ����ͷ����ݺͺ�׺��һ������ϸ�� pante 2015-03-20
 * SS14 ����poi��3.6��ע��//cell.setEncoding(cell.ENCODING_UTF_16); liunan 2016-4-11
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
 * <p>Title:Ϊ�ͻ����ṩ���� </p>
 * <p>Description:�����ṩ��һϵ�о�̬�������Է���ͻ��˵��÷��� </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */

public class CappClientHelper
{
    /**������Ա���*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**
     * ����������������
     */
    private static HashMap allSpechar;

    //CCBegin SS2
    static boolean fileVaultUsed = (RemoteProperty.getProperty(
            "registryFileVaultStoreMode", "true")).equals("true");
    //CCEnd SS2
    
    /**
     * ���캯��
     */
    public CappClientHelper()
    {
    }


    /**
     * ���������ڿͻ���Զ�̵��÷���˷���
     * @param serviceName Ҫ���õķ���������
     * @param methodName Ҫ���õķ��񷽷�����
     * @param paraClass  Ҫ���õķ��񷽷��Ĳ���������
     * @param paraObject Ҫ���õķ��񷽷��Ĳ���ֵ
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
     * ���ݳ־û���Ϣˢ��ҵ�����
     * ���ظ�������ֵ����
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
            //�������͵ļ���
        }
        Class[] paraClass =
                {BaseValueIfc.class};
        //����ֵ�ļ���
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
     * ���ݳ־û���Ϣˢ��ҵ�����
     * ���ظ�������ֵ����
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
            //�������͵ļ���
        }
        Class[] paraClass =
                {String.class};
        //����ֵ�ļ���
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
     * ��������Ĺ��տ�ֵ��������������ݿ���и��£����ظ��º�Ĺ��տ�ֵ����
     * @param technics QMFawTechnicsInfo ˢ��ǰ�Ĺ���ֵ����
     * @return QMFawTechnicsInfo ˢ��ǰ�Ĺ���ֵ����
     * @throws QMRemoteException
     */
    public static QMFawTechnicsInfo refresh(QMFawTechnicsInfo technics)
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientHelper.refresh(2) begin...");
            //�������͵ļ���
        }
        Class[] paraClass =
                {BaseValueIfc.class};
        //����ֵ�ļ���
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
     * ɾ��ҵ�����
     * @param info Ҫɾ����ҵ�����
     * @exception QMRemoteException
     */
    public static void deleteBaseValue(BaseValueIfc info)
            throws QMRemoteException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientHelper.deleteBaseValue() begin...");
            //�������͵ļ���
        }
        Class[] paraClass =
                {BaseValueIfc.class};
        //����ֵ�ļ���
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
     * ���������ʱ�����������ȡ��"��-��-��"���֡�
     * @param timestamp �����ʱ���
     * @return String �ַ�����ʽ��"��-��-��"
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
     * ��ȡָ���������ָ�����Ե���󳤶ȡ�
     * @param class1 ָ������
     * @param s ָ�����ָ������
     * @return int ��󳤶�
     */
    public static int getMaxLength(Class class1, String s)
    {
        int i = 0;

        return i;
    }


    /**
     * �жϵ�ǰ�û��Ƿ���з���Ȩ��
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
        //���ûỰ�����õ�ǰ�û�
        ServiceRequestInfo info1 = new ServiceRequestInfo();
        info1.setServiceName("SessionService");
        info1.setMethodName("getCurUser");
        //�������͵ļ���
        Class[] paraClass =
                {};
        info1.setParaClasses(paraClass);
        //����ֵ�ļ���
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

        //���÷��ʿ��Ʒ����жϵ�ǰ�û���ָ�����ࡢָ������
        //ָ������������״̬�Ƿ����ָ���ķ��ʿ���Ȩ��
        ServiceRequestInfo info2 = new ServiceRequestInfo();
        info2.setServiceName("AccessControlService");
        info2.setMethodName("hasAccess");
        //�������͵ļ���
        Class[] paraClass1 =
                {Actor.class,
                String.class,
                String.class,
                LifeCycleState.class,
                String.class
        };
        info2.setParaClasses(paraClass);
        //����ֵ�ļ���
        Object[] objs1 =
                {user, //�û�
                className, //����
                DomainHelper.SYSTEM_DOMAIN, //��
                null, //״̬
                permission //���ʿ���Ȩ��
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
     *��Ѱ���򣬹���ʹ�õ���Դ����
     *@param  bsoID �����bsoId
     *@return ʹ����Դ�����ļ���
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
     * �ж�ִ�и��Ʋ�����Դ�����Ŀ�����Ĺ��������Ƿ�һ�¡�
     * @param original Դ�������
     * @param object   Ŀ�깤�����
     * @return �����������һ�£��򷵻�Ϊ�棻���򷵻�Ϊ�١�
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
     * �ж�ִ�и��Ʋ�����Դ�����Ŀ�����Ĺ��������Ƿ�һ�¡�
     * @param original Դ���ն���
     * @param object   Ŀ�깤�ն���
     * @return �����������һ�£��򷵻�Ϊ�棻���򷵻�Ϊ�١�
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
     * ��������������,������������ʹ��
     * @return HashMap ��:����������� ����String ֵ:SpeCharaterInfo���󼯺� ����Vector
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
     * ���������ĵ�
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
		   System.out.println("ResouceTotle���ܽ��Ϊ��!");
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
			   titleMap.put("�ĵ����", num);
		   }
		   if(getUserFromCompany().equals("zczx")){
			   num = "PS-" +titleMap.get("���ձ��").toString()+"-"+totleType[i]+"-"+titleMap.get("���").toString();
			   titleMap.put("�ĵ����", num);
		   }
		 //CCEnd SS3
		 //CCBegin SS11
		   if(getUserFromCompany().equals("ct")){
			   num = "PS-" +titleMap.get("���ձ��").toString()+"-"+totleType[i]+"-"+titleMap.get("���").toString();
			   titleMap.put("�ĵ����", num);
		   }
		   
		   //CCEnd SS11
		   if(totleType[i].equals("�о���ϸ��"))
		   {
			   wbook = JJMXB(templePath, record, titleMap);
		   }else if(totleType[i].equals("���ܹ����嵥"))
		   {
			   //CCBegin SS8
			   //wbook = WNGJQD(templePath, record, titleMap);
			   wbook = WNGJQD(templePath, record, titleMap, totleType[i]);
                
       }else if(totleType[i].equals("���������嵥"))
       {
       	 wbook = WNGJQD(templePath, record, titleMap, totleType[i]);
       }else if(totleType[i].equals("��λ�����嵥"))
       {
       	 wbook = WNGJQD(templePath, record, titleMap, totleType[i]);
       	 //CCEnd SS8
		   }else if(totleType[i].equals("�豸�嵥"))
		   {
			   //                Vector record = (Vector)map.get(totleType[i]);
			   wbook = SBQD(templePath, record, titleMap);

		   }else if(totleType[i].equals("ĥ��һ����"))
		   {
			   //                Vector record = (Vector)map.get(totleType[i]);
			   wbook = MJ(templePath, record, titleMap);

		   }else if(totleType[i].equals("����һ����"))
		   {
			   //                Vector record = (Vector)map.get(totleType[i]);
			   wbook = DJ(templePath, record, titleMap,totleType[i]);

		   }else if(totleType[i].equals("�и���һ����"))
		   {
			   //                Vector record = (Vector)map.get(totleType[i]);
			   //CCBegin SS7
			   //wbook = DJ(templePath, record, titleMap,totleType[i]);
			   wbook = JJMXB(templePath, record, titleMap);
			   //CCEnd SS7

		   }else if(totleType[i].equals("װ�乤��һ����"))
		   {
			   //                Vector record = (Vector)map.get(totleType[i]);
			   wbook = DJ(templePath, record, titleMap,totleType[i]);
		   }
		   //CCBegin SS3
		   else if(totleType[i].equals("���ĥ��һ����"))
		   {
		   	//CCBegin SS5
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }else if(totleType[i].equals("��ݵ���һ����"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }
		   //		   CCBegin SS10
		   else if(totleType[i].equals("��ݵ�����һ����"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }
//		   CCEnd SS10
		   else if(totleType[i].equals("��ݼи���һ����"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }else if(totleType[i].equals("�������һ����"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }else if(totleType[i].equals("��ݼ츨��һ����"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }
		   else if(totleType[i].equals("��ݼо���ϸ��"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }
		   else if(totleType[i].equals("�������������ϸ��"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }
		   else if(totleType[i].equals("������ܹ�����ϸ��"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
		   }
		   else if(totleType[i].equals("��ݼ����ϸ��"))
		   {
			   wbook = YLB(templePath, record, titleMap,totleType[i],techID);
			   //CCEnd SS5
		   }else if(totleType[i].equals("����豸�嵥"))
		   {
			   wbook = SB(templePath, record, titleMap);
		   }
		   //CCEnd SS3
		   	//CCBegin SS11
		   else if(totleType[i].equals("���ع�װ��ϸ��"))
		   {
			   wbook = ctToolMingXi(templePath, record, titleMap);
		   }else if(totleType[i].equals("�����豸�嵥"))
		   {
			   wbook = ctEqList(templePath, record, titleMap);
			   
		   }else if(totleType[i].equals("����ģ���嵥"))
		   {
			   wbook = ctMoJuList(templePath, record, titleMap);
		   }
		   
		   //CCEnd SS11
		   if(wbook != null)
		   {
			   // ���·���Ƿ���ڣ��������򴴽�
			   String filePName = clientPath + "/"+totleType[i] + ".xls";
			   System.out.println("filePName======"+filePName);
			   File file = new File(clientPath);
			   // ����ļ��в������򴴽�
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
			   // ���������ļ�
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
				   // ����ApplicationDataInfo
				   ApplicationDataInfo appDataInfo = new ApplicationDataInfo();
				   FileInputStream input = null;
				   byte[] filebyte = null;

				   try
				   {
					   input = new FileInputStream(file);
				   }catch(FileNotFoundException e)
				   {
					   JOptionPane.showConfirmDialog(null, file+"������һӦ�ó���ʹ�ã���ر��ļ�","��ʾ", JOptionPane.INFORMATION_MESSAGE);
					   e.printStackTrace();
				   }

				   appDataInfo.setFileName(file.getName());
				   appDataInfo.setUploadPath(file.getPath());

				   ByteArrayOutputStream out = new ByteArrayOutputStream();
				   //  һ�ζ�ȡ1024�ֽ� 
				   byte[] bytes = new byte[1024];
				   while(input.read(bytes)!=-1){
					   out.write(bytes);
					   out.flush();
				   }

				   input.close();
				   out.close();
				   // �����ļ���b
				   filebyte = out.toByteArray();
				   appDataInfo.setFileSize(filebyte.length);
				   // �����ĵ�
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
    * ���ߡ�װ�乤��һ����
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
           HSSFSheet sheet = wb.getSheetAt(0); // ��õ�һ��ҳ��
           sheet.setSelected(true);
           HSSFRow row;
           HSSFCell cell; // ��1�е�0��
           // ��ͷ

           row = sheet.getRow(2);
           // �㲿�����
           //CCBegin SS7
           //cell = row.getCell((short)6);
           cell = row.getCell((short)9);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("�����") != null)
           {
               cell.setCellValue(map.get("�����").toString());
               System.out.println("cappclienthelp�� ����� = " + map.get("�����").toString());
           }
           // �㲿������
           //cell = row.getCell((short)8);
           cell = row.getCell((short)12);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("�������") != null)
               cell.setCellValue(map.get("�������").toString());
           // ����
           /*cell = row.getCell((short)9);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("����") != null)
               cell.setCellValue(map.get("����").toString());*/
           
           row = sheet.getRow(3);
           // �ں�
              cell = row.getCell((short)3);
              //cell.setEncoding(cell.ENCODING_UTF_16);
              if(map.get("�ĵ����") != null)
                  cell.setCellValue("��  " + map.get("�ĵ����").toString() + "  ��");
              
           row = sheet.getRow(4);
           // ��ҳ
           cell = row.getCell((short)12);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("��  " + pageNumber + "  ҳ");
           
           row = sheet.getRow(5);
           // ���
           cell = row.getCell((short)3);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("���") != null)
               cell.setCellValue(map.get("���").toString());
           // ����
           cell = row.getCell((short)5);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("����") != null)
               cell.setCellValue(map.get("����").toString());
           // ��Ʒƽ̨
           cell = row.getCell((short)7);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("��Ʒƽ̨") != null)
               cell.setCellValue(map.get("��Ʒƽ̨").toString());
           // ÿ������
           cell = row.getCell((short)9);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("ÿ������") != null)
               cell.setCellValue(map.get("ÿ������").toString());
           // ��ҳ
           /*cell = row.getCell((short)9);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("��  " + pageNumber + "  ҳ");*/

           HSSFSheet sheet1 = null; // �����ҳ��
            //if(pageNumber > 1)
            //sheet1 = wb.cloneSheet(0); // �����ҳ��


           
           // ������ܽ��
           int begin = 0;
           int end = 17;
           String[] line;
           for(int i = 1;i <= pageNumber;i++)
           {
System.out.println(i+"==========="+pageNumber);
               if(pageNumber > 1 && i < pageNumber)
                   sheet1 = wb.cloneSheet(i - 1);// �����ҳ��
               /*if(i==1)
               {
               }
               else if(pageNumber > 1)
               {
               	 sheet = sheet1;
               	 wb.setSheetName(i - 1, "Sheet" + i);
               	 if(i < pageNumber)
               	 {
               	 	 sheet1 = wb.cloneSheet(i - 1);// �����ҳ��
               	 }
               }*/
               end = i * 17;
               //row = sheet.getRow(4);
               row = sheet.getRow(5);
               //cell = row.getCell((short)9);
               cell = row.getCell((short)12);
               //cell.setEncoding(cell.ENCODING_UTF_16);
               cell.setCellValue("��  " + i + "  ҳ");
               
               
           //���ͼ��  ����ģ���ͼƬ������쳣����˲������ͼƬ��������
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
           /* ��������˵��  ��1����Ԫ�� ָ����ͼƬ���ϵ㣬��2����Ԫ��ָ����ͼƬ���µ�
              dx1 	��1����Ԫ����x���ƫ����  ��Ա���
              dy1 	��1����Ԫ����y���ƫ����  ��Ա���
              dx2 	��2����Ԫ����x���ƫ����  ��Ա���
              dy2 	��2����Ԫ����y���ƫ����  ��Ա���
              col1 	��1����Ԫ����к�
              row1 	��1����Ԫ����к�
              col2 	��2����Ԫ����к�
              row2 	��2����Ԫ����к�
           */
           HSSFClientAnchor anchor = new HSSFClientAnchor(0,125,650,125,(short) 4,1,(short)4,2); 
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
           //ͼƬ��ӽ���
           
               
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
    	  if(type.contains("һ����"))
    		  url = templePath + "/��ݹ���һ����.xls";
    	  if(type.contains("��ϸ��"))
    		  url = templePath + "/��ݹ�����ϸ��.xls";
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

    	  HSSFSheet sheet = wb.getSheetAt(0); // ��õ�һ��ҳ��
    	  HSSFRow row;
    	  HSSFCell cell; // ��1�е�0��
    	  // ��ͷ

    	  row = sheet.getRow(2);
    	  // ���ձ��
    	  cell = row.getCell((short)2);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("���ձ��") != null)
    	  {
    		  cell.setCellValue("����   "+map.get("���ձ��").toString()+"   ��");
    	  }
    	  // �㲿������
    	  cell = row.getCell((short)6);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("�������") != null){
    		  cell.setCellValue(map.get("�������").toString());
    	  }
    	  // �㲿��ͼ��
    	  cell = row.getCell((short)8);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("���ͼ��") != null){
    		  cell.setCellValue(map.get("���ͼ��").toString());
    	  }
    	  row = sheet.getRow(3);
    	  // ����
    	  cell = row.getCell((short)2);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("����") != null){
    		  cell.setCellValue(map.get("����").toString()+"  ����");
    	  }
    	  // ����
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
    			  if(definition.getDisplayName().equals("�����ƺ�")){
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
    	  //          if(map.get("����") != null){
    	  //        	  cell.setCellValue(map.get("����").toString());
    	  //        	  System.out.println("���� = " + map.get("����").toString());
    	  //          }

    	  row = sheet.getRow(4);
    	  // ���
    	  cell = row.getCell((short)1);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
//    	  CCBegin SS13
    	  if(type.contains("��ϸ��"))
    		  type = type.replace("��ϸ��", "");
    	  if(type.contains("һ����"))
    		  type = type.replace("һ����", "");
    	  if(type.contains("�嵥"))
    		  type = type.replace("�嵥", "");
    	  type = type.replace("���", "");
//    	  CCEnd SS13
    	  cell.setCellValue(type);
    	  // ��ҳ
    	  cell = row.getCell((short)8);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  cell.setCellValue("��  " + pageNumber + "  ҳ");

    	  row = sheet.getRow(5);
    	  // ���
    	  cell = row.getCell((short)2);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("���") != null)
    		  cell.setCellValue(map.get("���").toString());
    	  //��������
    	  cell = row.getCell((short)3);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("��������") != null)
    		  cell.setCellValue(map.get("��������").toString());
    	  //�ܳ��ͺ�
    	  cell = row.getCell((short)4);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("�ܳ��ͺ�") != null)
    		  cell.setCellValue(map.get("�ܳ��ͺ�").toString());
    	  //ÿ������
    	  cell = row.getCell((short)6);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("ÿ������") != null)
    		  cell.setCellValue(map.get("ÿ������").toString());

    	  HSSFSheet sheet1 = null; // �����ҳ��
    	  // if(pageNumber > 1)
    	  // sheet1 = wb.cloneSheet(0); // �����ҳ��

    	  // ������ܽ��
    	  int begin = 0;
    	  int end = 18;
    	  String[] line;
    	  for(int i = 1;i <= pageNumber;i++)
    	  {
    		  if(pageNumber > 1 && i < pageNumber)
    			  sheet1 = wb.cloneSheet(i - 1);// �����ҳ��
    		  end = i * 17;
    		  row = sheet.getRow(5);
    		  cell = row.getCell((short)8);
    		  //cell.setEncoding(cell.ENCODING_UTF_16);
    		  cell.setCellValue("��  " + i + "  ҳ");
    		  
          //CCBegin SS12
           //���ͼ��  ����ģ���ͼƬ������쳣����˲������ͼƬ��������
           String urlroot = "http://" +
               RemoteProperty.getProperty("server.hostName", "") +
               RemoteProperty.getProperty("routeListTemplate",
                                          "/PhosphorPDM/template/");
           ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
           ByteArrayOutputStream byteArrayOut1 = new ByteArrayOutputStream();
           try
           {
           	 BufferedImage bufferImg = ImageIO.read(new URL(urlroot+"������ĳ���.jpg"));
           	 ImageIO.write(bufferImg,"jpg",byteArrayOut);
           	 
           	 BufferedImage bufferImg1 = ImageIO.read(new URL(urlroot+"�����������.jpg"));
           	 ImageIO.write(bufferImg1,"jpg",byteArrayOut1);
           }
           catch (IOException e) 
           {
             e.printStackTrace();
           }          
           HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
           /* ��������˵��  ��1����Ԫ�� ָ����ͼƬ���ϵ㣬��2����Ԫ��ָ����ͼƬ���µ�
              dx1 	��1����Ԫ����x���ƫ����  ��Ա���
              dy1 	��1����Ԫ����y���ƫ����  ��Ա���
              dx2 	��2����Ԫ����x���ƫ����  ��Ա���
              dy2 	��2����Ԫ����y���ƫ����  ��Ա���
              col1 	��1����Ԫ����к�
              row1 	��1����Ԫ����к�
              col2 	��2����Ԫ����к�
              row2 	��2����Ԫ����к�
           */
          if(type.contains("һ����"))
          {
           HSSFClientAnchor anchor = new HSSFClientAnchor(360,5,785,240,(short) 0,1,(short)1,2);
           HSSFClientAnchor anchor1 = new HSSFClientAnchor(550,240,500,245,(short) 0,2,(short)1,3);
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
           patriarch.createPicture(anchor1 , wb.addPicture(byteArrayOut1.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
          }
          else if(type.contains("��ϸ��"))
          {
           HSSFClientAnchor anchor = new HSSFClientAnchor(110,80,880,110,(short) 1,1,(short)1,2);
           HSSFClientAnchor anchor1 = new HSSFClientAnchor(0,80,1020,110,(short) 0,1,(short)0,2);
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
           patriarch.createPicture(anchor1 , wb.addPicture(byteArrayOut1.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
          }
           //ͼƬ��ӽ���
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
    	  String url = templePath + "/" + "����豸�嵥.xls";
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

    	  HSSFSheet sheet = wb.getSheetAt(0); // ��õ�һ��ҳ��
    	  HSSFRow row;
    	  HSSFCell cell; // ��1�е�0��
    	  // ��ͷ

    	  row = sheet.getRow(1);
    	  // �ܳ��ͺ�
    	  cell = row.getCell((short)9);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(titlemap.get("�ܳ��ͺ�") != null)
    	  {
    		  cell.setCellValue(titlemap.get("�ܳ��ͺ�").toString());
    	  }
    	  row = sheet.getRow(2);
    	  // �㲿�����
    	  cell = row.getCell((short)9);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(titlemap.get("���ͼ��") != null)
    	  {
    		  cell.setCellValue(titlemap.get("���ͼ��").toString());
    	  }
    	  //�Ʊ�����
    	  SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    	  String a1=formatter.format(new Date());
    	  cell = row.getCell((short)10);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  cell.setCellValue(a1);
    	  // ��ҳ
    	  cell = row.getCell((short)11);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  cell.setCellValue("��  " + pageNumber + "  ҳ");
    	  
    	  row = sheet.getRow(3);
    	// ����
    	  cell = row.getCell((short)2);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(titlemap.get("����") != null)
    		  cell.setCellValue(titlemap.get("����").toString());
    	  // �㲿������
    	  cell = row.getCell((short)9);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(titlemap.get("�������") != null)
    		  cell.setCellValue(titlemap.get("�������").toString());
    	  
    	  HSSFSheet sheet1 = null; // �����ҳ��
    	  // if(pageNumber > 1)
    	  // sheet1 = wb.cloneSheet(0); // �����ҳ��

    	  // ������ܽ��
    	  int begin = 0;
    	  int end = 21;
    	  String[] line;
    	  for(int i = 1;i <= pageNumber;i++)
    	  {
    		  if(pageNumber > 1 && i < pageNumber)
    			  sheet1 = wb.cloneSheet(i - 1);// �����ҳ��
    		  end = i * 21;
    		  row = sheet.getRow(1);
    		  cell = row.getCell((short)11);
    		  //cell.setEncoding(cell.ENCODING_UTF_16);
    		  cell.setCellValue("��  " + i + "  ҳ");
    		  
          //CCBegin SS12
           //���ͼ��  ����ģ���ͼƬ������쳣����˲������ͼƬ��������
           String urlroot = "http://" +
               RemoteProperty.getProperty("server.hostName", "") +
               RemoteProperty.getProperty("routeListTemplate",
                                          "/PhosphorPDM/template/");
           ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
           ByteArrayOutputStream byteArrayOut1 = new ByteArrayOutputStream();
           try
           {
           	 BufferedImage bufferImg = ImageIO.read(new URL(urlroot+"������ĳ���.jpg"));
           	 ImageIO.write(bufferImg,"jpg",byteArrayOut);
           	 
           	 BufferedImage bufferImg1 = ImageIO.read(new URL(urlroot+"�����������.jpg"));
           	 ImageIO.write(bufferImg1,"jpg",byteArrayOut1);
           }
           catch (IOException e) 
           {
             e.printStackTrace();
           }          
           HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
           /* ��������˵��  ��1����Ԫ�� ָ����ͼƬ���ϵ㣬��2����Ԫ��ָ����ͼƬ���µ�
              dx1 	��1����Ԫ����x���ƫ����  ��Ա���
              dy1 	��1����Ԫ����y���ƫ����  ��Ա���
              dx2 	��2����Ԫ����x���ƫ����  ��Ա���
              dy2 	��2����Ԫ����y���ƫ����  ��Ա���
              col1 	��1����Ԫ����к�
              row1 	��1����Ԫ����к�
              col2 	��2����Ԫ����к�
              row2 	��2����Ԫ����к�
           */
           HSSFClientAnchor anchor = new HSSFClientAnchor(30,80,880,180,(short) 1,1,(short)2,2);
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
          
           //ͼƬ��ӽ���
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
    * ?? ĥ��һ����
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
           String url = templePath + "/" + "ĥ��һ����.xls";
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

           HSSFSheet sheet = wb.getSheetAt(0); // ��õ�һ��ҳ��
           HSSFRow row;
           HSSFCell cell; // ��1�е�0��
           // ��ͷ

           row = sheet.getRow(2);
           // �㲿�����
           //CCBegin SS7
           //cell = row.getCell((short)7);
           cell = row.getCell((short)12);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("�����") != null)
           {
               cell.setCellValue(map.get("�����").toString());
               System.out.println("cappclienthelp�� ����� = " + map.get("�����").toString());
           }
           // �㲿������
           //cell = row.getCell((short)9);
           cell = row.getCell((short)15);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("�������") != null)
               cell.setCellValue(map.get("�������").toString());
           // ����
           /*cell = row.getCell((short)11);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("����") != null)
               cell.setCellValue(map.get("����").toString());*/
           
           //row = sheet.getRow(3);
           // �ں�
              /*cell = row.getCell((short)3);
              //cell.setEncoding(cell.ENCODING_UTF_16);
              if(map.get("�ĵ����") != null)
                  cell.setCellValue("��  " + map.get("�ĵ����").toString() + "  ��");*/
              
           row = sheet.getRow(4);
           // ��ҳ
           cell = row.getCell((short)15);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("��  " + pageNumber + "  ҳ");
           
           row = sheet.getRow(5);
           // ���
           cell = row.getCell((short)0);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("���") != null)
               cell.setCellValue(map.get("���").toString());
           // ����
           cell = row.getCell((short)5);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("����") != null)
               cell.setCellValue(map.get("����").toString());
           // ��Ʒƽ̨
           cell = row.getCell((short)9);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("��Ʒƽ̨") != null)
               cell.setCellValue(map.get("��Ʒƽ̨").toString());
           // ÿ������
           cell = row.getCell((short)12);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("ÿ������") != null)
               cell.setCellValue(map.get("ÿ������").toString());
           // ��ҳ
           /*cell = row.getCell((short)11);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("��  " + pageNumber + "  ҳ");*/

           HSSFSheet sheet1 = null; // �����ҳ��
           // if(pageNumber > 1)
           // sheet1 = wb.cloneSheet(0); // �����ҳ��

           // ������ܽ��
           int begin = 0;
           int end = 17;
           String[] line;
           for(int i = 1;i <= pageNumber;i++)
           {
               if(pageNumber > 1 && i < pageNumber)
                   sheet1 = wb.cloneSheet(i - 1);// �����ҳ��
               end = i * 17;
               //row = sheet.getRow(4);
               row = sheet.getRow(5);
               //cell = row.getCell((short)11);
               cell = row.getCell((short)15);
               //cell.setEncoding(cell.ENCODING_UTF_16);
               cell.setCellValue("��  " + i + "  ҳ");
               
               
           //���ͼ��  ����ģ���ͼƬ������쳣����˲������ͼƬ��������
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
           /* ��������˵��  ��1����Ԫ�� ָ����ͼƬ���ϵ㣬��2����Ԫ��ָ����ͼƬ���µ�
              dx1 	��1����Ԫ����x���ƫ����  ��Ա���
              dy1 	��1����Ԫ����y���ƫ����  ��Ա���
              dx2 	��2����Ԫ����x���ƫ����  ��Ա���
              dy2 	��2����Ԫ����y���ƫ����  ��Ա���
              col1 	��1����Ԫ����к�
              row1 	��1����Ԫ����к�
              col2 	��2����Ԫ����к�
              row2 	��2����Ԫ����к�
           */
           HSSFClientAnchor anchor = new HSSFClientAnchor(700,125,750,125,(short) 5,1,(short)6,2); 
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
           //ͼƬ��ӽ���
           
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
    * �豸�嵥
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
       String url = templePath + "/" + "�豸�嵥.xls";
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

       HSSFSheet sheet = wb.getSheetAt(0); // ��õ�һ��ҳ��
       HSSFRow row;
       HSSFCell cell; // ��1�е�0��
       // ��ͷ

       row = sheet.getRow(2);
       // �㲿�����
       //cell = row.getCell((short)5);
       cell = row.getCell((short)8);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(titlemap.get("�����") != null)
       {
           cell.setCellValue(titlemap.get("�����").toString());
           System.out.println("cappclienthelp�� ����� = " + titlemap.get("�����").toString());
       }
       // �㲿������
       //cell = row.getCell((short)6);
       cell = row.getCell((short)10);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(titlemap.get("�������") != null)
           cell.setCellValue(titlemap.get("�������").toString());
       // ����
       /*cell = row.getCell((short)7);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(titlemap.get("����") != null)
           cell.setCellValue(titlemap.get("����").toString());*/

       //row = sheet.getRow(3);
       // �ں�
          /*cell = row.getCell((short)2);
          //cell.setEncoding(cell.ENCODING_UTF_16);
          if(titlemap.get("�ĵ����") != null)
              cell.setCellValue("��  " + titlemap.get("�ĵ����").toString() + "  ��");*/
          
       row = sheet.getRow(4);
       // ��ҳ
       cell = row.getCell((short)10);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       cell.setCellValue("��  " + pageNumber + "  ҳ");
       
       row = sheet.getRow(5);
       
       // ���
       cell = row.getCell((short)0);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(titlemap.get("���") != null)
         cell.setCellValue(titlemap.get("���").toString());
       
       // ����
       cell = row.getCell((short)2);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(titlemap.get("����") != null)
           cell.setCellValue(titlemap.get("����").toString());
       // ��Ʒƽ̨
       cell = row.getCell((short)5);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(titlemap.get("��Ʒƽ̨") != null)
           cell.setCellValue(titlemap.get("��Ʒƽ̨").toString());
       // ÿ������
       cell = row.getCell((short)8);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(titlemap.get("ÿ������") != null)
           cell.setCellValue(titlemap.get("ÿ������").toString());
       // ��ҳ
       /*cell = row.getCell((short)6);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       cell.setCellValue("��  " + pageNumber + "  ҳ");*/

       HSSFSheet sheet1 = null; // �����ҳ��
       // if(pageNumber > 1)
       // sheet1 = wb.cloneSheet(0); // �����ҳ��

       // ������ܽ��
       int begin = 0;
       //int end = 17;
       int end = 15;
       String[] line;
       for(int i = 1;i <= pageNumber;i++)
       {
           if(pageNumber > 1 && i < pageNumber)
               sheet1 = wb.cloneSheet(i - 1);// �����ҳ��
           //end = i * 17;
           end = i * 15;
           //row = sheet.getRow(4);
           row = sheet.getRow(5);
           //cell = row.getCell((short)6);
           cell = row.getCell((short)10);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("��  " + i + "  ҳ");
           
           //���ͼ��  ����ģ���ͼƬ������쳣����˲������ͼƬ��������
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
           /* ��������˵��  ��1����Ԫ�� ָ����ͼƬ���ϵ㣬��2����Ԫ��ָ����ͼƬ���µ�
              dx1 	��1����Ԫ����x���ƫ����  ��Ա���
              dy1 	��1����Ԫ����y���ƫ����  ��Ա���
              dx2 	��2����Ԫ����x���ƫ����  ��Ա���
              dy2 	��2����Ԫ����y���ƫ����  ��Ա���
              col1 	��1����Ԫ����к�
              row1 	��1����Ԫ����к�
              col2 	��2����Ԫ����к�
              row2 	��2����Ԫ����к�
           */
           HSSFClientAnchor anchor = new HSSFClientAnchor(260,165,1023,253,(short) 3,1,(short)3,2); 
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
           //ͼƬ��ӽ���
           
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
 * �о���ϸ��
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
        String url = templePath + "/" + "�о���ϸ��.xls";
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

        HSSFSheet sheet = wb.getSheetAt(0); // ��õ�һ��ҳ��
        HSSFRow row;
        HSSFCell cell; // ��1�е�0��
        // ��ͷ

        row = sheet.getRow(2);
        // �㲿�����
        //cell = row.getCell((short)5);
        cell = row.getCell((short)9);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        if(map.get("�����") != null)
        {
            cell.setCellValue(map.get("�����").toString());
            System.out.println("cappclienthelp�� ����� = " + map.get("�����").toString());
        }
        // �㲿������
        //cell = row.getCell((short)6);
        cell = row.getCell((short)12);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        if(map.get("�������") != null)
            cell.setCellValue(map.get("�������").toString());
        
        // ����
        /*cell = row.getCell((short)8);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        if(map.get("����") != null)
            cell.setCellValue(map.get("����").toString());*/
        
        row = sheet.getRow(3);
        // �ں�
           /*cell = row.getCell((short)2);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           if(map.get("�ĵ����") != null)
               cell.setCellValue("��  " + map.get("�ĵ����").toString() + "  ��");*/
        
        row = sheet.getRow(4);
        // ��ҳ
        cell = row.getCell((short)12);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        cell.setCellValue("��  " + pageNumber + "  ҳ");
        
        row = sheet.getRow(5);
    	  // ���
    	  cell = row.getCell((short)0);
    	  //cell.setEncoding(cell.ENCODING_UTF_16);
    	  if(map.get("���") != null)
    		  cell.setCellValue(map.get("���").toString());
        // ����
        cell = row.getCell((short)4);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        if(map.get("����") != null)
            cell.setCellValue(map.get("����").toString());
        
        // ��Ʒƽ̨
        cell = row.getCell((short)7);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        if(map.get("��Ʒƽ̨") != null)
            cell.setCellValue(map.get("��Ʒƽ̨").toString());
        // ÿ������
        cell = row.getCell((short)9);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        if(map.get("ÿ������") != null)
            cell.setCellValue(map.get("ÿ������").toString());
        
        // ��ҳ
        /*cell = row.getCell((short)7);
        //cell.setEncoding(cell.ENCODING_UTF_16);
        cell.setCellValue("��  " + pageNumber + "  ҳ");*/

        HSSFSheet sheet1 = null; // �����ҳ��
        // if(pageNumber > 1)
        // sheet1 = wb.cloneSheet(0); // �����ҳ��

        // ������ܽ��
        int begin = 0;
        //int end = 17;
        int end = 15;// ��15����ϸ
        String[] line;
        for(int i = 1;i <= pageNumber;i++)
        {
            if(pageNumber > 1 && i < pageNumber)
                sheet1 = wb.cloneSheet(i - 1);// �����ҳ��
            //end = i * 17;
            end = i * 15;
            //row = sheet.getRow(4);
            //cell = row.getCell((short)7);
            row = sheet.getRow(5);
            cell = row.getCell((short)12);
            //cell.setEncoding(cell.ENCODING_UTF_16);
            cell.setCellValue("��  " + i + "  ҳ");
            
            
           //���ͼ��  ����ģ���ͼƬ������쳣����˲������ͼƬ��������
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
           /* ��������˵��  ��1����Ԫ�� ָ����ͼƬ���ϵ㣬��2����Ԫ��ָ����ͼƬ���µ�
              dx1 	��1����Ԫ����x���ƫ����  ��Ա���
              dy1 	��1����Ԫ����y���ƫ����  ��Ա���
              dx2 	��2����Ԫ����x���ƫ����  ��Ա���
              dy2 	��2����Ԫ����y���ƫ����  ��Ա���
              col1 	��1����Ԫ����к�
              row1 	��1����Ԫ����к�
              col2 	��2����Ԫ����к�
              row2 	��2����Ԫ����к�
           */
           HSSFClientAnchor anchor = new HSSFClientAnchor(0,170,700,200,(short) 5,1,(short)5,2); 
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
           //ͼƬ��ӽ���
           
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
    * ����ģ���嵥
    */
   public static HSSFWorkbook ctMoJuList(String templePath, Vector record, HashMap map) throws IOException{
	   
	   String url = templePath + "����ģ���嵥.xls";
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

       HSSFSheet sheet = wb.getSheetAt(0); // ��õ�һ��ҳ��
       HSSFRow row;
       HSSFCell cell; // ��1�е�0��
       // ��ͷ

       row = sheet.getRow(2);
       // �㲿�����
       cell = row.getCell((short)9);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("������") != null)
       {
           cell.setCellValue(map.get("������").toString());
           System.out.println("cappclienthelp�� ����� = " + map.get("������").toString());
       }
       // �㲿������
       row = sheet.getRow(2);
       cell = row.getCell((short)6);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("�������") != null)
           cell.setCellValue(map.get("�������").toString());
       // ����
       row = sheet.getRow(3);
       cell = row.getCell((short)9);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("���ϱ��") != null&&map.get("��������") != null)
           cell.setCellValue("����:"+map.get("���ϱ��").toString()+"/"+map.get("��������").toString());
       
          
       row = sheet.getRow(4);
       // ���
       cell = row.getCell((short)3);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("���") != null)
           cell.setCellValue(map.get("���").toString());
       // ����
       cell = row.getCell((short)6);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("��������") != null)
           cell.setCellValue(map.get("��������").toString());
       
       // ��ҳ
       cell = row.getCell((short)9);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       cell.setCellValue("��  " + pageNumber + "  ҳ");

       HSSFSheet sheet1 = null; // �����ҳ��
       System.out.println("pageNumber=========================="+pageNumber);
       // ������ܽ��
       int begin = 0;
       int end = 18;
       String[] line;
       for(int i = 1;i <= pageNumber;i++)
       {
           if(pageNumber > 1 && i < pageNumber)
               sheet1 = wb.cloneSheet(i - 1);// �����ҳ��
           end = i * 18;
           
           row = sheet.getRow(4);
           cell = row.getCell((short)10);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("��  " + i + "  ҳ");
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
    * �����豸�嵥
    */
   public static HSSFWorkbook  ctEqList(String templePath, Vector record, HashMap map) throws IOException{
	   
	   String url = templePath + "�����豸�嵥.xls";
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

       HSSFSheet sheet = wb.getSheetAt(0); // ��õ�һ��ҳ��
       HSSFRow row;
       HSSFCell cell; // ��1�е�0��
       // ��ͷ

    // �㲿������
       row = sheet.getRow(1);
       cell = row.getCell((short)7);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("�������") != null)
           cell.setCellValue(map.get("�������").toString());
       
       
       row = sheet.getRow(2);
       // �㲿�����
       cell = row.getCell((short)7);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("������") != null)
       {
           cell.setCellValue(map.get("������").toString());
           System.out.println("cappclienthelp�� ����� = " + map.get("������").toString());
       }
       
       // ����
       row = sheet.getRow(3);
       cell = row.getCell((short)7);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("����") != null)
           cell.setCellValue(map.get("����").toString());
       
          
       row = sheet.getRow(4);
       // ������
       cell = row.getCell((short)7);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("������") != null)
           cell.setCellValue("������");
       row = sheet.getRow(5);
//       // ����
//       cell = row.getCell((short)1);
//       //cell.setEncoding(cell.ENCODING_UTF_16);
//       if(map.get("����") != null)
//           cell.setCellValue(map.get("����").toString());
       //���
       cell = row.getCell((short)2);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("���") != null)
           cell.setCellValue(map.get("���").toString());
       //��������
       cell = row.getCell((short)6);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("��������") != null)
           cell.setCellValue(map.get("��������").toString());
       
       // ��ҳ
       cell = row.getCell((short)7);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       cell.setCellValue("��  " + pageNumber + "  ҳ");

       HSSFSheet sheet1 = null; // �����ҳ��
 
       // ������ܽ��
       int begin = 0;
       int end = 16;
       String[] line;
       for(int i = 1;i <= pageNumber;i++)
       {
           if(pageNumber > 1 && i < pageNumber)
               sheet1 = wb.cloneSheet(i - 1);// �����ҳ��
           end = i * 16;
           
           row = sheet.getRow(6);
           cell = row.getCell((short)7);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("��  " + i + "  ҳ");
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
    * ���ع�װ��ϸ
    * @param templePath
    * @param record
    * @param map
    * @return
    * @throws IOException
    */
   public static HSSFWorkbook ctToolMingXi(String templePath, Vector record, HashMap map) throws IOException{

	   
	   String url = templePath + "���ع�װ��ϸ��.xls";
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

       HSSFSheet sheet = wb.getSheetAt(0); // ��õ�һ��ҳ��
       HSSFRow row;
       HSSFCell cell; // ��1�е�0��
       // ��ͷ

       row = sheet.getRow(1);
       // �㲿�����
       cell = row.getCell((short)12);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("������") != null)
       {
           cell.setCellValue(map.get("������").toString());
           System.out.println("cappclienthelp�� ����� = " + map.get("������").toString());
       }
       // �㲿������
       row = sheet.getRow(2);
       cell = row.getCell((short)12);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("�������") != null)
           cell.setCellValue(map.get("�������").toString());
       // ����
       row = sheet.getRow(3);
       cell = row.getCell((short)12);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("����") != null)
           cell.setCellValue(map.get("����").toString());
       
          
       row = sheet.getRow(4);
       // ������
       cell = row.getCell((short)12);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("������") != null)
           cell.setCellValue("������");
       // ����
       row = sheet.getRow(5);
       cell = row.getCell((short)1);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("����") != null)
           cell.setCellValue(map.get("����").toString());
       //���
       cell = row.getCell((short)5);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("���") != null)
           cell.setCellValue(map.get("���").toString());
       //��������
       cell = row.getCell((short)9);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("��������") != null)
           cell.setCellValue(map.get("��������").toString());
       
       // ��ҳ
       cell = row.getCell((short)12);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       cell.setCellValue("��  " + pageNumber + "  ҳ");

       HSSFSheet sheet1 = null; // �����ҳ��
 
       // ������ܽ��
       int begin = 0;
       int end = 14;
       String[] line;
       for(int i = 1;i <= pageNumber;i++)
       {
           if(pageNumber > 1 && i < pageNumber)
               sheet1 = wb.cloneSheet(i - 1);// �����ҳ��
           end = i * 14;
           
           row = sheet.getRow(6);
           cell = row.getCell((short)12);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("��  " + i + "  ҳ");
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
    * ���ܹ����嵥
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
       //String url = templePath + "/" + "���ܹ����嵥.xls";
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

       HSSFSheet sheet = wb.getSheetAt(0); // ��õ�һ��ҳ��
       HSSFRow row;
       HSSFCell cell; // ��1�е�0��
       // ��ͷ

       row = sheet.getRow(2);
       // �㲿�����
       //CCBegin SS7
       //cell = row.getCell((short)5);
       cell = row.getCell((short)10);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("�����") != null)
       {
           cell.setCellValue(map.get("�����").toString());
           System.out.println("cappclienthelp�� ����� = " + map.get("�����").toString());
       }
       // �㲿������
       //cell = row.getCell((short)7);
       cell = row.getCell((short)13);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("�������") != null)
           cell.setCellValue(map.get("�������").toString());
       // ����
       /*cell = row.getCell((short)8);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("����") != null)
           cell.setCellValue(map.get("����").toString());*/
       
       //row = sheet.getRow(3);
       // �ں�
          /*cell = row.getCell((short)2);
          //cell.setEncoding(cell.ENCODING_UTF_16);
          if(map.get("�ĵ����") != null)
              cell.setCellValue("��  " + map.get("�ĵ����").toString() + "  ��");*/
          
       row = sheet.getRow(4);
       // ��ҳ
       cell = row.getCell((short)13);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       cell.setCellValue("��  " + pageNumber + "  ҳ");
       
       row = sheet.getRow(5);
       
       // ���
       cell = row.getCell((short)3);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("���") != null)
         cell.setCellValue(map.get("���").toString());
       
       // ����
       cell = row.getCell((short)6);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("����") != null)
           cell.setCellValue(map.get("����").toString());
           
       // ��Ʒƽ̨
       //cell = row.getCell((short)5);
       cell = row.getCell((short)8);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("��Ʒƽ̨") != null)
           cell.setCellValue(map.get("��Ʒƽ̨").toString());
       // ÿ������
       //cell = row.getCell((short)7);
       cell = row.getCell((short)10);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       if(map.get("ÿ������") != null)
           cell.setCellValue(map.get("ÿ������").toString());
       // ��ҳ
       /*cell = row.getCell((short)8);
       //cell.setEncoding(cell.ENCODING_UTF_16);
       cell.setCellValue("��  " + pageNumber + "  ҳ");*/

       HSSFSheet sheet1 = null; // �����ҳ��
 
       // ������ܽ��
       int begin = 0;
       int end = 17;
       String[] line;
       for(int i = 1;i <= pageNumber;i++)
       {
           if(pageNumber > 1 && i < pageNumber)
               sheet1 = wb.cloneSheet(i - 1);// �����ҳ��
           end = i * 17;
           //row = sheet.getRow(4);
           row = sheet.getRow(5);
           //cell = row.getCell((short)8);
           cell = row.getCell((short)13);
           //cell.setEncoding(cell.ENCODING_UTF_16);
           cell.setCellValue("��  " + i + "  ҳ");
           
           //���ͼ��  ����ģ���ͼƬ������쳣����˲������ͼƬ��������
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
           /* ��������˵��  ��1����Ԫ�� ָ����ͼƬ���ϵ㣬��2����Ԫ��ָ����ͼƬ���µ�
              dx1 	��1����Ԫ����x���ƫ����  ��Ա���
              dy1 	��1����Ԫ����y���ƫ����  ��Ա���
              dx2 	��2����Ԫ����x���ƫ����  ��Ա���
              dy2 	��2����Ԫ����y���ƫ����  ��Ա���
              col1 	��1����Ԫ����к�
              row1 	��1����Ԫ����к�
              col2 	��2����Ԫ����к�
              row2 	��2����Ԫ����к�
           */
           HSSFClientAnchor anchor = new HSSFClientAnchor(250,125,1023,125,(short) 4,1,(short)4,2); 
           patriarch.createPicture(anchor , wb.addPicture(byteArrayOut.toByteArray(),HSSFWorkbook.PICTURE_TYPE_JPEG));
           //ͼƬ��ӽ���
           
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
 			if (groupName.equals("�������û���")) 
 			{
 				return true;
 			}
 		}
 		return false;
 	}

   /**
    * �ж��û�������˾
    * @return String ����û�������˾
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
	   Object[] paraobj1 = {tech.getBsoID(),"�����ļ����֪ͨ��"};
	   //CCBegin SS6
//	   ZCZXUtil zc = new ZCZXUtil();
//	   num = zc.getSerialNum(tech.getBsoID(), "�����ļ����֪ͨ��");
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

		   HSSFSheet sheet = wb.getSheetAt(0); // ��õ�һ��ҳ��
		   HSSFRow row;
		   HSSFCell cell; // ��1�е�0��
		   // ��ͷ

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
		   cell.setCellValue("��  " + pageNumber + "  ҳ");
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

		   HSSFSheet sheet1 = null; // �����ҳ��

		   int begin = 0;
		   int end = 14;
		   String[] line;
		   for(int i = 1;i <= pageNumber;i++)
		   {
			   if(pageNumber > 1 && i < pageNumber)
				   sheet1 = wb.cloneSheet(i - 1);// �����ҳ��
			   end = i * 14;

			   row = sheet.getRow(1);
			   cell = row.getCell((short)15);
			   //cell.setEncoding(cell.ENCODING_UTF_16);
			   cell.setCellValue("��  " + i + "  ҳ");

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
		   // ���·���Ƿ���ڣ��������򴴽�
		   String filePName = clientPath + "/�����ļ������.xls";
		   File file = new File(clientPath);
		   // ����ļ��в������򴴽�
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
		   // ���������ļ�
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
			   Object[] paraobj = {tech.getBsoID(),"�����ļ����֪ͨ��",appDataInfo,num};
			   doc = (DocInfo) useServiceMethod("StandardCappService", "createFile", paraclass,paraobj);
		   }
		   else
		   {
			   // ����ApplicationDataInfo
			   ApplicationDataInfo appDataInfo = new ApplicationDataInfo();
			   FileInputStream input = null;
			   byte[] filebyte = null;

			   try
			   {
				   input = new FileInputStream(file);
			   }catch(FileNotFoundException e)
			   {
				   JOptionPane.showConfirmDialog(null, file+"������һӦ�ó���ʹ�ã���ر��ļ�","��ʾ", JOptionPane.INFORMATION_MESSAGE);
				   e.printStackTrace();
			   }

			   appDataInfo.setFileName(file.getName());
			   appDataInfo.setUploadPath(file.getPath());

			   ByteArrayOutputStream out = new ByteArrayOutputStream();
			   //  һ�ζ�ȡ1024�ֽ� 
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
			   // �����ļ���b
			   filebyte = out.toByteArray();
			   appDataInfo.setFileSize(filebyte.length);
			   // �����ĵ�
			   StandardCappService scs = null;
			   Class[] paraclass = {String.class,String.class,ApplicationDataInfo.class,byte[].class,String.class};
			   Object[] paraobj = {tech.getBsoID(),"�����ļ����֪ͨ��",appDataInfo,filebyte,num};
			   doc = (DocInfo) useServiceMethod("StandardCappService", "createFile", paraclass,paraobj);
		   }
	   }
   }
   //CCEnd SS4
}
