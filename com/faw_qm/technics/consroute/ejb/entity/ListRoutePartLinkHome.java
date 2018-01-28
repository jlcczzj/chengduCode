/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceHome;

/**
 * 工艺路线对应的产品结构可以选择。产品的子零件 <p>Title: </p> <p>Description: 工艺路线服务端类</p> <p>Copyright: Copyright (c) 2004.2</p> <p>Company: 一汽启明公司</p>
 * @author 赵立彬
 * @version 1.0
 */
public interface ListRoutePartLinkHome extends BsoReferenceHome
{
    /**
     * 用于根据主键恢复对象
     */
    public ListRoutePartLink findByPrimaryKey(String bsoID) throws FinderException;

    /**
     * 根据值对象建立业务对象
     */
    public ListRoutePartLink create(BaseValueIfc info) throws CreateException;

    /**
     * 根据值对象和指定时间戳建立业务对象
     */
    public ListRoutePartLink create(BaseValueIfc info, Timestamp ct, Timestamp mt) throws CreateException;
}
