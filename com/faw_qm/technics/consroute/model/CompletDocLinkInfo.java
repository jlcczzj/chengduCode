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

public class CompletDocLinkInfo extends BinaryLinkInfo
{
    /**
     * ȱʡ������
     */
    public CompletDocLinkInfo()
    {
    }


    /**
     * ������������
     * @param equipment �豸��BsoID
     * @param docMaster �ĵ�Master��BsoID
     */
    public CompletDocLinkInfo(String equipment, String docMaster)
    {
        super(equipment, docMaster);
    }


    /**
     * ���BsoName
     * @return String BsoName
     */
    public String getBsoName()
    {
        return "consCompletDocLink";
    }

    static final long serialVersionUID = 1L;

}
