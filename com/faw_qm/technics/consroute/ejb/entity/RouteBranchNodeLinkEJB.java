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
import com.faw_qm.technics.consroute.model.RouteBranchNodeLinkInfo;
import com.faw_qm.technics.consroute.model.RouteNodeIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchIfc;
import com.faw_qm.technics.consroute.util.RouteHelper;

/**
 * 路线串和节点的关联 <p>Title:leftID = branchID , rightID = routeNodeID </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company:faw_qm </p>
 * @author 管春元
 * @version 1.0
 */
abstract public class RouteBranchNodeLinkEJB extends BinaryLinkEJB
{
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
     * @roseuid 4039F29D037B
     */
    public RouteBranchNodeLinkEJB()
    {

    }

    /**
     * 获得业务对象名。
     */
    public String getBsoName()
    {
        return "consRouteBranchNodeLink";
    }

    /**
     * 获得值对象。
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        RouteBranchNodeLinkInfo info = new RouteBranchNodeLinkInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * 对新建值对象进行设置。
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
     * 根据值对象进行持久化。
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
            throw new QMRuntimeException("路线串值对象设置有错误");
        }
        this.setLeftBsoID(branchNodeInfo.getRouteBranchInfo().getBsoID());
        if(branchNodeInfo.getRouteNodeInfo() == null || branchNodeInfo.getRouteNodeInfo().getBsoID() == null)
        {
            //if(TechnicsRouteServiceEJB.VERBOSE)
            {
                System.out.println("branchNodeLinkEJB's createByValueInfo bsoid = " + branchNodeInfo.getRouteNodeInfo().getBsoID());
            }
            throw new QMRuntimeException("路线节点值对象设置有错误");
        }
        this.setRightBsoID(branchNodeInfo.getRouteNodeInfo().getBsoID());
        //begin CR1
        this.setAttribute1(branchNodeInfo.getAttribute1());
        this.setAttribute2(branchNodeInfo.getAttribute2());
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
            System.out.println("enter TechnicsRouteList's updateByValueInfo");
        }
        super.updateByValueInfo(info);
        RouteBranchNodeLinkInfo branchNodeInfo = (RouteBranchNodeLinkInfo)info;
        if(branchNodeInfo.getRouteBranchInfo() == null || branchNodeInfo.getRouteBranchInfo().getBsoID() == null)
        {
            throw new QMException("路线串值对象设置有错误");
        }
        this.setLeftBsoID(branchNodeInfo.getRouteBranchInfo().getBsoID());
        if(branchNodeInfo.getRouteNodeInfo() == null || branchNodeInfo.getRouteNodeInfo().getBsoID() == null)
        {
            throw new QMException("路线节点值对象设置有错误");
        }
        this.setRightBsoID(branchNodeInfo.getRouteNodeInfo().getBsoID());
        //begin CR1
        this.setAttribute1(branchNodeInfo.getAttribute1());
        this.setAttribute2(branchNodeInfo.getAttribute2());
        //end CR1

    }

}
