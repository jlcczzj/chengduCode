/**
 * 生成程序 RouteListProductLinkInfo.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 
 */
package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BinaryLinkInfo;

//LEFTID = routeListMasterID; RIGHTID = productMasterID.
/**
 * <p>Title:RouteListProductLinkInfo.java</p> <p>Description: 路线产品关联值对象</p> <p>Package:com.faw_qm.technics.consroute.model</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:一汽启明</p>
 * @author 管春元
 * @version 1.0
 */
public class RouteListProductLinkInfo extends BinaryLinkInfo implements RouteListProductLinkIfc
{
    private static final long serialVersionUID = 1L;

    /**
     * 构造函数
     */
    public RouteListProductLinkInfo()
    {

    }

    /**
     * 获得业务对象名。
     */
    public String getBsoName()
    {
        return "consRouteListProductLink";
    }

    /**
     * 得到路线表主信息id
     * @return String
     */
    public String getRouteListMasterID()
    {
        return this.getLeftBsoID();
    }

    /**
     * 设置路线表主信息的id
     * @param routeListMasterID String
     */
    public void setRouteListMasterID(String routeListMasterID)
    {
        this.setLeftBsoID(routeListMasterID);
    }

    /**
     * 得到产品（领部件主信息的id）
     * @return String
     */
    public String getProductMasterID()
    {
        return this.getRightBsoID();
    }

    /**
     * 设置产品（领部件主信息的id）
     * @param productMasterID String
     */
    public void setProductMasterID(String productMasterID)
    {
        this.setRightBsoID(productMasterID);
    }
}
