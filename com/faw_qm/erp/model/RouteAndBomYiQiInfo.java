/**
 * 生成程序MaterialStructureInfo.java	1.0              2007-9-25
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.model;

import com.faw_qm.framework.service.BaseValueInfo;

/**
 * <p>Title: 。</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: 启明信息技术股份有限公司</p>
 * @author 谢斌
 * @version 1.0
 */
public class RouteAndBomYiQiInfo extends BaseValueInfo implements RouteAndBomYiQiIfc
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private RouteAndBomYiQiMap routeAndBomYiQiMap;

    /**
     * 
     */
    public RouteAndBomYiQiInfo()
    {
        super();
        routeAndBomYiQiMap = new RouteAndBomYiQiMap();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.framework.service.BaseValueInfo#getBsoName()
     */
    public String getBsoName()
    {
        return "RouteAndBomYiQi";
    }

    


    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getParentPartNumber()
     */
    public String getPartNumber()
    {
        return routeAndBomYiQiMap.getPartNumber();
    }
    public String getPartVersion()
    {
        return routeAndBomYiQiMap.getPartVersion();
    }
    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getParentPartVersion()
     */
    public String getRouteTZSNumber()
    {
        return routeAndBomYiQiMap.getRouteTZSNumber();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getQuantity()
     */
    public String getZhunBei()
    {
        return routeAndBomYiQiMap.getZhunBei();
    }
    public String getZhunBei1()
    {
        return routeAndBomYiQiMap.getZhunBei1();
    }
   

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentNumber(java.lang.String)
     */
    public void setPartNumber(String partNumber)
    {
    	routeAndBomYiQiMap.setPartNumber(partNumber);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentPartNumber(java.lang.String)
     */
    public void setPartVersion(String partVersion)
    {
    	routeAndBomYiQiMap.setPartVersion(partVersion);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentPartVersion(java.lang.String)
     */
    public void setRouteTZSNumber(String routeTZSNumber)
    {
    	routeAndBomYiQiMap.setRouteTZSNumber(routeTZSNumber);
    }
    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentPartVersion(java.lang.String)
     */
    public void setZhunBei(String zhunBei)
    {
    	routeAndBomYiQiMap.setZhunBei(zhunBei);
    }
    public void setZhunBei1(String zhunBei1)
    {
    	routeAndBomYiQiMap.setZhunBei1(zhunBei1);
    }
    
}
