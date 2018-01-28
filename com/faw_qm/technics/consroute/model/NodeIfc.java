/**
 * 生成程序 NodeIfc.java    1.0    2005/07/01
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title:NodeIfc.java</p> <p>Description:节点接口 </p> <p>Package:com.faw_qm.technics.consroute.model</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:一汽启明</p>
 * @author unascribed
 * @version 1.0
 */
public interface NodeIfc extends BaseValueIfc
{
    /**
     * 获得工艺路线值对象。
     * @return com.faw_qm.technics.consroute.model.TechnicsRouteIfc
     * @roseuid 4032F5BD01BD
     */
    public TechnicsRouteIfc getRouteInfo();

    /**
     * 设置工艺路线值对象。
     * @param routeInfo
     * @roseuid 403AB50F0043
     */
    public void setRouteInfo(TechnicsRouteIfc routeInfo);
}
