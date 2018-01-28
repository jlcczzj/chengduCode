/**
 * ���ɳ��� RouteListDocLinkInfo.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.BinaryLinkInfo;

/**
 * <p>Title:RouteListDocLinkInfo.java</p> <p>Description:��Դ�ĵ�����ֵ���� </p> <p>Package:com.faw_qm.technics.consroute.model</p> <p>ProjectName:CAPP</p> <p>Copyright: Copyright (c) 2005</p> <p>Company:һ������</p>
 * @author unascribed
 * @version 1.0
 */
public class RouteListDocLinkInfo extends BinaryLinkInfo
{
    /**
     * ȱʡ������
     */
    public RouteListDocLinkInfo()
    {}

    /**
     * ������������
     * @param equipment �豸��BsoID
     * @param docMaster �ĵ�Master��BsoID
     */
    public RouteListDocLinkInfo(String equipment, String docMaster)
    {
        super(equipment, docMaster);
    }

    /**
     * ���BsoName
     * @return String BsoName
     */
    public String getBsoName()
    {
        return "consEquipmentDocLink";
    }

    static final long serialVersionUID = 1L;

}
