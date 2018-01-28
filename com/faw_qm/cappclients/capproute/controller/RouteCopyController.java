/**
 * ���ɳ��� RouteCopyController.java    1.0    2004/02/19
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cappclients.capproute.controller;

import com.faw_qm.framework.remote.*;
import com.faw_qm.technics.route.model.*;

/**
 * <p>
 * Title:�Թ���·�߽��и���,ճ����û�й���·�ߵ��㲿���С�
 * </p>
 * ���ƹ���·��ʱ�����Դӵ�ǰ·�߱��е�һ���㲿���Ĺ���·�߸��ƣ�Ҳ���Դ�һ���㲿��������
 * ·�߱���ƵĹ���·�߸��ƣ�ճ��ʱ������ճ������ǰѡ�е��㲿����Ҳ����ճ������·�߱�����
 * ����·�ߵ��㲿��������㲿������·�ߣ�ʹ�á�ճ����������·��ʱ�����ܸ��Ƶ���Щ�㲿����
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: һ������
 * </p>
 *
 * @author ����
 * @repare skybird
 * @version 1.0
 */
public class RouteCopyController extends CappRouteAction {
    /** Դ·��BsoID */
    private String routeID;

    /** Ŀ���㲿������ */
    private ListRoutePartLinkIfc objectLinkInfo;

    /**
     * ���캯��
     *
     * @roseuid 403189BB0011
     */
    public RouteCopyController() {
    }

    /**
     * ����Դ·��
     *
     * @param routeBsoID
     *            Դ·��BsoID
     */
    public void setOriginalRoute(String routeBsoID) {
        routeID = routeBsoID;
    }

    /**
     * ����Ŀ���㲿������
     *
     * @param linkInfo
     *            Ŀ���㲿������
     */
    public void setObjectPartLink(ListRoutePartLinkIfc linkInfo) {
        objectLinkInfo = linkInfo;
    }

    /**
     * ���÷���,��Դ·��ճ����Ŀ���㲿����
     *
     * @return �Ƿ��Ƴɹ�
     * @throws QMRemoteException
     */
    public boolean copy() throws QMRemoteException {
        boolean success = false;
        if (routeID != null && objectLinkInfo != null) {
            Class[] c = { String.class, ListRoutePartLinkIfc.class };
            Object[] objs = { routeID, objectLinkInfo };
            useServiceMethod("TechnicsRouteService", "copyRoute", c, objs);
            success = true;
        }
        return success;
    }

}
