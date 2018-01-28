/**
 * 生成程序 RouteBranchNodeLinkIfc.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2011/12/22 徐春英       原因：增加预留属性
 */
package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BinaryLinkIfc;

/**
 * 路线串和节点的关联 <p>Title:leftID = branchID , rightID = routeNodeID </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company:faw_qm </p>
 * @author 管春元
 * @version 1.0
 */
public interface RouteBranchNodeLinkIfc extends BinaryLinkIfc
{
    /**
     * 得到路线分支值对象
     * @return TechnicsRouteBranchIfc
     */
    public TechnicsRouteBranchIfc getRouteBranchInfo();

    /**
     * 设置路线分支值对象
     */
    public void setRouteBranchInfo(TechnicsRouteBranchIfc branchInfo);

    /**
     * 得到路线节点值对象
     * @return RouteNodeIfc
     */
    public RouteNodeIfc getRouteNodeInfo();

    /**
     * 设置路线节点值对象
     */
    public void setRouteNodeInfo(RouteNodeIfc nodeInfo);

    //begin CR1
    /**
     * 设置预留属性1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1);

    /**
     * 获得预留属性1
     * @param attribute1
     * @return
     */
    public String getAttribute1();

    /**
     * 设置预留属性2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2);

    /**
     * 获得预留属性2
     * @param attribute2
     * @return
     */
    public String getAttribute2();
    //end CR1
}
