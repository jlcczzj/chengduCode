/**
 * ���ɳ���PromulgateDocLinkInfo.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.model;

import com.faw_qm.framework.service.BinaryLinkInfo;

/**
 * <p>Title: ����֪ͨ�����ĵ�����</p>
 * <p>Description: ����֪ͨ�����ĵ�����</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public class PromulgateDocLinkInfo extends BinaryLinkInfo
{
    /**
     * ���BsoName
     * @return BsoName
     */
    public String getBsoName()
    {
        return "CDPromulgateDocLink";
    }//end getBsoName()

    /**
     * ȱʡ������
     */
    public PromulgateDocLinkInfo()
    {
    }//end DocUsageLinkInfo()

    /**
     * ���������캯�����Ƽ�ʹ��
     * @param usedBy ����ֵ֪ͨ����BsoID
     * @param uses �����ֵ����BsoID
     */
    public PromulgateDocLinkInfo(String usedBy, String uses)
    {
        super(usedBy, uses);
    }//end IteratedUsageLInkInfo(String,String)

    static final long serialVersionUID = -1L;
}//end class DocUsageLinkInfo
