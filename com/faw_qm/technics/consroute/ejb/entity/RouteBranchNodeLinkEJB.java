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
import com.faw_qm.technics.consroute.model.RouteBranchNodeLinkInfo;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchIfc;
import com.faw_qm.technics.consroute.util.RouteHelper;

/**
 * ·�ߴ��ͽڵ�Ĺ��� <p>Title:leftID = branchID , rightID = routeNodeID </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company:faw_qm </p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
abstract public class RouteBranchNodeLinkEJB extends BinaryLinkEJB
{
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
     * @roseuid 4039F29D037B
     */
    public RouteBranchNodeLinkEJB()
    {

    }

    /**
     * ���ҵ���������
     */
    public String getBsoName()
    {
        return "consRouteBranchNodeLink";
    }

    /**
     * ���ֵ����
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        RouteBranchNodeLinkInfo info = new RouteBranchNodeLinkInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * ���½�ֵ����������á�
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        RouteBranchNodeLinkInfo branchNodeInfo = (RouteBranchNodeLinkInfo)info;
        TechnicsRouteBranchIfc branchInfo = (TechnicsRouteBranchIfc)RouteHelper.refreshInfo(this.getLeftBsoID());
        branchNodeInfo.setRouteBranchInfo(branchInfo);
        RouteNodeIfc routeNodeInfo = (RouteNodeIfc)RouteHelper.refreshInfo(this.getRightBsoID());
        branchNodeInfo.setRouteNodeInfo(routeNodeInfo);
        //begin CR1
        branchNodeInfo.setAttribute1(this.getAttribute1());
        branchNodeInfo.setAttribute2(this.getAttribute2());
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
            System.out.println("enter RouteBranchNodeLinkEJB's createByValueInfo");
        }
        super.createByValueInfo(info);
        RouteBranchNodeLinkInfo branchNodeInfo = (RouteBranchNodeLinkInfo)info;
        if(branchNodeInfo.getRouteBranchInfo() == null || branchNodeInfo.getRouteBranchInfo().getBsoID() == null)
        {
            if(TechnicsRouteServiceEJB.VERBOSE)
            {
                System.out.println("branchNodeLinkEJB's createByValueInfo bsoid = " + branchNodeInfo.getRouteBranchInfo().getBsoID());
            }
            throw new QMRuntimeException("·�ߴ�ֵ���������д���");
        }
        this.setLeftBsoID(branchNodeInfo.getRouteBranchInfo().getBsoID());
        if(branchNodeInfo.getRouteNodeInfo() == null || branchNodeInfo.getRouteNodeInfo().getBsoID() == null)
        {
            //if(TechnicsRouteServiceEJB.VERBOSE)
            {
                System.out.println("branchNodeLinkEJB's createByValueInfo bsoid = " + branchNodeInfo.getRouteNodeInfo().getBsoID());
            }
            throw new QMRuntimeException("·�߽ڵ�ֵ���������д���");
        }
        this.setRightBsoID(branchNodeInfo.getRouteNodeInfo().getBsoID());
        //begin CR1
        this.setAttribute1(branchNodeInfo.getAttribute1());
        this.setAttribute2(branchNodeInfo.getAttribute2());
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
            System.out.println("enter TechnicsRouteList's updateByValueInfo");
        }
        super.updateByValueInfo(info);
        RouteBranchNodeLinkInfo branchNodeInfo = (RouteBranchNodeLinkInfo)info;
        if(branchNodeInfo.getRouteBranchInfo() == null || branchNodeInfo.getRouteBranchInfo().getBsoID() == null)
        {
            throw new QMException("·�ߴ�ֵ���������д���");
        }
        this.setLeftBsoID(branchNodeInfo.getRouteBranchInfo().getBsoID());
        if(branchNodeInfo.getRouteNodeInfo() == null || branchNodeInfo.getRouteNodeInfo().getBsoID() == null)
        {
            throw new QMException("·�߽ڵ�ֵ���������д���");
        }
        this.setRightBsoID(branchNodeInfo.getRouteNodeInfo().getBsoID());
        //begin CR1
        this.setAttribute1(branchNodeInfo.getAttribute1());
        this.setAttribute2(branchNodeInfo.getAttribute2());
        //end CR1

    }

}
