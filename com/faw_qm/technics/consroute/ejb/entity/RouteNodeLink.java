/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * CR1 2011/12/22 徐春英      原因:增加预留属性
 */

package com.faw_qm.technics.consroute.ejb.entity;

import com.faw_qm.framework.service.BinaryLink;

/**
 * 得闲和节点的关联 <p>Title: </p> <p>Description: routeID是为了查找路线中开始单位和结束单位。 业务需求：工艺路线串的构成为路线单位节点，一个单位可以在一个路线串中出现多次。路线串中包括加工单位和装配单位，所以路线串的构成必须符合下列规则： 1. 装配单位在一个路线串中只能有一个，且只能是最后一个节点； 2.
 * 一个单位如果在一个路线串中出现多次，则必须是不同类型的节点(制造或装配)，否则不能在相邻的位置出现。 ￠ * 如果一个路线串中设计了多个装配节点，则显示对应的消息； ￠ * 如果装配节点不是最后节点，则显示对应的消息； ￠ * 如果路线串中存在相邻的同类型节点，则显示对应的消息 </p> <p>Copyright: Copyright (c) 2005</p> <p>Company:
 * faw-qm</p>
 * @author 管春元
 * @version 1.0
 */
public interface RouteNodeLink extends BinaryLink
{
    /**
     * 工艺路线ID
     */
    public java.lang.String getRouteID();

    /**
     * 工艺路线ID
     */
    public void setRouteID(String routeID);

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
