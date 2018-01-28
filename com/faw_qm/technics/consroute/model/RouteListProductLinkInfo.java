/**
 * ���ɳ��� RouteListProductLinkInfo.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * 
 */
package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BinaryLinkInfo;

//LEFTID = routeListMasterID; RIGHTID = productMasterID.
/**
 * <p>Title:RouteListProductLinkInfo.java</p> <p>Description: ·�߲�Ʒ����ֵ����</p> <p>Package:com.faw_qm.technics.consroute.model</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:һ������</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public class RouteListProductLinkInfo extends BinaryLinkInfo implements RouteListProductLinkIfc
{
    private static final long serialVersionUID = 1L;

    /**
     * ���캯��
     */
    public RouteListProductLinkInfo()
    {

    }

    /**
     * ���ҵ���������
     */
    public String getBsoName()
    {
        return "consRouteListProductLink";
    }

    /**
     * �õ�·�߱�����Ϣid
     * @return String
     */
    public String getRouteListMasterID()
    {
        return this.getLeftBsoID();
    }

    /**
     * ����·�߱�����Ϣ��id
     * @param routeListMasterID String
     */
    public void setRouteListMasterID(String routeListMasterID)
    {
        this.setLeftBsoID(routeListMasterID);
    }

    /**
     * �õ���Ʒ���첿������Ϣ��id��
     * @return String
     */
    public String getProductMasterID()
    {
        return this.getRightBsoID();
    }

    /**
     * ���ò�Ʒ���첿������Ϣ��id��
     * @param productMasterID String
     */
    public void setProductMasterID(String productMasterID)
    {
        this.setRightBsoID(productMasterID);
    }
}
