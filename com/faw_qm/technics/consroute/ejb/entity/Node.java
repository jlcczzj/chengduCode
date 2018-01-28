/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.entity;

import com.faw_qm.framework.service.BsoReference;

/**
 * 此接口暂时无意义，如删除，需修改自省文件。 <p>Title: </p> <p>Description: 工艺路线服务端类</p> <p>Copyright: Copyright (c) 2004.2</p> <p>Company: 一汽启明公司</p>
 * @author 赵立彬
 * @version 1.0
 */
public interface Node extends BsoReference
{
    /**
     * 获得工艺路线ID.
     * @return java.lang.String
     * @roseuid 4032F60B0364
     */
    public String getRouteID();

    /**
     * 设置工艺路线ID.
     * @roseuid 4032F613026B
     */
    public void setRouteID(String routeID);
}
