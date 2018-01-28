/**
 * 生成程序FilterPartIfc.java   1.0              2006-11-6
 * 版权归启明信息技术股份有限公司所有
 * 本程序属启明信息技术股份有限公司的私有机要资料
 * 未经本公司授权不得非法传播和盗用
 * 可在本公司授权范围内使用本程序
 * 保留所有权利
 */
package com.faw_qm.erp.model;

import com.faw_qm.framework.service.BaseValueIfc;

public interface SameMaterialIfc extends BaseValueIfc
{
	public String getPartNumber();
	
	public void setPartNumber(String number);
	
	public String getRouteCode();
	
	public void setRouteCode(String code);
	
	public String getSameMaterialNumber();
	
	public void setSameMaterialNumber(String number);
}