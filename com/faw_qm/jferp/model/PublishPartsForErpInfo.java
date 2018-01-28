/**
 * ���ɳ���MaterialStructureInfo.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.model;

import com.faw_qm.framework.service.BaseValueInfo;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public class PublishPartsForErpInfo extends BaseValueInfo implements PublishPartsForErpIfc
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private PublishPartsForErpMap publishPartsForErpMap;

    /**
     * 
     */
    public PublishPartsForErpInfo()
    {
        super();
        publishPartsForErpMap = new PublishPartsForErpMap();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.BaseValueInfo#getBsoName()
     */
    public String getBsoName()
    {
        return "JFPublishPartsForErp";
    }

    


    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#getParentPartNumber()
     */
    public String getPartBsoId()
    {
        return publishPartsForErpMap.getPartBsoId();
    }
    public String getPartState()
    {
        return publishPartsForErpMap.getPartState();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#getQuantity()
     */
    public String getZhunBei()
    {
        return publishPartsForErpMap.getZhunBei();
    }
    public int getZhunBei1()
    {
        return publishPartsForErpMap.getZhunBei1();
    }
   

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#setParentNumber(java.lang.String)
     */
    public void setPartBsoId(String partBsoid)
    {
    	publishPartsForErpMap.setPartBsoId(partBsoid);
    }

    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#setParentPartNumber(java.lang.String)
     */
    public void setPartState(String partState)
    {
    	publishPartsForErpMap.setPartState(partState);
    }
    /* ���� Javadoc��
     * @see com.faw_qm.jferp.model.MaterialStructureIfc#setParentPartVersion(java.lang.String)
     */
    public void setZhunBei(String zhunBei)
    {
    	publishPartsForErpMap.setZhunBei(zhunBei);
    }
    public void setZhunBei1(int zhunBei1)
    {
    	publishPartsForErpMap.setZhunBei1(zhunBei1);
    }
    
}
