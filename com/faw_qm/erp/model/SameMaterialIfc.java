/**
 * ���ɳ���FilterPartIfc.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
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