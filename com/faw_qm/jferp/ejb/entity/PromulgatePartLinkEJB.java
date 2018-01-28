/**
 * ���ɳ���PromulgatePartLinkEJB.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.ejb.entity;

import javax.ejb.CreateException;
import com.faw_qm.jferp.model.PromulgatePartLinkInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkEJB;

/**
 * <p>Title:����֪ͨ���������ӿ�ʵ�� </p>
 * <p>Description: ����֪ͨ���������ӿ�ʵ��</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
abstract public class PromulgatePartLinkEJB extends BinaryLinkEJB
{
    /**
     * ���캯����
     */
    public PromulgatePartLinkEJB()
    {
        super();
    }

    /**
     * ��ȡҵ�������ơ�
     * @return "PromulgatePartLink"
     */
    public String getBsoName()
    {
        return "JFPromulgatePartLink";
    }

    /**
     * ���ù������ֵ����
     * @param info BaseValueIfc
     * @throws QMException
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
//        PromulgatePartLinkInfo pInfo = (PromulgatePartLinkInfo) info;
    }

    /**
     * ��ȡ�������ֵ����
     * @return BaseValueIfc
     * @throws QMException
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        PromulgatePartLinkInfo linkInfo = new PromulgatePartLinkInfo();
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
//        PromulgatePartLinkInfo linkInfo = (PromulgatePartLinkInfo) info;
    }

    /**
     * ����ֵ������¹������ҵ�����
     * @param info BaseValueIfc
     * @throws QMException
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
//        PromulgatePartLinkInfo linkInfo = (PromulgatePartLinkInfo) info;
    }
}
