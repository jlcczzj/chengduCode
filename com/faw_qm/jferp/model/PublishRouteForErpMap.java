/**
 * ���ɳ���MaterialStructureMap.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.model;

import com.faw_qm.framework.service.ObjectMappable;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author л��
 * @version 1.0
 */
public class PublishRouteForErpMap implements ObjectMappable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String routeList;

    private String zhunBei;
    /**
     * @return ���� parentPartNumber��
     */
    public String getRouteList()
    {
        return routeList;
    }

    /**
     * @param parentPartNumber Ҫ���õ� parentPartNumber��
     */
    public void setRouteList(String routeList)
    {
        this.routeList = routeList;
    }

    /**
     * @return ���� quantity��
     */
    public String getZhunBei()
    {
        return zhunBei;
    }

    /**
     * @param quantity Ҫ���õ� quantity��
     */
    public void setZhunBei(String zhunBei)
    {
        this.zhunBei = zhunBei;
    }
}
