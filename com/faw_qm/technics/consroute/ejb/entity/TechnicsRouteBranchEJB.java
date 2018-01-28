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
import com.faw_qm.framework.service.BsoReferenceEJB;
import com.faw_qm.technics.consroute.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.consroute.model.TechnicsRouteBranchInfo;
import com.faw_qm.technics.consroute.model.TechnicsRouteIfc;
import com.faw_qm.technics.consroute.util.RouteHelper;

/**
 * 工艺路线分支，既路线串 <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: faw-qm</p>
 * @author 管春元 zz 周茁添加字段routeStr 存编制好的路线串
 * @version 1.0
 */
public abstract class TechnicsRouteBranchEJB extends BsoReferenceEJB
{

    public TechnicsRouteBranchEJB()
    {

    }

    /**
     * 设置是否是主要路线
     */
    public abstract void setMainRoute(boolean mainRoute);

    /**
     * 标记路线串是否为主要路线，默认值为True ,用户可标记为False
     */
    public abstract boolean getMainRoute();

    /**
     * 得到路线的id
     */
    public abstract java.lang.String getRouteID();

    /**
     * 设置路线串的字符串
     */
    public abstract void setRouteStr(String routeID);

    /**
     * 得到路线串的字符串
     */
    public abstract java.lang.String getRouteStr();

    /**
     * 设置路线的id
     */
    public abstract void setRouteID(String routeID);

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
        return "consTechnicsRouteBranch";
    }

    /**
     * 获得值对象。
     * @throws QMException 
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        TechnicsRouteBranchInfo info = new TechnicsRouteBranchInfo();
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
        TechnicsRouteBranchInfo branchInfo = (TechnicsRouteBranchInfo)info;
        branchInfo.setMainRoute(this.getMainRoute());
        TechnicsRouteIfc routeInfo = (TechnicsRouteIfc)RouteHelper.refreshInfo(this.getRouteID());
        branchInfo.setRouteInfo(routeInfo);
        branchInfo.setRouteStr(this.getRouteStr());
        //begin CR1
        branchInfo.setAttribute1(this.getAttribute1());
        branchInfo.setAttribute2(this.getAttribute2());
        //end CR1
    }

    /**
     * 根据值对象进行持久化。
     * @throws CreateException 
     * @throws QMException 
     * @throws QMException 
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        if(TechnicsRouteServiceEJB.VERBOSE)
        {
            System.out.println("enter TechnicsRouteBranchEJB's createByValueInfo");
        }
        super.createByValueInfo(info);
        TechnicsRouteBranchInfo branchInfo = (TechnicsRouteBranchInfo)info;
        this.setMainRoute(branchInfo.getMainRoute());
        if(branchInfo.getRouteInfo() == null || branchInfo.getRouteInfo().getBsoID() == null)
        {
            throw new QMRuntimeException("路线串引用的工艺路线值对象设置有错误");
        }
        this.setRouteID(branchInfo.getRouteInfo().getBsoID());
        // zz
        this.setRouteStr(branchInfo.getRouteStr());
        // zz
        //begin CR1
        this.setAttribute1(branchInfo.getAttribute1());
        this.setAttribute2(branchInfo.getAttribute2());
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
            System.out.println("enter TechnicsRouteBranchEJB's updateByValueInfo");
        }
        super.updateByValueInfo(info);
        TechnicsRouteBranchInfo branchInfo = (TechnicsRouteBranchInfo)info;
        this.setMainRoute(branchInfo.getMainRoute());
        if(branchInfo.getRouteInfo() == null || branchInfo.getRouteInfo().getBsoID() == null)
        {
            throw new QMException("路线串引用的工艺路线值对象设置有错误");
        }
        this.setRouteID(branchInfo.getRouteInfo().getBsoID());
        this.setRouteStr(branchInfo.getRouteStr());
        //begin CR1
        this.setAttribute1(branchInfo.getAttribute1());
        this.setAttribute2(branchInfo.getAttribute2());
        //end CR1
    }

}
