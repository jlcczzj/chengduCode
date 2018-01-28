/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.entity;

import javax.ejb.CreateException;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkEJB;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.RouteListProductLinkInfo;

//leftID = sourceNodeID, rightID = destinationNodeID.
/**
 * 路线表master和产品master关联。
 * @author 管春元
 * @version 1.0
 */
public abstract class RouteListProductLinkEJB extends BinaryLinkEJB
{

    /**
     * @roseuid 4039F29D00AA
     */
    public RouteListProductLinkEJB()
    {

    }

    /**
     * 获得业务对象名
     * @return String RouteListProductLink
     */
    public String getBsoName()
    {
        return "consRouteListProductLink";
    }

    /**
     * 获得值对象
     * @throws QMException
     * @return BaseValueIfc 值对象
     * @throws QMException 
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        RouteListProductLinkInfo info = new RouteListProductLinkInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * 对新建值对象进行设置。
     * @param info BaseValueIfc 值对象
     * @throws QMException 
     * @throws QMException
     * @see BaseValueInfo
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
    }

    /**
     * 根据值对象进行持久化。
     * @param info BaseValueIfc 值对象
     * @throws CreateException 
     * @throws CreateException
     * @see BaseValueInfo
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter RouteListProductLinkEJB's createByValueInfo");
        }
        super.createByValueInfo(info);
    }

    /**
     * 根据值对象进行更新。
     * @param info BaseValueIfc 值对象
     * @throws QMException 
     * @throws QMException
     * @see BaseValueInfo
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter RouteListProductLinkEJB's updateByValueInfo");
        }
        super.updateByValueInfo(info);
    }
}
