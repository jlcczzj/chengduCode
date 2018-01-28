/**
 * ���ɳ��� RouteNodeLinkInfo.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2011/12/22 �촺Ӣ       ԭ������Ԥ������
 */
package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BinaryLinkInfo;

/**
 * <p>Title:RouteNodeLinkInfo.java</p> <p>Description: ·�߽ڵ����ֵ����</p> <p>Package:com.faw_qm.technics.consroute.model</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:һ������</p>
 * @author �ܴ�Ԫ
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
     * ���캯��
     */
    public RouteNodeLinkInfo()
    {

    }

    /**
     * ���ҵ���������
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
     * ����Ԥ������1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1)
    {
        this.attribute1 = attribute1;
    }

    /**
     * ���Ԥ������1
     * @param attribute1
     * @return
     */
    public String getAttribute1()
    {
        return this.attribute1;
    }

    /**
     * ����Ԥ������2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2)
    {
        this.attribute2 = attribute2;
    }

    /**
     * ���Ԥ������2
     * @param attribute2
     * @return
     */
    public String getAttribute2()
    {
        return this.attribute2;
    }
    //end CR1
}
