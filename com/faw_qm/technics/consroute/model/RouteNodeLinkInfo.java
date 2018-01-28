/**
 * 生成程序 RouteNodeLinkInfo.java    1.0    2005/07/01
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
 * <p>Title:RouteNodeLinkInfo.java</p> <p>Description: 路线节点关联值对象</p> <p>Package:com.faw_qm.technics.consroute.model</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:一汽启明</p>
 * @author 管春元
 * @version 1.0
 */
public class RouteNodeLinkInfo extends BinaryLinkInfo implements RouteNodeLinkIfc
{
    private RouteNodeIfc sourceNode;
    private RouteNodeIfc destinationNode;
    private TechnicsRouteIfc routeInfo;
    private static final long serialVersionUID = 1L;
    //begin CR1
    private String attribute1;
    private String attribute2;

    //end CR1

    /**
     * 构造函数
     */
    public RouteNodeLinkInfo()
    {

    }

    /**
     * 获得业务对象名。
     */
    public String getBsoName()
    {
        return "consRouteNodeLink";
    }

    /**
     * @return com.faw_qm.technics.consroute.model.RouteNodeIfc
     * @roseuid 403AB65302A2
     */
    public RouteNodeIfc getSourceNode()
    {
        return this.sourceNode;
    }

    /**
     * @return com.faw_qm.technics.consroute.model.RouteNodeIfc
     * @roseuid 403AB6530324
     */
    public RouteNodeIfc getDestinationNode()
    {
        return this.destinationNode;
    }

    /**
     * @roseuid 403AB653039C
     */
    public void setSourceNode(RouteNodeIfc sourceNode)
    {
        this.sourceNode = sourceNode;
    }

    /**
     * @roseuid 403AB65303C4
     */
    public void setDestinationNode(RouteNodeIfc destinationNode)
    {
        this.destinationNode = destinationNode;
    }

    /**
     * @return com.faw_qm.technics.consroute.model.TechnicsRouteIfc
     * @roseuid 403AB6540040
     */
    public TechnicsRouteIfc getRouteInfo()
    {
        return routeInfo;
    }

    /**
     * @param routeInfo
     * @roseuid 403AB65400D6
     */
    public void setRouteInfo(TechnicsRouteIfc routeInfo)
    {
        this.routeInfo = routeInfo;
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
