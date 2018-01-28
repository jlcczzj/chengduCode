/**
 * 生成程序 RouteNodeLinkIfc.java    1.0    2005/07/01
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
 * <p>Title:RouteNodeLinkIfc.java</p> <p>Description: 路线节点关联接口</p> <p>Package:com.faw_qm.technics.consroute.model</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:一汽启明</p>
 * @author 管春元
 * @version 1.0
 */
public interface RouteNodeLinkIfc extends BinaryLinkIfc
{
    /**
     * 取得源节点
     * @return com.faw_qm.technics.consroute.model.RouteNodeIfc
     */
    public RouteNodeIfc getSourceNode();

    /**
     * 取得目的节点
     * @return com.faw_qm.technics.consroute.model.RouteNodeIfc
     */
    public RouteNodeIfc getDestinationNode();

    /**
     * 设置源节点
     * @param sourceNode RouteNodeIfc
     */
    public void setSourceNode(RouteNodeIfc sourceNode);

    /**
     * 设置目的节点
     * @param destinationNode RouteNodeIfc
     */
    public void setDestinationNode(RouteNodeIfc destinationNode);

    /**
     * 得到路线值对象
     * @return com.faw_qm.technics.consroute.model.TechnicsRouteIfc
     */
    public TechnicsRouteIfc getRouteInfo();

    /**
     * 设置路线信息
     * @param routeInfo TechnicsRouteIfc
     */
    public void setRouteInfo(TechnicsRouteIfc routeInfo);

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
