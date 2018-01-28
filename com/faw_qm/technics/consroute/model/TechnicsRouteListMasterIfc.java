/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

package com.faw_qm.technics.consroute.model;

import com.faw_qm.enterprise.model.MasterIfc;
import com.faw_qm.framework.service.BaseValueIfc;

/**
 * 工艺路线表主信息值对象 <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company:faw-qm </p>
 * @author 管春元
 * @version 1.0
 */
public interface TechnicsRouteListMasterIfc extends BaseValueIfc, MasterIfc
{
    /**
     * 路线表的编号 唯一。
     */
    public java.lang.String getRouteListNumber();

    /**
     * 设置路线表的标号
     */
    public void setRouteListNumber(String number);

    /**
     * 得到路线表名
     */
    public java.lang.String getRouteListName();

    /**
     * 设置辘轳县表名
     */
    public void setRouteListName(String name);

    /**
     * 获得工艺路线表对应的产品ID.
     * @return java.lang.String
     */
    public String getProductMasterID();

    /**
     * 获得工艺路线表对应的产品ID.
     * @param productMasterID - 零部件ID.
     */
    public void setProductMasterID(String productMasterID);

}
