/**
 * ���ɳ���PromulgateNotifyHTMLAction.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.ejb.entity;

import com.faw_qm.framework.service.BsoReference;

/**
 * <p>Title: �������ļ����ƵĹ����ӿ�</p>
 * <p>Description:�������ļ����ƵĹ����ӿ� </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public interface FileNameLink extends BsoReference
{
    /**
     * �����ļ���
     * @param fileName String
     */
    public void setFileName(String fileName);

    /**
     * ����ļ���
     * @return String
     */
    public String getFileName();

    public void setNotice(String notice);

    public String getNotice();
}
