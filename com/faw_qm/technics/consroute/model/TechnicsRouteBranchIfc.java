/**
 * 生成程序 TechnicsRouteBranchIfc.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * CR1 2011/12/22 徐春英       原因：增加预留属性
 */
package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title:TechnicsRouteBranchIfc.java</p> <p>Description: 工艺路线分支接口</p> <p>Package:com.faw_qm.technics.consroute.model</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:一汽启明</p>
 * @author 管春元
 * @version 1.0
 */
public interface TechnicsRouteBranchIfc extends CappIdentity, BaseValueIfc
{
    /**
     * 是否是主要路线
     */
    public void setMainRoute(boolean mainRoute);

    /**
     * 标记路线串是否为主要路线，默认值为True ,用户可标记为False
     */
    public boolean getMainRoute();

    public void setRouteStr(String srt);

    public String getRouteStr();

    /**
     * 得到路线值对象
     */
    public TechnicsRouteIfc getRouteInfo();

    /**
     * 设置路线值对象
     */
    public void setRouteInfo(TechnicsRouteIfc routeInfo);

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
