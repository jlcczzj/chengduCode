/**
 * ���ɳ��� RouteNodeLinkIfc.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * CR1 2011/12/22 �촺Ӣ       ԭ������Ԥ������
 */
package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BinaryLinkIfc;

/**
 * <p>Title:RouteNodeLinkIfc.java</p> <p>Description: ·�߽ڵ�����ӿ�</p> <p>Package:com.faw_qm.technics.consroute.model</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:һ������</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public interface RouteNodeLinkIfc extends BinaryLinkIfc
{
    /**
     * ȡ��Դ�ڵ�
     * @return com.faw_qm.technics.consroute.model.RouteNodeIfc
     */
    public RouteNodeIfc getSourceNode();

    /**
     * ȡ��Ŀ�Ľڵ�
     * @return com.faw_qm.technics.consroute.model.RouteNodeIfc
     */
    public RouteNodeIfc getDestinationNode();

    /**
     * ����Դ�ڵ�
     * @param sourceNode RouteNodeIfc
     */
    public void setSourceNode(RouteNodeIfc sourceNode);

    /**
     * ����Ŀ�Ľڵ�
     * @param destinationNode RouteNodeIfc
     */
    public void setDestinationNode(RouteNodeIfc destinationNode);

    /**
     * �õ�·��ֵ����
     * @return com.faw_qm.technics.consroute.model.TechnicsRouteIfc
     */
    public TechnicsRouteIfc getRouteInfo();

    /**
     * ����·����Ϣ
     * @param routeInfo TechnicsRouteIfc
     */
    public void setRouteInfo(TechnicsRouteIfc routeInfo);

    //begin CR1
    /**
     * ����Ԥ������1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1);

    /**
     * ���Ԥ������1
     * @param attribute1
     * @return
     */
    public String getAttribute1();

    /**
     * ����Ԥ������2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2);

    /**
     * ���Ԥ������2
     * @param attribute2
     * @return
     */
    public String getAttribute2();
    //end CR1

}
