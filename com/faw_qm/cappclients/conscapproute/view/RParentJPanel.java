/**
 * ���ɳ��� RParentJPanel.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * Title:���湤���� Description: Copyright: Copyright (c) 2004 Company: һ������
 * @author ����
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
     * ���������ڿͻ���Զ�̵��÷���˷���
     * @param serviceName Ҫ���õķ���������
     * @param methodName Ҫ���õķ��񷽷�����
     * @param paraClass Ҫ���õķ��񷽷��Ĳ���������
     * @param paraObject Ҫ���õķ��񷽷��Ĳ���ֵ
     * @return �ӷ��񷽷���õķ���ֵ
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
     * ��ø�����
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
     * ����ҵ������ BsoID�����ֵ����
     * @param bsoID ҵ������ BsoID
     * @return ҵ������ֵ����
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
     * ���ݹ���·�߱�ˢ��ֵ�����ҵ����°汾
     * @param info ����·�߱�
     * @return ҵ������ֵ����
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

    /**
     * ʹָ����ҵ��������־û���Ϣ
     * @param bvi ҵ�����
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
