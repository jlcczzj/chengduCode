/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 * CR1 2011/12/21 徐春英      原因：工艺路线补充需求说明的实现，在路线对象中增加"更改标记"属性,另外再预留两个属性
 */

package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BaseValueIfc;

/**
 * 工艺路线值对象接口 <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company:faw-qm </p>
 * @author 管春元
 * @version 1.0
 */
public interface TechnicsRouteIfc extends BaseValueIfc, CappIdentity
{

    /**
     * 路线说明
     * @return java.lang.String
     */
    public String getRouteDescription();

    /**
     * 路线说明。
     * @param description
     */
    public void setRouteDescription(String description);

    //begin CR1
    /**
     * 获得更改标记
     * @return 更改标记
     */
    public String getModifyIdenty();

    /**
     * 设置更改标记
     * @param identy 更改标记
     */
    public void setModifyIdenty(String identy);

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
