/**
 * ���ɳ���PromulgateProductLinkEJB.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.ejb.entity;

import javax.ejb.CreateException;
import com.faw_qm.erp.model.PromulgateProductLinkInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkEJB;

/**
 * <p>Title: ����֪ͨ�������Ʒ�ӿ�ʵ��</p>
 * <p>Description: ����֪ͨ�������Ʒ�ӿ�ʵ��</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
abstract public class PromulgateProductLinkEJB extends BinaryLinkEJB
{
    /**
     * ���캯����
     */
    public PromulgateProductLinkEJB()
    {
        super();
    }

    /**
     * ��ȡҵ�������ơ�
     * @return "PromulgateProductLink"
     */
    public String getBsoName()
    {
        return "PromulgateProductLink";
    }

    /**
     * ���ù������ֵ����
     * @param info BaseValueIfc
     * @throws QMException
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
//        PromulgateProductLinkInfo pInfo = (PromulgateProductLinkInfo) info;
    }

    /**
     * ��ȡ�������ֵ����
     * @return BaseValueIfc
     * @throws QMException
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        PromulgateProductLinkInfo linkInfo = new PromulgateProductLinkInfo();
        setValueInfo(linkInfo);
        return linkInfo;
    }

    /**
     * ���ݹ�����ֵ���󴴽��������ҵ�����
     * @param info BaseValueIfc
     * @throws CreateException
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
//        PromulgateProductLinkInfo linkInfo = (PromulgateProductLinkInfo) info;
    }

    /**
     * ����ֵ������¹������ҵ�����
     * @param info BaseValueIfc
     * @throws QMException
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
//        PromulgateProductLinkInfo linkInfo = (PromulgateProductLinkInfo) info;
    }
}
