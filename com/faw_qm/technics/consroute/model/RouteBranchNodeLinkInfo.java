/**
 * ���ɳ���RouteBranchNodeLinkInfo.java    1.0    2005/07/01
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
 * ·�ߴ��ͽڵ�Ĺ��� <p>Title:leftID = branchID , rightID = routeNodeID </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company:faw_qm </p>
 * @author �ܴ�Ԫ
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
     * ���캯��
     */
    public RouteBranchNodeLinkInfo()
    {

    }

    /**
     * �õ�ҵ������
     * @return String
     */
    public String getBsoName()
    {
        return "consRouteBranchNodeLink";
    }

    /**
     * �õ�·�߷�ֵ֧����
     * @return TechnicsRouteBranchIfc
     */
    public TechnicsRouteBranchIfc getRouteBranchInfo()
    {
        return branchInfo;
    }

    /**
     * �õ�·�߽ڵ�ֵ����
     * @return RouteNodeIfc
     */
    public RouteNodeIfc getRouteNodeInfo()
    {
        return nodeInfo;
    }

    /**
     * ����·�߷�ֵ֧����
     */
    public void setRouteBranchInfo(TechnicsRouteBranchIfc branchInfo)
    {
        this.branchInfo = branchInfo;
    }

    /**
     * ����·�߽ڵ�ֵ����
     */
    public void setRouteNodeInfo(RouteNodeIfc nodeInfo)
    {
        this.nodeInfo = nodeInfo;
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
