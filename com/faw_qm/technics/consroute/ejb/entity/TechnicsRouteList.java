/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * CR1 2011/12/22 �촺Ӣ      ԭ��:����Ԥ������
 */

package com.faw_qm.technics.consroute.ejb.entity;

import java.util.Vector;

import com.faw_qm.enterprise.ejb.entity.RevisionControlled;

/**
 * һ���㲿�������ж������·�߱�
 * һ������·�߱��ж������·�ߡ�
 * TechnicsRouteListIfc��Ψһ��ʶ�ɱ�ʶ������������
 */

/**
 * <p>Title: </p> <p>Description: ����·�߷������</p> <p>Copyright: Copyright (c) 2004.2</p> <p>Company: һ��������˾</p>
 * @author ������
 * @mener skybird
 * @version 1.0
 */
public interface TechnicsRouteList extends RevisionControlled
{
    /**
     * ����·�߱�ı�ţ�Ψһ��ʶ����Ҫ���зǿռ�顣 ����·�߱��а汾����ŵ�Ψһ���ڶ�Ӧ��Master�б�֤��
     */
    public java.lang.String getRouteListNumber();

    /**
     * ����·�߱�ı�ţ�Ψһ��ʶ������·�߱��а汾����ŵ�Ψһ���ڶ�Ӧ��Master�б�֤��
     */
    public void setRouteListNumber(String number);

    /**
     * �õ��첿����˳��
     */
    public Vector getPartIndex();

    /**
     * �����㲿������ѫ
     */
    public void setPartIndex(Vector partIndex);

    /**
     * ����·�߱�����ƣ���Ҫ���зǿռ�顣
     */
    public java.lang.String getRouteListName();

    /**
     * ����·�߱�����ƣ���Ҫ���зǿռ��
     */
    public void setRouteListName(String name);

    /**
     * ·�߱�˵��
     */
    public java.lang.String getRouteListDescription();

    /**
     * ·�߱�˵��
     */
    public void setRouteListDescription(String description);

    /**
     * ��������·�߱���һ��·�߻��Ƕ���·�ߣ�ֵȡ��ö�ټ�(һ��������).
     */
    public java.lang.String getRouteListLevel();

    /**
     * ��������·�߱���һ��·�߻��Ƕ���·�ߣ�ֵȡ��ö�ټ�(һ��������)
     */
    public void setRouteListLevel(String level);

    /**
     * ����·�߱�״̬
     * @return
     */
    public java.lang.String getRouteListState();

    /**
     * ���ù���·�ߵ�״̬
     */
    public void setRouteListState(String state);

    /**
     * ����·�߱�ĵ�λ��ʶ:codeID
     */
    public java.lang.String getRouteListDepartment();

    /**
     * ����·�߱�ĵ�λ��ʶ:codeID
     */
    public void setRouteListDepartment(String department);

    /**
     * ��ù���·�߱��Ӧ�Ĳ�ƷID.
     */
    public java.lang.String getProductMasterID();

    /**
     * ��ù���·�߱��Ӧ�Ĳ�ƷID.
     */
    public void setProductMasterID(String productMasterID);

    //begin CR1
    /**
     * ����Ԥ������1
     * @param attribute1
     * @return
     */
    public void setAttribute1(String attribute1);

    /**
     * ���Ԥ������1
     * @param attribute1
     * @return
     */
    public String getAttribute1();

    /**
     * ����Ԥ������2
     * @param attribute2
     * @return
     */
    public void setAttribute2(String attribute2);

    /**
     * ���Ԥ������2
     * @param attribute2
     * @return
     */
    public String getAttribute2();
    //end CR1
}
