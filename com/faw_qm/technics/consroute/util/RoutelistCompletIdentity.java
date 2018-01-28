/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/08
 */
package com.faw_qm.technics.consroute.util;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.technics.consroute.model.*;
import com.faw_qm.unique.ejb.entity.IdentifyObject;


/**
 * 材料唯一性标识类
 * @author 罗桥
 * @version 1.0
 */

public class RoutelistCompletIdentity extends IdentifyObject
{
    /**
     * 缺省构造器
     */
    public RoutelistCompletIdentity()
    {
    }


    /**
     * 参数化构造器
     * @param number 材料编号
     */
    public RoutelistCompletIdentity(String number)
    {
        setCompletNumber(number);
    }


    /**
     * 获得材料编号
     * @return String 材料编号
     */
    public String getCompletNumber()
    {
        return equipmentNumber;
    }


    /**
     * 设置材料编号
     * @param number 材料编号
     */
    public void setCompletNumber(String number)
    {
        equipmentNumber = number;
    }


    /**
     * 该方法由IdentifyService 调用，将所有唯一属性的值从IdentifyObject子类中拷贝回
     * 业务类的值对象中。
     * @param valueInfo
     */
    public void setToObject(BaseValueIfc valueInfo)
    {
        ((RoutelistCompletInfo) valueInfo).setCompletNum(getCompletNumber());
    }


    /**
     * 获取唯一标识
     * @return String
     */
    public String getIdentity()
    {
        return equipmentNumber;
    }

    private String equipmentNumber;
}
