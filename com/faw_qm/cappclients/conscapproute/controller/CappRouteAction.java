/**
 * ���ɳ��� CappRouteAction.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.conscapproute.controller;

import javax.swing.JFrame;
import com.faw_qm.framework.remote.*;
import com.faw_qm.cappclients.capp.util.*;
import com.faw_qm.clients.util.QMThread;
import com.faw_qm.framework.service.*;
import com.faw_qm.identity.*;

/**
 * <p> Title:������ </p> <p> Description: </p> <p> Copyright: Copyright (c) 2004 </p> <p> Company: һ������ </p>
 * @author ����
 * @version 1.0
 */
public class CappRouteAction extends QMThread
{
    private JFrame parentFrame;

    public static String RESOURCE = "com.faw_qm.cappclients.conscapproute.util.CappRouteRB";

    /** ���ڴ������ */
    public static boolean verbose = (RemoteProperty.getProperty("com.faw_qm.cappclients.verbose", "true")).equals("true");

    /**
     * ���캯��
     * @roseuid 403189BA02EB
     */
    public CappRouteAction()
    {

    }

    /**
     * ���������ڿͻ���Զ�̵��÷���˷���
     * @param serviceName Ҫ���õķ���������
     * @param methodName Ҫ���õķ��񷽷�����
     * @param paraClass Ҫ���õķ��񷽷��Ĳ���������
     * @param paraObject Ҫ���õķ��񷽷��Ĳ���ֵ
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
     * ���ø�����
     * @param parent ������
     * @roseuid 4031603F0147
     */
    public void setParentJFrame(JFrame parent)
    {
        parentFrame = parent;
    }

    /**
     * ��ø�����
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
     * ���ָ��ҵ�����Ķ������ͺͱ�ʶ
     * @param obj ҵ�����
     * @return �������ͺͱ�ʶ
     */
    public static String getIdentity(Object obj)
    {
        String s = "";
        if(obj instanceof BaseValueInfo)
        {
            //ͨ����ʶ������������ֵ�����Ӧ����ʾ��ʶ����
            DisplayIdentity displayidentity = IdentityFactory.getDisplayIdentity(obj);
            //��ö������� + ��ʶ
            s = displayidentity.getLocalizedMessage(null).trim();

        }
        return s;
    }

}