/**
 * 生成程序MaterialStructureMap.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.model;

import com.faw_qm.framework.service.ObjectMappable;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
 * @version 1.0
 */
public class PublishRouteForErpMap implements ObjectMappable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String routeList;

    private String zhunBei;
    /**
     * @return 返回 parentPartNumber。
     */
    public String getRouteList()
    {
        return routeList;
    }

    /**
     * @param parentPartNumber 要设置的 parentPartNumber。
     */
    public void setRouteList(String routeList)
    {
        this.routeList = routeList;
    }

    /**
     * @return 返回 quantity。
     */
    public String getZhunBei()
    {
        return zhunBei;
    }

    /**
     * @param quantity 要设置的 quantity。
     */
    public void setZhunBei(String zhunBei)
    {
        this.zhunBei = zhunBei;
    }
}
