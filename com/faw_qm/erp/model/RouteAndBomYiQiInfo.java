/**
 * ���ɳ���MaterialStructureInfo.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.model;

import com.faw_qm.framework.service.BaseValueInfo;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
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

    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.BaseValueInfo#getBsoName()
     */
    public String getBsoName()
    {
        return "RouteAndBomYiQi";
    }

    


    /* ���� Javadoc��
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
    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getParentPartVersion()
     */
    public String getRouteTZSNumber()
    {
        return routeAndBomYiQiMap.getRouteTZSNumber();
    }

    /* ���� Javadoc��
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
   

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentNumber(java.lang.String)
     */
    public void setPartNumber(String partNumber)
    {
    	routeAndBomYiQiMap.setPartNumber(partNumber);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentPartNumber(java.lang.String)
     */
    public void setPartVersion(String partVersion)
    {
    	routeAndBomYiQiMap.setPartVersion(partVersion);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentPartVersion(java.lang.String)
     */
    public void setRouteTZSNumber(String routeTZSNumber)
    {
    	routeAndBomYiQiMap.setRouteTZSNumber(routeTZSNumber);
    }
    /* ���� Javadoc��
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
