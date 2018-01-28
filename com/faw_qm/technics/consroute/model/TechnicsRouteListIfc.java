/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * CR1 2011/12/22 徐春英       原因：艺准增加预留属性
 */

package com.faw_qm.technics.consroute.model;

import java.util.Vector;

import com.faw_qm.enterprise.model.RevisionControlledIfc;
import com.faw_qm.framework.exceptions.QMException;

/**
 * 因其为最终使用类且不被其它包使用，为建立Map. 其他类同之。
 * 具体属性见ejb.
 */

/**
 * 工艺路线表值对象接口 <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company: faw-qm</p>
 * @author 管春元
 * @version 1.0
 */
public interface TechnicsRouteListIfc extends RevisionControlledIfc
{
    /**
     * 工艺路线表的编号，唯一标识。
     * @return java.lang.String
     */
    public String getRouteListNumber();

    /**
     * 工艺路线表的编号，唯一标识
     * @param number - 字符长度不超过30
     * @throws QMException 
     */
    public void setRouteListNumber(String number) throws QMException;

    /**
     * 得到领部件的顺序
     */
    public Vector getPartIndex();

    /**
     * 设置零部件的殊勋
     */
    public void setPartIndex(Vector partIndex);

    /**
     * 工艺路线表的名称，需要进行非空检查
     * @return java.lang.String
     */
    public String getRouteListName();

    /**
     * 工艺路线表的名称，需要进行非空检查
     * @param name
     * @throws QMException 
     */
    public void setRouteListName(String name) throws QMException;

    /**
     * 路线表说明
     * @return java.lang.String
     */
    public String getRouteListDescription();

    /**
     * 路线表说明
     * @param description
     */
    public void setRouteListDescription(String description);

    /**
     * 用于区分路线表是一级路线还是二级路线，值取自枚举集(一级、二级)
     * @return java.lang.String
     */
    public String getRouteListLevel();

    /**
     * 用于区分路线表是一级路线还是二级路线，值取自枚举集(一级、二级)
     * @param level
     */
    public void setRouteListLevel(String level);

    /**
     * 工艺路线表的状态 前准、试准、艺准、临准等，在代码管理中维护
     * @return java.lang.String
     */
    public String getRouteListState();

    /**
     * 设置工艺路线表的状态
     * @param state
     */
    public void setRouteListState(String state);

    /**
     * 二级路线表的单位代码ID
     * @return java.lang.String
     */
    public String getRouteListDepartment();

    /**
     * 二级路线表的单位代码ID
     * @param department - 二级路线表的单位代码ID
     */
    public void setRouteListDepartment(String department);

    /**
     * 二级路线表的单位名称.
     * @return java.lang.String
     */
    public String getDepartmentName();

    /**
     * 二级路线表的单位名称.此方法有EJB调用。
     * @param department - 二级路线表的单位名称.
     */
    public void setDepartmentName(String departmentName);

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
