/**
 * ���ɳ���PromulgateDocLinkEJB.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.ejb.entity;

import javax.ejb.CreateException;

import com.faw_qm.cderp.model.PromulgateDocLinkInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BinaryLinkEJB;

/**
 * <p>Title: ����֪ͨ����ĵ�����ʵ��</p>
 * <p>Description: ����֪ͨ����ĵ�����ʵ��</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
abstract public class PromulgateDocLinkEJB extends BinaryLinkEJB
{
    /**
     * ���캯����
     */
    public PromulgateDocLinkEJB()
    {
        super();
    }

    /**
     * ��ȡҵ�������ơ�
     * @return "PromulgateDocLink"
     */
    public String getBsoName()
    {
        return "JFPromulgateDocLink";
    }

    /**
     * ���ù������ֵ����
     * @param info BaseValueIfc
     * @throws QMException
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
//        PromulgateDocLinkInfo pInfo = (PromulgateDocLinkInfo) info;
    }

    /**
     * ��ȡ�������ֵ����
     * @return BaseValueIfc
     * @throws QMException
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        PromulgateDocLinkInfo linkInfo = new PromulgateDocLinkInfo();
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
//        PromulgateDocLinkInfo linkInfo = (PromulgateDocLinkInfo) info;
    }

    /**
     * ����ֵ������¹������ҵ�����
     * @param info BaseValueIfc
     * @throws QMException
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
//        PromulgateDocLinkInfo linkInfo = (PromulgateDocLinkInfo) info;
    }
}
