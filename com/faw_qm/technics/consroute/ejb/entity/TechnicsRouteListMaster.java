/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.entity;

import com.faw_qm.enterprise.ejb.entity.Master;

/**
 * ·�߱�����Ϣ <p>Title: </p> <p>Description: ���Ψһ�����̳�Ψһ�԰���Identified�ӿڡ�ͨ�������ݿ�������Ψһ��Լ����֤��Ψһ�� �ڿͻ��˽�ȡ�쳣ʱ�������⴦�� ���������ʼ�����Ӧ�����°汾һ�¡� ע�⳷�����ʱ���������°汾������һ���ԡ� </p> <p>Copyright: Copyright (c) 2004.2</p>
 * <p>Company: һ��������˾</p>
 * @author ������
 * @version 1.0
 */
public interface TechnicsRouteListMaster extends Master
{
    /**
     * ����·�߱��� Ψһ��
     */
    public java.lang.String getRouteListNumber();

    /**
     * �õ�·�߱���
     */
    public void setRouteListNumber(String number);

    /**
     *����·�߱���
     */
    public java.lang.String getRouteListName();

    /**
     *�õ�·�߱���
     */
    public void setRouteListName(String name);

    /**
     * ��ù���·�߱��Ӧ�Ĳ�ƷID.
     * @return java.lang.String
     */
    public String getProductMasterID();

    /**
     * ��ù���·�߱��Ӧ�Ĳ�ƷID.
     * @param productMasterID - ��ƷID.
     */
    public void setProductMasterID(String productMasterID);

}
