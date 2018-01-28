/**
 * 生成程序 RouteCopyController.java    1.0    2004/02/19
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.cappclients.capproute.controller;

import com.faw_qm.framework.remote.*;
import com.faw_qm.technics.route.model.*;

/**
 * <p>
 * Title:对工艺路线进行复制,粘贴到没有工艺路线的零部件中。
 * </p>
 * 复制工艺路线时，可以从当前路线表中的一个零部件的工艺路线复制，也可以从一个零部件的其它
 * 路线表编制的工艺路线复制；粘贴时，可以粘贴到当前选中的零部件，也可以粘贴到本路线表中其
 * 它无路线的零部件；如果零部件已有路线，使用“粘贴到”复制路线时，不能复制到这些零部件。
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: 一汽启明
 * </p>
 *
 * @author 刘明
 * @repare skybird
 * @version 1.0
 */
public class RouteCopyController extends CappRouteAction {
    /** 源路线BsoID */
    private String routeID;

    /** 目标零部件关联 */
    private ListRoutePartLinkIfc objectLinkInfo;

    /**
     * 构造函数
     *
     * @roseuid 403189BB0011
     */
    public RouteCopyController() {
    }

    /**
     * 设置源路线
     *
     * @param routeBsoID
     *            源路线BsoID
     */
    public void setOriginalRoute(String routeBsoID) {
        routeID = routeBsoID;
    }

    /**
     * 设置目标零部件关联
     *
     * @param linkInfo
     *            目标零部件关联
     */
    public void setObjectPartLink(ListRoutePartLinkIfc linkInfo) {
        objectLinkInfo = linkInfo;
    }

    /**
     * 调用服务,把源路线粘贴到目标零部件上
     *
     * @return 是否复制成功
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
