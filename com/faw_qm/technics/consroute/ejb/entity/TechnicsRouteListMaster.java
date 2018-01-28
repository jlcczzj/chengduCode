/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.entity;

import com.faw_qm.enterprise.ejb.entity.Master;

/**
 * 路线表主信息 <p>Title: </p> <p>Description: 编号唯一。不继承唯一性包的Identified接口。通过在数据库中设置唯一性约束保证其唯一。 在客户端截取异常时需做特殊处理。 编号与名称始终与对应的最新版本一致。 注意撤销检出时保持与最新版本的数据一致性。 </p> <p>Copyright: Copyright (c) 2004.2</p>
 * <p>Company: 一汽启明公司</p>
 * @author 赵立彬
 * @version 1.0
 */
public interface TechnicsRouteListMaster extends Master
{
    /**
     * 设置路线表编号 唯一。
     */
    public java.lang.String getRouteListNumber();

    /**
     * 得到路线表编号
     */
    public void setRouteListNumber(String number);

    /**
     *设置路线表名
     */
    public java.lang.String getRouteListName();

    /**
     *得到路线表名
     */
    public void setRouteListName(String name);

    /**
     * 获得工艺路线表对应的产品ID.
     * @return java.lang.String
     */
    public String getProductMasterID();

    /**
     * 获得工艺路线表对应的产品ID.
     * @param productMasterID - 产品ID.
     */
    public void setProductMasterID(String productMasterID);

}
