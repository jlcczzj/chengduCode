/**
 * 生成程序FilterPartInfo.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.model;

import com.faw_qm.framework.service.BaseValueInfo;


public class SameMaterialInfo extends BaseValueInfo implements SameMaterialIfc
{
    /* （非 Javadoc）
     * @see com.faw_qm.framework.service.BaseValueInfo#getIdentity()
     */
    public String getIdentity()
    {
        return "SameMaterial:" + partNumber + routeCode + sameMaterialNumber;
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String partNumber;

    private String routeCode;
    
    private String sameMaterialNumber;

    public String getPartNumber()
    {
    	return partNumber;
    }
	
	public void setPartNumber(String number)
	{
		this.partNumber=number;
	}
	
	public String getRouteCode()
	{
		return this.routeCode;
	}
	
	public void setRouteCode(String code)
	{
		this.routeCode=code;
	}
	
	public String getSameMaterialNumber()
	{
		return sameMaterialNumber;
	}
	
	public void setSameMaterialNumber(String number)
	{
		this.sameMaterialNumber=number;
	}


    /**
     * getBsoName
     *
     * @return String
     */
    public String getBsoName()
    {
        return "SameMaterial";
    }
}
