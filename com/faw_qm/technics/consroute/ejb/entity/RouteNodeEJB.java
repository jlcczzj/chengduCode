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

import com.faw_qm.codemanage.model.CodingClassificationIfc;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.RouteNodeInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.util.RouteCategoryType;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.util.EJBServiceHelper;

/**
 * 路线节点 <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company:faw-qm </p>
 * @author 管春元
 * @version 1.0
 */
public abstract class RouteNodeEJB extends BsoReferenceEJB
{

    public RouteNodeEJB()
    {

    }

    /**
     * 工艺路线中的节点说明。
     */
    public abstract java.lang.String getNodeDescription();

    /**
     * 工艺路线中的节点说明。
     */
    public abstract void setNodeDescription(String description);

    /**
     * 工艺路线ID.
     */
    public abstract java.lang.String getRouteID();

    /**
     * 工艺路线ID.
     */
    public abstract void setRouteID(String routeID);

    /**
     * 获得路线单位。
     */
    public abstract java.lang.String getNodeDepartment();

    /**
     * 设置路线单位。
     */
    public abstract void setNodeDepartment(String department);

    /**
     * 记录路线节点的类别是制造还是装配，从枚举集合中取值{装配/制造}，默认值为制造
     */
    public abstract java.lang.String getRouteType();

    /**
     * 记录路线节点的类别是制造还是装配，从枚举集合中取值{装配/制造}，默认值为制造
     */
    public abstract void setRouteType(String routeType);

    //st skybird 2005.2.25
    // gcy add in 2005.4.26 for reinforce requirement  start
    /**
     *工艺路线中的节点配比说明(暂时不用)
     */
    public abstract java.lang.String getNodeRatio();

    /**
     * 工艺路线中的节点配置说明（暂时不用）
     */
    public abstract void setNodeRatio(String ratio);

    /**
     * 得到是否是临时路线
     * @return boolean
     */
    public abstract boolean getIsTempRoute();

    /**
     * 设置是否是临时路线
     * @param temp boolean
     */
    public abstract void setIsTempRoute(boolean temp);

    /**
     * 工艺路线节点中的生效时间说明
     * @return validTime
     */
    public abstract java.lang.String getNodeValidTime();

    /**
     * 工艺路线中的节点的生效时间
     */
    public abstract void setNodeValidTime(String validTime);

    /**
     * 工艺路线中的接点失效时间说明
     */
    public abstract java.lang.String getNodeInvalidTime();

    /**
     * 工艺路线中的节点的失效时间说明
     */
    public abstract void setNodeInvalidTime(String invalidTime);

    // gcy add in 2005.4.26 for reinforce requirement  end

    /**
     * 获得节点的X坐标。
     */
    public abstract long getX();

    /**
     * 设置节点的X坐标
     */
    public abstract void setX(long Xlocation);

    /**
     * 获得节点的Y坐标。
     */
    public abstract long getY();

    /**
     * 设置节点的Y坐标
     */
    public abstract void setY(long Ylocation);

    /**
     * 获得工艺路线分枝ID.
     */
    public abstract java.lang.String getRouteBranchID();

    /**
     * 设置工艺路线分枝ID.
     */
    public abstract void setRouteBranchID(String routeBranchID);

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
     * 获得业务对象名。
     */
    public String getBsoName()
    {
        return "consRouteNode";
    }

    
    /**
     * 获得值对象。
     * @throws QMException 
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        RouteNodeInfo info = new RouteNodeInfo();
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
        RouteNodeInfo nodeInfo = (RouteNodeInfo)info;
        if(this.getNodeDepartment() != null)
        {
            nodeInfo.setNodeDepartmentName(getDepartmentName(this.getNodeDepartment()));
            nodeInfo.setNodeDepartment(this.getNodeDepartment());
        }else
        {
            throw new QMException("单位ID不应该为空。");
        }
        nodeInfo.setNodeDescription(this.getNodeDescription());
        nodeInfo.setRouteType(RouteCategoryType.toRouteCategoryType(this.getRouteType()).getDisplay());
        //st skybird 20025.2.25
        // gcy add in 2005.4.26 for reinforce requirement  start
        nodeInfo.setIsTempRoute(this.getIsTempRoute());
        nodeInfo.setNodeRatio(this.getNodeRatio());
        nodeInfo.setNodeValidTime(this.getNodeValidTime());
        nodeInfo.setNodeInvalidTime(this.getNodeInvalidTime());
        // gcy add in 2005.4.26 for reinforce requirement  end
        //ed
        //nodeInfo.setStartNode(this.getStartNode());
        nodeInfo.setX(this.getX());
        nodeInfo.setY(this.getY());
        TechnicsRouteIfc routeInfo = (TechnicsRouteIfc)RouteHelper.refreshInfo(this.getRouteID());
        nodeInfo.setRouteInfo(routeInfo);
        //begin CR1
        nodeInfo.setAttribute1(this.getAttribute1());
        nodeInfo.setAttribute2(this.getAttribute2());
        //end CR1
    }

    //获得单位名称。
    private String getDepartmentName(String departmentID) throws QMException
    {
        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
        BaseValueIfc codeInfo = pservice.refreshInfo(departmentID);
        String departmentName = null;
        if(codeInfo instanceof CodingIfc)
        {
            departmentName = ((CodingIfc)codeInfo).getShorten();
        }
        if(codeInfo instanceof CodingClassificationIfc)
        {
            departmentName = ((CodingClassificationIfc)codeInfo).getClassSort();
        }
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("RouteNodeEJB's getDepartmentName name = " + departmentName);
        }
        return departmentName;
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
            System.out.println("enter RouteNodeEJB's createByValueInfo");
        }
        super.createByValueInfo(info);
        RouteNodeInfo nodeInfo = (RouteNodeInfo)info;
        this.setNodeDepartment(nodeInfo.getNodeDepartment());
        this.setNodeDescription(nodeInfo.getNodeDescription());
        this.setRouteType(RouteHelper.getValue(RouteCategoryType.getRouteCategoryTypeSet(), nodeInfo.getRouteType()));

        //st skybird 2005.2.25
        // gcy add in 2005.4.26 for reinforce requirement  start
        this.setIsTempRoute(nodeInfo.getIsTempRoute());
        this.setNodeRatio(nodeInfo.getNodeRatio());
        this.setNodeValidTime(nodeInfo.getNodeValidTime());
        this.setNodeInvalidTime(nodeInfo.getNodeInvalidTime());
        // gcy add in 2005.4.26 for reinforce requirement  end
        //ed

        //this.setStartNode(nodeInfo.getStartNode());
        this.setX(nodeInfo.getX());
        this.setY(nodeInfo.getY());
        if(nodeInfo.getRouteInfo() == null || nodeInfo.getRouteInfo().getBsoID() == null)
        {
            throw new QMRuntimeException("工艺路线节点引用的工艺路线值对象设置有错误");
        }
        this.setRouteID(nodeInfo.getRouteInfo().getBsoID());
        //begin CR1
        this.setAttribute1(nodeInfo.getAttribute1());
        this.setAttribute2(nodeInfo.getAttribute2());
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
            System.out.println("enter RouteNodeEJB's updateByValueInfo");
        }
        super.updateByValueInfo(info);
        RouteNodeInfo nodeInfo = (RouteNodeInfo)info;
        this.setNodeDepartment(nodeInfo.getNodeDepartment());
        this.setNodeDescription(nodeInfo.getNodeDescription());
        this.setRouteType(RouteHelper.getValue(RouteCategoryType.getRouteCategoryTypeSet(), nodeInfo.getRouteType()));
        //st skybird 2005.2.25
        // gcy add in 2005.4.26 for reinforce requirement  start
        this.setIsTempRoute(nodeInfo.getIsTempRoute());
        this.setNodeRatio(nodeInfo.getNodeRatio());
        this.setNodeValidTime(nodeInfo.getNodeValidTime());
        this.setNodeInvalidTime(nodeInfo.getNodeInvalidTime());
        // gcy add in 2005.4.26 for reinforce requirement  end
        //ed

        //this.setStartNode(nodeInfo.getStartNode());
        this.setX(nodeInfo.getX());
        this.setY(nodeInfo.getY());
        if(nodeInfo.getRouteInfo() == null || nodeInfo.getRouteInfo().getBsoID() == null)
        {
            throw new QMException("工艺路线节点引用的工艺路线值对象设置有错误");
        }
        this.setRouteID(nodeInfo.getRouteInfo().getBsoID());
        //begin CR1
        this.setAttribute1(nodeInfo.getAttribute1());
        this.setAttribute2(nodeInfo.getAttribute2());
        //end CR1
    }
}
