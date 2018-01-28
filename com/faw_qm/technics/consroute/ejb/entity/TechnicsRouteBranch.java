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

import com.faw_qm.framework.service.BsoReference;

/**
 * 工艺路线分支，既路线串 <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: faw-qm</p>
 * @author 管春元
 * @version 1.0
 */
public interface TechnicsRouteBranch extends BsoReference
{
    /**
     * 设置是否是主要路线
     */
    public void setMainRoute(boolean mainRoute);

    /**
     * 标记路线串是否为主要路线，默认值为True ,用户可标记为False
     */
    public boolean getMainRoute();

    /**
     *得到路线的id
     */
    public java.lang.String getRouteID();

    /**
     * 设置路线的id
     */
    public void setRouteID(String routeID);

    /**
     * 得到路线串字符串
     * @return String
     */
    public java.lang.String getRouteStr();

    public void setRouteStr(String routeID);

    //begin CR1
    /**
     * 设置预留属性1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1);

    /**
     * 获得预留属性1
     * @param attribute1
     * @return
     */
    public String getAttribute1();

    /**
     * 设置预留属性2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2);

    /**
     * 获得预留属性2
     * @param attribute2
     * @return
     */
    public String getAttribute2();
    //end CR1
}
