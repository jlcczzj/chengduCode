/**
 * ���ɳ���PromulgateNotifyHTMLAction.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.ejb.entity;

import com.faw_qm.framework.service.BsoReference;

/**
 * <p>Title: ����������ӿ�</p>
 * <p>Description: ����������ӿ�</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public interface FilterPart extends BsoReference
{
    public void setNoticeType(String noticeType);

    public String getNoticeType();

    public void setNoticeNumber(String noticeNumber);

    public String getNoticeNumber();

    public void setState(String state);

    public String getState();

    public void setVersionValue(String versionValue);

    public String getVersionValue();

    public void setPartNumber(String partNumber);

    public String getPartNumber();
    
    public String getRoute();
    
    public void setRoute(String route);
}
