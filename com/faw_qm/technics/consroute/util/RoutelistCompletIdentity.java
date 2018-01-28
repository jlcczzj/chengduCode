/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/08
 */
package com.faw_qm.technics.consroute.util;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.technics.consroute.model.*;
import com.faw_qm.unique.ejb.entity.IdentifyObject;


/**
 * ����Ψһ�Ա�ʶ��
 * @author ����
 * @version 1.0
 */

public class RoutelistCompletIdentity extends IdentifyObject
{
    /**
     * ȱʡ������
     */
    public RoutelistCompletIdentity()
    {
    }


    /**
     * ������������
     * @param number ���ϱ��
     */
    public RoutelistCompletIdentity(String number)
    {
        setCompletNumber(number);
    }


    /**
     * ��ò��ϱ��
     * @return String ���ϱ��
     */
    public String getCompletNumber()
    {
        return equipmentNumber;
    }


    /**
     * ���ò��ϱ��
     * @param number ���ϱ��
     */
    public void setCompletNumber(String number)
    {
        equipmentNumber = number;
    }


    /**
     * �÷�����IdentifyService ���ã�������Ψһ���Ե�ֵ��IdentifyObject�����п�����
     * ҵ�����ֵ�����С�
     * @param valueInfo
     */
    public void setToObject(BaseValueIfc valueInfo)
    {
        ((RoutelistCompletInfo) valueInfo).setCompletNum(getCompletNumber());
    }


    /**
     * ��ȡΨһ��ʶ
     * @return String
     */
    public String getIdentity()
    {
        return equipmentNumber;
    }

    private String equipmentNumber;
}
