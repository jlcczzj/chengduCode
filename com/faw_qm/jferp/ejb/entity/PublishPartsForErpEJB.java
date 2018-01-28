/**
 * ���ɳ���MaterialStructureEJB.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.ejb.entity;

import javax.ejb.CreateException;
import com.faw_qm.jferp.model.PublishPartsForErpInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public abstract class PublishPartsForErpEJB extends BsoReferenceEJB
{
    /**
     * ���ø����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @param parentPartNumber �����š�
     */
    public abstract void setPartBsoId(String partBsoid);

    /**
     * ��ȡ�����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @return �����š�
     */
    public abstract String getPartBsoId();
    public abstract void setPartState(String partState);

    /**
     * ��ȡ�����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @return �����š�
     */
    public abstract String getPartState();

    
    public abstract void setZhunBei(String zhunBei);

    /**
     * ��ȡ�����ϣ���¼��ֺ�����ϸ���š�
     * @return �����ϡ�
     */
    public abstract String getZhunBei();

    /**
     * ���������ϣ���¼��ֺ����������š�
     * @param childNumber �����ϡ�
     */
    public abstract void setZhunBei1(int zhunBei1);

    /**
     * ��ȡ�����ϣ���¼��ֺ����������š�
     * @return �����ϡ�
     */
    public abstract int getZhunBei1();

    
    
    public BaseValueIfc getValueInfo() throws QMException
    {
    	PublishPartsForErpInfo info = new PublishPartsForErpInfo();
        setValueInfo(info);
        return info;
    }

    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        PublishPartsForErpInfo info1 = (PublishPartsForErpInfo) info;
        info1.setPartBsoId(getPartBsoId());
        info1.setPartState(getPartState());
        info1.setZhunBei(getZhunBei());
        info1.setZhunBei1(getZhunBei1());
    }

    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        PublishPartsForErpInfo info1 = (PublishPartsForErpInfo) info;
        setPartBsoId(info1.getPartBsoId());
        setPartState(info1.getPartState());
        setZhunBei(info1.getZhunBei());
        setZhunBei1(info1.getZhunBei1());
    }

    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        PublishPartsForErpInfo info1 = (PublishPartsForErpInfo) info;
        setPartBsoId(info1.getPartBsoId());
        setPartState(info1.getPartState());
        setZhunBei(info1.getZhunBei());
        setZhunBei1(info1.getZhunBei1());
    }

    public String getBsoName()
    {
        return "JFPublishPartsForErp";
    }
}
