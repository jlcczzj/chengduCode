/**
 * 生成程序MaterialStructureInfo.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.jferp.model;

import com.faw_qm.framework.service.BaseValueInfo;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
 * @version 1.0
 */
public class PublishRouteForErpInfo extends BaseValueInfo implements PublishRouteForErpIfc
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private PublishRouteForErpMap PublishRouteForErpMap;

    /**
     * 
     */
    public PublishRouteForErpInfo()
    {
        super();
        PublishRouteForErpMap = new PublishRouteForErpMap();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.framework.service.BaseValueInfo#getBsoName()
     */
    public String getBsoName()
    {
        return "JFPublishRouteForErp";
    }

    


    public String getRouteList()
    {
        return PublishRouteForErpMap.getRouteList();
    }

    public String getZhunBei()
    {
        return PublishRouteForErpMap.getZhunBei();
    }

    public void setRouteList(String routeList)
    {
    	PublishRouteForErpMap.setRouteList(routeList);
    }

    public void setZhunBei(String zhunBei)
    {
    	PublishRouteForErpMap.setZhunBei(zhunBei);
    }

}
