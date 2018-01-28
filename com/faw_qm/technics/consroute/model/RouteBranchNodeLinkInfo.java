/**
 * 生成程序RouteBranchNodeLinkInfo.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2011/12/22 徐春英       原因：增加预留属性
 */
package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BinaryLinkInfo;

/**
 * 路线串和节点的关联 <p>Title:leftID = branchID , rightID = routeNodeID </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company:faw_qm </p>
 * @author 管春元
 * @version 1.0
 */
public class RouteBranchNodeLinkInfo extends BinaryLinkInfo implements RouteBranchNodeLinkIfc
{
    private TechnicsRouteBranchIfc branchInfo;
    private RouteNodeIfc nodeInfo;
    private static final long serialVersionUID = 1L;
    //begin CR1
    private String attribute1;
    private String attribute2;

    //end CR1

    /**
     * 构造函数
     */
    public RouteBranchNodeLinkInfo()
    {

    }

    /**
     * 得到业务类名
     * @return String
     */
    public String getBsoName()
    {
        return "consRouteBranchNodeLink";
    }

    /**
     * 得到路线分支值对象
     * @return TechnicsRouteBranchIfc
     */
    public TechnicsRouteBranchIfc getRouteBranchInfo()
    {
        return branchInfo;
    }

    /**
     * 得到路线节点值对象
     * @return RouteNodeIfc
     */
    public RouteNodeIfc getRouteNodeInfo()
    {
        return nodeInfo;
    }

    /**
     * 设置路线分支值对象
     */
    public void setRouteBranchInfo(TechnicsRouteBranchIfc branchInfo)
    {
        this.branchInfo = branchInfo;
    }

    /**
     * 设置路线节点值对象
     */
    public void setRouteNodeInfo(RouteNodeIfc nodeInfo)
    {
        this.nodeInfo = nodeInfo;
    }

    //begin CR1
    /**
     * 设置预留属性1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1)
    {
        this.attribute1 = attribute1;
    }

    /**
     * 获得预留属性1
     * @param attribute1
     * @return
     */
    public String getAttribute1()
    {
        return this.attribute1;
    }

    /**
     * 设置预留属性2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2)
    {
        this.attribute2 = attribute2;
    }

    /**
     * 获得预留属性2
     * @param attribute2
     * @return
     */
    public String getAttribute2()
    {
        return this.attribute2;
    }
    //end CR1
}
