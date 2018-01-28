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

import com.faw_qm.framework.service.BaseValueInfo;

/**
 * 工艺路线值对象
 * @author 管春元
 * @version 1.0
 */
public class TechnicsRouteInfo extends BaseValueInfo implements TechnicsRouteIfc
{
    //描述
    private String description;

    private static final long serialVersionUID = 1L;
    //begin CR1
    private String modifyIdenty;
    private String attribute1;
    private String attribute2;

    //end CR1

    /**
     * 构造函数
     */
    public TechnicsRouteInfo()
    {

    }

    /**
     * 获得路线说明
     * @return String 路线说明
     */
    public String getRouteDescription()
    {
        return this.description;
    }

    /**
     * 设置路线说明。
     * @param description 路线说明
     */
    public void setRouteDescription(String description)
    {
        this.description = description;
    }

    /**
     * 得到标识
     * @return String 标识
     */
    public String getIdentity()
    {
        if(this.getBsoID() == null)
        {
            return this.getBsoName();
        }else
        {
            return this.getBsoID();
        }
    }

    /**
     * 得到业务类名
     * @return String TechnicsRoute
     */
    public String getBsoName()
    {
        return "consTechnicsRoute";
    }

    //begin CR1
    /**
     * 获得更改标记
     * @return 更改标记
     */
    public String getModifyIdenty()
    {
        return this.modifyIdenty;
    }

    /**
     * 设置更改标记
     * @param identy 更改标记
     */
    public void setModifyIdenty(String identy)
    {
        this.modifyIdenty = identy;
    }

    /**
     * 设置预留属性1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1)
    {
        this.attribute1 = attribute1;
    }

    /**
     * 获得预留属性1
     * @param attribute1
     * @return
     */
    public String getAttribute1()
    {
        return this.attribute1;
    }

    /**
     * 设置预留属性2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2)
    {
        this.attribute2 = attribute2;
    }

    /**
     * 获得预留属性2
     * @param attribute2
     * @return
     */
    public String getAttribute2()
    {
        return this.attribute2;
    }
    //end CR1
}
