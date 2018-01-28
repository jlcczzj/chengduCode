/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * CR1 2011/12/22 �촺Ӣ      ԭ��:����Ԥ������
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
 * ���кͽڵ�Ĺ��� <p>Title: </p> <p>Description: routeID��Ϊ�˲���·���п�ʼ��λ�ͽ�����λ�� ҵ�����󣺹���·�ߴ��Ĺ���Ϊ·�ߵ�λ�ڵ㣬һ����λ������һ��·�ߴ��г��ֶ�Ρ�·�ߴ��а����ӹ���λ��װ�䵥λ������·�ߴ��Ĺ��ɱ���������й��� 1. װ�䵥λ��һ��·�ߴ���ֻ����һ������ֻ�������һ���ڵ㣻 2.
 * һ����λ�����һ��·�ߴ��г��ֶ�Σ�������ǲ�ͬ���͵Ľڵ�(�����װ��)�������������ڵ�λ�ó��֡� �� * ���һ��·�ߴ�������˶��װ��ڵ㣬����ʾ��Ӧ����Ϣ�� �� * ���װ��ڵ㲻�����ڵ㣬����ʾ��Ӧ����Ϣ�� �� * ���·�ߴ��д������ڵ�ͬ���ͽڵ㣬����ʾ��Ӧ����Ϣ </p> <p>Copyright: Copyright (c) 2005</p> <p>Company:
 * faw-qm</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public abstract class RouteNodeLinkEJB extends BinaryLinkEJB
{

    public RouteNodeLinkEJB()
    {

    }

    /**
     * ����·��ID
     */
    public abstract java.lang.String getRouteID();

    /**
     * ����·��ID
     */
    public abstract void setRouteID(String routeID);

    /**
     * ���ҵ���������
     */
    public String getBsoName()
    {
        return "consRouteNodeLink";
    }

    //begin CR1
    /**
     * ����Ԥ������1
     */
    public abstract void setAttribute1(String attribute1);

    /**
     * ���Ԥ������1
     */
    public abstract String getAttribute1();

    /**
     * ����Ԥ������2
     */
    public abstract void setAttribute2(String attribute2);

    /**
     * ���Ԥ������2
     */
    public abstract String getAttribute2();

    //end CR1

    /**
     * ���ֵ����
     * @throws QMException 
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        RouteNodeLinkInfo info = new RouteNodeLinkInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * ���½�ֵ����������á�
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
     * ����ֵ������г־û���
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
            throw new QMRuntimeException("Դ�ڵ�ֵ���������д���");
        }
        this.setLeftBsoID(nodeLinkInfo.getSourceNode().getBsoID());
        if(nodeLinkInfo.getDestinationNode() == null || nodeLinkInfo.getDestinationNode().getBsoID() == null)
        {
            throw new QMRuntimeException("Ŀ�Ľڵ�ֵ���������д���");
        }
        this.setRightBsoID(nodeLinkInfo.getDestinationNode().getBsoID());

        if(nodeLinkInfo.getRouteInfo() == null || nodeLinkInfo.getRouteInfo().getBsoID() == null)
        {
            throw new QMRuntimeException("�ڵ�����ࣺ����·��ֵ���������д���");
        }
        this.setRouteID(nodeLinkInfo.getRouteInfo().getBsoID());
        //begin CR1
        this.setAttribute1(nodeLinkInfo.getAttribute1());
        this.setAttribute2(nodeLinkInfo.getAttribute2());
        //end CR1
    }

    /**
     * ����ֵ������и��¡�
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
            throw new QMException("Դ�ڵ�ֵ���������д���");
        }
        this.setLeftBsoID(nodeLinkInfo.getSourceNode().getBsoID());
        if(nodeLinkInfo.getDestinationNode() == null || nodeLinkInfo.getDestinationNode().getBsoID() == null)
        {
            throw new QMException("Ŀ�Ľڵ�ֵ���������д���");
        }
        this.setRightBsoID(nodeLinkInfo.getDestinationNode().getBsoID());

        if(nodeLinkInfo.getRouteInfo() == null || nodeLinkInfo.getRouteInfo().getBsoID() == null)
        {
            throw new QMException("�ڵ�����ࣺ����·��ֵ���������д���");
        }
        this.setRouteID(nodeLinkInfo.getRouteInfo().getBsoID());
        //begin CR1
        this.setAttribute1(nodeLinkInfo.getAttribute1());
        this.setAttribute2(nodeLinkInfo.getAttribute2());
        //end CR1

    }

}
