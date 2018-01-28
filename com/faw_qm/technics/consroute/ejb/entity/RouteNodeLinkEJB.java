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

import javax.ejb.CreateException;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkEJB;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.RouteNodeLinkInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.util.RouteHelper;

//leftID = sourceNodeID, rightID = destinationNodeID.
/**
 * 得闲和节点的关联 <p>Title: </p> <p>Description: routeID是为了查找路线中开始单位和结束单位。 业务需求：工艺路线串的构成为路线单位节点，一个单位可以在一个路线串中出现多次。路线串中包括加工单位和装配单位，所以路线串的构成必须符合下列规则： 1. 装配单位在一个路线串中只能有一个，且只能是最后一个节点； 2.
 * 一个单位如果在一个路线串中出现多次，则必须是不同类型的节点(制造或装配)，否则不能在相邻的位置出现。 ￠ * 如果一个路线串中设计了多个装配节点，则显示对应的消息； ￠ * 如果装配节点不是最后节点，则显示对应的消息； ￠ * 如果路线串中存在相邻的同类型节点，则显示对应的消息 </p> <p>Copyright: Copyright (c) 2005</p> <p>Company:
 * faw-qm</p>
 * @author 管春元
 * @version 1.0
 */
public abstract class RouteNodeLinkEJB extends BinaryLinkEJB
{

    public RouteNodeLinkEJB()
    {

    }

    /**
     * 工艺路线ID
     */
    public abstract java.lang.String getRouteID();

    /**
     * 工艺路线ID
     */
    public abstract void setRouteID(String routeID);

    /**
     * 获得业务对象名。
     */
    public String getBsoName()
    {
        return "consRouteNodeLink";
    }

    //begin CR1
    /**
     * 设置预留属性1
     */
    public abstract void setAttribute1(String attribute1);

    /**
     * 获得预留属性1
     */
    public abstract String getAttribute1();

    /**
     * 设置预留属性2
     */
    public abstract void setAttribute2(String attribute2);

    /**
     * 获得预留属性2
     */
    public abstract String getAttribute2();

    //end CR1

    /**
     * 获得值对象。
     * @throws QMException 
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        RouteNodeLinkInfo info = new RouteNodeLinkInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * 对新建值对象进行设置。
     * @throws QMException 
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        RouteNodeLinkInfo nodeLinkInfo = (RouteNodeLinkInfo)info;
        RouteNodeIfc sourceNode = (RouteNodeIfc)RouteHelper.refreshInfo(this.getLeftBsoID());
        nodeLinkInfo.setSourceNode(sourceNode);
        RouteNodeIfc destinationNode = (RouteNodeIfc)RouteHelper.refreshInfo(this.getRightBsoID());
        nodeLinkInfo.setDestinationNode(destinationNode);
        TechnicsRouteIfc routeInfo = (TechnicsRouteIfc)RouteHelper.refreshInfo(this.getRouteID());
        nodeLinkInfo.setRouteInfo(routeInfo);
        //begin CR1
        nodeLinkInfo.setAttribute1(this.getAttribute1());
        nodeLinkInfo.setAttribute2(this.getAttribute2());
        //end CR1
    }

    /**
     * 根据值对象进行持久化。
     * @throws CreateException 
     * @throws QMException 
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter RouteNodeLinkEJB's createByValueInfo");
        }
        super.createByValueInfo(info);
        RouteNodeLinkInfo nodeLinkInfo = (RouteNodeLinkInfo)info;
        if(nodeLinkInfo.getSourceNode() == null || nodeLinkInfo.getSourceNode().getBsoID() == null)
        {
            throw new QMRuntimeException("源节点值对象设置有错误");
        }
        this.setLeftBsoID(nodeLinkInfo.getSourceNode().getBsoID());
        if(nodeLinkInfo.getDestinationNode() == null || nodeLinkInfo.getDestinationNode().getBsoID() == null)
        {
            throw new QMRuntimeException("目的节点值对象设置有错误");
        }
        this.setRightBsoID(nodeLinkInfo.getDestinationNode().getBsoID());

        if(nodeLinkInfo.getRouteInfo() == null || nodeLinkInfo.getRouteInfo().getBsoID() == null)
        {
            throw new QMRuntimeException("节点关联类：工艺路线值对象设置有错误");
        }
        this.setRouteID(nodeLinkInfo.getRouteInfo().getBsoID());
        //begin CR1
        this.setAttribute1(nodeLinkInfo.getAttribute1());
        this.setAttribute2(nodeLinkInfo.getAttribute2());
        //end CR1
    }

    /**
     * 根据值对象进行更新。
     * @throws QMException 
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter RouteNodeLinkEJB's updateByValueInfo");
        }
        super.updateByValueInfo(info);
        RouteNodeLinkInfo nodeLinkInfo = (RouteNodeLinkInfo)info;
        if(nodeLinkInfo.getSourceNode() == null || nodeLinkInfo.getSourceNode().getBsoID() == null)
        {
            throw new QMException("源节点值对象设置有错误");
        }
        this.setLeftBsoID(nodeLinkInfo.getSourceNode().getBsoID());
        if(nodeLinkInfo.getDestinationNode() == null || nodeLinkInfo.getDestinationNode().getBsoID() == null)
        {
            throw new QMException("目的节点值对象设置有错误");
        }
        this.setRightBsoID(nodeLinkInfo.getDestinationNode().getBsoID());

        if(nodeLinkInfo.getRouteInfo() == null || nodeLinkInfo.getRouteInfo().getBsoID() == null)
        {
            throw new QMException("节点关联类：工艺路线值对象设置有错误");
        }
        this.setRouteID(nodeLinkInfo.getRouteInfo().getBsoID());
        //begin CR1
        this.setAttribute1(nodeLinkInfo.getAttribute1());
        this.setAttribute2(nodeLinkInfo.getAttribute2());
        //end CR1

    }

}
