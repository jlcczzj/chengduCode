/**
 * ���ɳ���PromulgateProductLinkInfo.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.model;

import com.faw_qm.framework.service.BinaryLinkInfo;

/**
 * <p>Title: ����֪ͨ�����Ʒ����</p>
 * <p>Description: ����֪ͨ�����Ʒ����</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public class PromulgateProductLinkInfo extends BinaryLinkInfo
{
    /**
     * ���BsoName
     * @return BsoName
     */
    public String getBsoName()
    {
        return "PromulgateProductLink";
    }//end getBsoName()

    /**
     * ȱʡ������
     */
    public PromulgateProductLinkInfo()
    {
    }//end DocUsageLinkInfo()

    /**
     * ���������캯�����Ƽ�ʹ��
     * @param usedBy ����ֵ֪ͨ����BsoID
     * @param uses �����ֵ����BsoID
     */
    public PromulgateProductLinkInfo(String usedBy, String uses)
    {
        super(usedBy, uses);
    }//end IteratedUsageLInkInfo(String,String)

    static final long serialVersionUID = -1L;
}//end class DocUsageLinkInfo
