/**
 * ���ɳ���FilterPartInfo.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.model;

import com.faw_qm.framework.service.BaseValueInfo;


public class SameMaterialInfo extends BaseValueInfo implements SameMaterialIfc
{
    /* ���� Javadoc��
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
