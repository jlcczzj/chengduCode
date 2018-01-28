/**
 * ���ɳ��� RouteBranchNodeLinkIfc.java    1.0    2005/07/01
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
 * ·�ߴ��ͽڵ�Ĺ��� <p>Title:leftID = branchID , rightID = routeNodeID </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company:faw_qm </p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public interface RouteBranchNodeLinkIfc extends BinaryLinkIfc
{
    /**
     * �õ�·�߷�ֵ֧����
     * @return TechnicsRouteBranchIfc
     */
    public TechnicsRouteBranchIfc getRouteBranchInfo();

    /**
     * ����·�߷�ֵ֧����
     */
    public void setRouteBranchInfo(TechnicsRouteBranchIfc branchInfo);

    /**
     * �õ�·�߽ڵ�ֵ����
     * @return RouteNodeIfc
     */
    public RouteNodeIfc getRouteNodeInfo();

    /**
     * ����·�߽ڵ�ֵ����
     */
    public void setRouteNodeInfo(RouteNodeIfc nodeInfo);

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
