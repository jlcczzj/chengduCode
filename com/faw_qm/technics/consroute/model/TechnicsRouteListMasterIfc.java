/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */

package com.faw_qm.technics.consroute.model;

import com.faw_qm.enterprise.model.MasterIfc;
import com.faw_qm.framework.service.BaseValueIfc;

/**
 * ����·�߱�����Ϣֵ���� <p>Title: </p> <p>Description: </p> <p>Copyright: Copyright (c) 2005</p> <p>Company:faw-qm </p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public interface TechnicsRouteListMasterIfc extends BaseValueIfc, MasterIfc
{
    /**
     * ·�߱�ı�� Ψһ��
     */
    public java.lang.String getRouteListNumber();

    /**
     * ����·�߱�ı��
     */
    public void setRouteListNumber(String number);

    /**
     * �õ�·�߱���
     */
    public java.lang.String getRouteListName();

    /**
     * ��������ر���
     */
    public void setRouteListName(String name);

    /**
     * ��ù���·�߱��Ӧ�Ĳ�ƷID.
     * @return java.lang.String
     */
    public String getProductMasterID();

    /**
     * ��ù���·�߱��Ӧ�Ĳ�ƷID.
     * @param productMasterID - �㲿��ID.
     */
    public void setProductMasterID(String productMasterID);

}
