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
import com.faw_qm.erp.model.MaterialPartStructureInfo;
import com.faw_qm.erp.model.RouteAndBomYiQiInfo;
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
public abstract class RouteAndBomYiQiEJB extends BsoReferenceEJB
{
    /**
     * ���ø����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @param parentPartNumber �����š�
     */
    public abstract void setPartNumber(String partNumber);

    /**
     * ��ȡ�����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @return �����š�
     */
    public abstract String getPartNumber();
    public abstract void setPartVersion(String partVersion);

    /**
     * ��ȡ�����ţ������ϲ��ǰ������ţ����ڼ��ṹ�Ƿ���ġ�
     * @return �����š�
     */
    public abstract String getPartVersion();

    /**
     * ���ø����汾�������ϲ��ǰ����汾�����ڼ��ṹ�Ƿ���ġ�
     * @param parentPartVersion �����汾��
     */
    public abstract void setRouteTZSNumber(String routeTZSNumber);

    /**
     * ��ȡ�����汾�������ϲ��ǰ����汾�����ڼ��ṹ�Ƿ���ġ�
     * @return �����汾��
     */
    public abstract String getRouteTZSNumber();

    /**
     * ���ø����ϣ���¼��ֺ�����ϸ���š�
     * @param parentNumber �����ϡ�
     */
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
    public abstract void setZhunBei1(String zhunBei1);

    /**
     * ��ȡ�����ϣ���¼��ֺ����������š�
     * @return �����ϡ�
     */
    public abstract String getZhunBei1();

    
    
    public BaseValueIfc getValueInfo() throws QMException
    {
    	RouteAndBomYiQiInfo info = new RouteAndBomYiQiInfo();
        setValueInfo(info);
        return info;
    }

    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        RouteAndBomYiQiInfo info1 = (RouteAndBomYiQiInfo) info;
        info1.setPartNumber(getPartNumber());
        info1.setPartVersion(getPartVersion());
        info1.setRouteTZSNumber(getRouteTZSNumber());
        info1.setZhunBei(getZhunBei());
        info1.setZhunBei1(getZhunBei1());
    }

    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        RouteAndBomYiQiInfo info1 = (RouteAndBomYiQiInfo) info;
        setPartNumber(info1.getPartNumber());
        setPartVersion(info1.getPartVersion());
        setRouteTZSNumber(info1.getRouteTZSNumber());
        setZhunBei(info1.getZhunBei());
        setZhunBei1(info1.getZhunBei1());
    }

    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        RouteAndBomYiQiInfo info1 = (RouteAndBomYiQiInfo) info;
        setPartNumber(info1.getPartNumber());
        setPartVersion(info1.getPartVersion());
        setRouteTZSNumber(info1.getRouteTZSNumber());
        setZhunBei(info1.getZhunBei());
        setZhunBei1(info1.getZhunBei1());
    }

    public String getBsoName()
    {
        return "RouteAndBomYiQi";
    }
}
