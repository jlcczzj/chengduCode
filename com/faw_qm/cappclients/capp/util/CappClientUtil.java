/** ���ɳ��� CappClientUtil.java    1.0    2003/09/05
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2009/10/26 �촺Ӣ ԭ�򣺲���EJB�ĵط�һ�ɸ�Ϊ����ֵ����
 * CR2 2009/11/02 ������ ԭ��ͳһ�ͻ��˻򹤾�����÷���ķ�ʽ
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
 * <p>Title:Ϊ�ͻ����ṩ���� </p>
 * <p>Description:�����ṩ��һϵ�о�̬�������Է���ͻ��˵��÷��� </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */

public class CappClientUtil
{
    /**������Ա���*/
    private static boolean verbose = (RemoteProperty.getProperty(
            "com.faw_qm.cappclients.verbose", "true")).equals("true");


    /**
     * ����������������
     */
    private static HashMap allSpechar;


    /**
     * ���캯��
     */
    public CappClientUtil()
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
     * ���ݳ־û���Ϣˢ��ҵ�����
     * ���ظ�������ֵ����
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
            //�������͵ļ���
        }
        Class[] paraClass =
                {BaseValueIfc.class};
        //����ֵ�ļ���
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
     * ���ݳ־û���Ϣˢ��ҵ�����
     * ���ظ�������ֵ����
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
            //�������͵ļ���
        }
        Class[] paraClass =
                {String.class};
        //����ֵ�ļ���
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
     * ��������Ĺ��տ�ֵ��������������ݿ���и��£����ظ��º�Ĺ��տ�ֵ����
     * @param technics QMFawTechnicsInfo ˢ��ǰ�Ĺ���ֵ����
     * @return QMFawTechnicsInfo ˢ��ǰ�Ĺ���ֵ����
     * @throws QMException 
     */
    public static QMFawTechnicsInfo refresh(QMFawTechnicsInfo technics) throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientUtil.refresh(2) begin...");
            //�������͵ļ���
        }
        Class[] paraClass =
                {BaseValueIfc.class};
        //����ֵ�ļ���
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
     * ɾ��ҵ�����
     * @param info Ҫɾ����ҵ�����
     * @throws QMException 
     * @exception QMRemoteException
     */
    public static void deleteBaseValue(BaseValueIfc info) throws QMException
    {
        if (verbose)
        {
            System.out.println(
                    "cappclients.capp.util.CappClientUtil.deleteBaseValue() begin...");
            //�������͵ļ���
        }
        Class[] paraClass =
                {BaseValueIfc.class};
        //����ֵ�ļ���
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
     * ���������ʱ�����������ȡ��"��-��-��"���֡�
     * @param timestamp �����ʱ���
     * @return String �ַ�����ʽ��"��-��-��"
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
     * ��ȡָ���������ָ�����Ե���󳤶ȡ�
     * @param class1 ָ������
     * @param s ָ�����ָ������
     * @return int ��󳤶�
     */
//    public static int getMaxLength(Class class1, String s)
//    {
//        int i = 0;
//
//        return i;
//    }


    /**
     * �жϵ�ǰ�û��Ƿ���з���Ȩ��
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
//        //���ûỰ�����õ�ǰ�û�
//        //Begin CR2
////        ServiceRequestInfo info1 = new ServiceRequestInfo();
////        info1.setServiceName("SessionService");
////        info1.setMethodName("getCurUser");
////        //�������͵ļ���
////        Class[] paraClass =
////                {};
////        info1.setParaClasses(paraClass);
////        //����ֵ�ļ���
////        Object[] objs =
////                {};
////        info1.setParaValues(objs);
//       
//        UserIfc user = null; //CR1
//        user = (UserIfc) RequestHelper.request("SsssionService", "getCurUser", 
//            		new Class[]{}, new Object[]{}); //CR1��End CR2
//        
//
//        //���÷��ʿ��Ʒ����жϵ�ǰ�û���ָ�����ࡢָ������
//        //ָ������������״̬�Ƿ����ָ���ķ��ʿ���Ȩ��
//        //Begin CR2
////        ServiceRequestInfo info2 = new ServiceRequestInfo();
////        info2.setServiceName("AccessControlService");
////        info2.setMethodName("hasAccess");
////        //�������͵ļ���
////        Class[] paraClass1 =
////                {ActorIfc.class,
////                String.class,
////                String.class,
////                LifeCycleState.class,
////                String.class
////        }; //CR1
////        info2.setParaClasses(paraClass);
////        //����ֵ�ļ���
////        Object[] objs1 =
////                {user, //�û�
////                className, //����
////                DomainHelper.SYSTEM_DOMAIN, //��
////                null, //״̬
////                permission //���ʿ���Ȩ��
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
     *��Ѱ���򣬹���ʹ�õ���Դ����
     *@param  bsoID �����bsoId
     *@return ʹ����Դ�����ļ���
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
