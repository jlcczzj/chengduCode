/**
 * ���ɳ���FilterPartIfc.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.model;

import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title: ���˷�������ӿ�</p>
 * <p>Description: ���˷�������ӿ�</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public interface FilterPartIfc extends BaseValueIfc
{
    public String getNoticeType();

    public String getNoticeNumber();

    public String getState();

    public String getVersionValue();

    public void setPartNumber(String partNumber);

    public void setNoticeType(String noticeType);

    public void setNoticeNumber(String noticeNumber);

    public void setState(String state);

    public void setVersionValue(String versionValue);

    public String getPartNumber();
    
    public String getRoute();
    
    public void setRoute(String route);
}
