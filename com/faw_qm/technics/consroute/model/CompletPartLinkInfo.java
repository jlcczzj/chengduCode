/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */

package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BinaryLinkInfo;


/**
 * <p>Title: </p>
 * <p>��Դ�ĵ�����ֵ����</p>
 * @author �Ի�
 * @version 1.0
 */

public class CompletPartLinkInfo extends BinaryLinkInfo
{
    /**
     * ȱʡ������
     */
    public CompletPartLinkInfo()
    {
    }


    /**
     * ������������
     * @param equipment �豸��BsoID
     * @param docMaster �ĵ�Master��BsoID
     */
    public CompletPartLinkInfo(String equipment, String docMaster)
    {
        super(equipment, docMaster);
    }


    /**
     * ���BsoName
     * @return String BsoName
     */
    public String getBsoName()
    {
        return "consCompletPartLink";
    }

    static final long serialVersionUID = 1L;

}