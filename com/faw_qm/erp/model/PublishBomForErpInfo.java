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
public class PublishBomForErpInfo extends BaseValueInfo implements PublishBomForErpIfc
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private PublishBomForErpMap publishBomForErpMap;

    /**
     * 
     */
    public PublishBomForErpInfo()
    {
        super();
        publishBomForErpMap = new PublishBomForErpMap();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.framework.service.BaseValueInfo#getBsoName()
     */
    public String getBsoName()
    {
        return "PublishBomForErp";
    }

    


    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getParentPartNumber()
     */
    public String getPartBsoId()
    {
        return publishBomForErpMap.getPartBsoId();
    }
    public String getPartState()
    {
        return publishBomForErpMap.getPartState();
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#getQuantity()
     */
    public String getZhunBei()
    {
        return publishBomForErpMap.getZhunBei();
    }
    public int getZhunBei1()
    {
        return publishBomForErpMap.getZhunBei1();
    }
   

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentNumber(java.lang.String)
     */
    public void setPartBsoId(String partBsoid)
    {
    	publishBomForErpMap.setPartBsoId(partBsoid);
    }

    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentPartNumber(java.lang.String)
     */
    public void setPartState(String partState)
    {
    	publishBomForErpMap.setPartState(partState);
    }
    /* （非 Javadoc）
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentPartVersion(java.lang.String)
     */
    public void setZhunBei(String zhunBei)
    {
    	publishBomForErpMap.setZhunBei(zhunBei);
    }
    public void setZhunBei1(int zhunBei1)
    {
    	publishBomForErpMap.setZhunBei1(zhunBei1);
    }
    
}
