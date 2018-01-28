package com.faw_qm.jferp.ejb.entity;

import com.faw_qm.framework.service.BsoReference;

public interface SameMaterial extends BsoReference
{
	public String getPartNumber();
	
	public void setPartNumber(String number);
	
	public String getRouteCode();
	
	public void setRouteCode(String code);
	
	public String getSameMaterialNumber();
	
	public void setSameMaterialNumber(String number);
}