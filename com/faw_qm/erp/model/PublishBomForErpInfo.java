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

    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.BaseValueInfo#getBsoName()
     */
    public String getBsoName()
    {
        return "PublishBomForErp";
    }

    


    /* ���� Javadoc��
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

    /* ���� Javadoc��
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
   

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentNumber(java.lang.String)
     */
    public void setPartBsoId(String partBsoid)
    {
    	publishBomForErpMap.setPartBsoId(partBsoid);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.erp.model.MaterialStructureIfc#setParentPartNumber(java.lang.String)
     */
    public void setPartState(String partState)
    {
    	publishBomForErpMap.setPartState(partState);
    }
    /* ���� Javadoc��
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
