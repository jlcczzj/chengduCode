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
public class PublishRouteForErpInfo extends BaseValueInfo implements PublishRouteForErpIfc
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private PublishRouteForErpMap PublishRouteForErpMap;

    /**
     * 
     */
    public PublishRouteForErpInfo()
    {
        super();
        PublishRouteForErpMap = new PublishRouteForErpMap();
    }

    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.BaseValueInfo#getBsoName()
     */
    public String getBsoName()
    {
        return "JFPublishRouteForErp";
    }

    


    public String getRouteList()
    {
        return PublishRouteForErpMap.getRouteList();
    }

    public String getZhunBei()
    {
        return PublishRouteForErpMap.getZhunBei();
    }

    public void setRouteList(String routeList)
    {
    	PublishRouteForErpMap.setRouteList(routeList);
    }

    public void setZhunBei(String zhunBei)
    {
    	PublishRouteForErpMap.setZhunBei(zhunBei);
    }

}
