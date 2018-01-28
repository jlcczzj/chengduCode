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

import java.util.Vector;

import com.faw_qm.enterprise.ejb.entity.RevisionControlled;

/**
 * 一个零部件可以有多个工艺路线表。
 * 一个工艺路线表有多个工艺路线。
 * TechnicsRouteListIfc的唯一标识由标识代理来决定。
 */

/**
 * <p>Title: </p> <p>Description: 工艺路线服务端类</p> <p>Copyright: Copyright (c) 2004.2</p> <p>Company: 一汽启明公司</p>
 * @author 赵立彬
 * @mener skybird
 * @version 1.0
 */
public interface TechnicsRouteList extends RevisionControlled
{
    /**
     * 工艺路线表的编号，唯一标识。需要进行非空检查。 工艺路线表有版本，编号的唯一性在对应的Master中保证。
     */
    public java.lang.String getRouteListNumber();

    /**
     * 工艺路线表的编号，唯一标识。工艺路线表有版本，编号的唯一性在对应的Master中保证。
     */
    public void setRouteListNumber(String number);

    /**
     * 得到领部件的顺序
     */
    public Vector getPartIndex();

    /**
     * 设置零部件的殊勋
     */
    public void setPartIndex(Vector partIndex);

    /**
     * 工艺路线表的名称，需要进行非空检查。
     */
    public java.lang.String getRouteListName();

    /**
     * 工艺路线表的名称，需要进行非空检查
     */
    public void setRouteListName(String name);

    /**
     * 路线表说明
     */
    public java.lang.String getRouteListDescription();

    /**
     * 路线表说明
     */
    public void setRouteListDescription(String description);

    /**
     * 用于区分路线表是一级路线还是二级路线，值取自枚举集(一级、二级).
     */
    public java.lang.String getRouteListLevel();

    /**
     * 用于区分路线表是一级路线还是二级路线，值取自枚举集(一级、二级)
     */
    public void setRouteListLevel(String level);

    /**
     * 工艺路线表状态
     * @return
     */
    public java.lang.String getRouteListState();

    /**
     * 设置工艺路线的状态
     */
    public void setRouteListState(String state);

    /**
     * 二级路线表的单位标识:codeID
     */
    public java.lang.String getRouteListDepartment();

    /**
     * 二级路线表的单位标识:codeID
     */
    public void setRouteListDepartment(String department);

    /**
     * 获得工艺路线表对应的产品ID.
     */
    public java.lang.String getProductMasterID();

    /**
     * 获得工艺路线表对应的产品ID.
     */
    public void setProductMasterID(String productMasterID);

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
