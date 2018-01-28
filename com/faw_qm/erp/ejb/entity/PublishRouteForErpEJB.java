/**
 * ���ɳ���MaterialStructureEJB.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.ejb.entity;

import javax.ejb.CreateException;
import com.faw_qm.erp.model.PublishRouteForErpInfo;
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
public abstract class PublishRouteForErpEJB extends BsoReferenceEJB
{
    /**
     * ���ø����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @param parentPartNumber �����š�
     */
    public abstract void setRouteList(String routeList);

    /**
     * ��ȡ�����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @return �����š�
     */
    public abstract String getRouteList();

    
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

    
    
    public BaseValueIfc getValueInfo() throws QMException
    {
    	PublishRouteForErpInfo info = new PublishRouteForErpInfo();
        setValueInfo(info);
        return info;
    }

    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        PublishRouteForErpInfo info1 = (PublishRouteForErpInfo) info;
        info1.setRouteList(getRouteList());
        info1.setZhunBei(getZhunBei());
    }

    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        PublishRouteForErpInfo info1 = (PublishRouteForErpInfo) info;
        setRouteList(info1.getRouteList());
        setZhunBei(info1.getZhunBei());
    }

    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        PublishRouteForErpInfo info1 = (PublishRouteForErpInfo) info;
        setRouteList(info1.getRouteList());
        setZhunBei(info1.getZhunBei());
    }

    public String getBsoName()
    {
        return "PublishRouteForErp";
    }
}
