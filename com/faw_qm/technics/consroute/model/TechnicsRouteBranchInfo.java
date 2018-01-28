/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * CR1 2011/12/22 徐春英       原因：增加预留属性
 */

package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BaseValueInfo;

/**
 * 路线分支值对象 <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: faw_qm</p>
 * @author 管春元
 * @version 1.0
 */
public class TechnicsRouteBranchInfo extends BaseValueInfo implements TechnicsRouteBranchIfc
{
    //是否是主要路线
    private boolean mainRoute;

    // 路线串字符串
    private String routeStr;

    //路线值对象
    private TechnicsRouteIfc routeInfo;
    private static final long serialVersionUID = 1L;
    //begin CR1
    private String attribute1;
    private String attribute2;

    //end CR1

    /**
     * 构造函数
     */
    public TechnicsRouteBranchInfo()
    {

    }

    /**
     * 是否是路线值对象
     */
    public void setMainRoute(boolean mainRoute)
    {
        this.mainRoute = mainRoute;
    }

    /**
     * 标记路线串是否为主要路线，默认值为True ,用户可标记为False
     */
    public boolean getMainRoute()
    {
        return mainRoute;
    }

    public void setRouteStr(String routeString)
    {
        this.routeStr = routeString;
    }

    public String getRouteStr()
    {
        return routeStr;
    }

    /**
     * 获得工艺路线表ID.
     * @return java.lang.String
     */
    public TechnicsRouteIfc getRouteInfo()
    {
        return this.routeInfo;
    }

    /**
     * 设置工艺路线表ID.
     * @param routeListID - 工艺路线表ID.
     */
    public void setRouteInfo(TechnicsRouteIfc routeInfo)
    {
        this.routeInfo = routeInfo;
    }

    /**
     * 得到标识
     * @return java.lang.String
     */
    public String getIdentity()
    {
        return getBsoName();
    }

    /**
     * 获得业务对象名。
     * @return String
     */
    public String getBsoName()
    {
        return "consTechnicsRouteBranch";
    }

    //begin CR1
    /**
     * 设置预留属性1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1)
    {
        this.attribute1 = attribute1;
    }

    /**
     * 获得预留属性1
     * @param attribute1
     * @return
     */
    public String getAttribute1()
    {
        return this.attribute1;
    }

    /**
     * 设置预留属性2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2)
    {
        this.attribute2 = attribute2;
    }

    /**
     * 获得预留属性2
     * @param attribute2
     * @return
     */
    public String getAttribute2()
    {
        return this.attribute2;
    }
    //end CR1
}
