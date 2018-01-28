/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.entity;

import javax.ejb.CreateException;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkEJB;
import com.faw_qm.technics.consroute.model.*;


/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public abstract class CompletListLinkEJB extends BinaryLinkEJB
{

    /**
     * ��ȡֵ����
     * @return ֵ����
     */
    public BaseValueIfc getValueInfo()
            throws QMException
    {
        CompletListLinkInfo info = new CompletListLinkInfo();
        setValueInfo(info);
        return info;
    }


    /**
     * ����ֵ����
     * @param info  ֵ����
     */
    public void setValueInfo(BaseValueIfc info)
            throws QMException
    {
        super.setValueInfo(info);
    }


    /**
     * �������
     * @return ����
     */
    public String getBsoName()
    {
        return "consCompletListLink";
    }


    /**
     * ����ֵ���󴴽�ҵ�����
     * @param info  ֵ����
     */
    public void createByValueInfo(BaseValueIfc info)
            throws CreateException
    {
        super.createByValueInfo(info);
    }


    /**
     * ����ֵ�����޸�ҵ�����
     * @param info  ֵ����
     */
    public void updateByValueInfo(BaseValueIfc info)
            throws QMException
    {
        super.updateByValueInfo(info);
    }
}
