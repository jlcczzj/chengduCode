/**
 * ���ɳ���FileNameLinkIfc.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.model;

import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title: �������ļ����ƹ����ӿ�</p>
 * <p>Description: �������ļ����ƹ����ӿ�</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public interface FileNameLinkIfc extends BaseValueIfc
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
    /**
     * ���ò��ö���
     * @param notice String
     */
    public void setNotice(String notice);
    /**
     * ��ò��ö���
     * @return String
     */
    public String getNotice();
}
