/**
 * ���ɳ���FileNameLinkInfo.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.model;

import com.faw_qm.framework.service.BaseValueInfo;

/**
 * <p>Title: �������ļ����ƹ���ʵ��</p>
 * <p>Description: �������ļ����ƹ���ʵ��</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public class FileNameLinkInfo extends BaseValueInfo implements FileNameLinkIfc
{
    String fileName;

    String notice;

    /* ���� Javadoc��
     * @see com.faw_qm.framework.service.BaseValueInfo#getIdentity()
     */
    public String getIdentity()
    {
        return "FileNameLink";
    }

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public FileNameLinkInfo()
    {
    }

    /**
     * getBsoName
     *
     * @return String
     */
    public String getBsoName()
    {
        return "FileNameLink";
    }

    /**
     * �����ļ���
     * @param fileName String
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    /**
     * ����ļ���
     * @return String
     */
    public String getFileName()
    {
        return this.fileName;
    }

    /**
   * ���ò��ö���
   * @param notice String
   */

    public void setNotice(String notice)
    {
        this.notice = notice;
    }

    /**
  * ��ò��ö���
  * @return String
  */
  public String getNotice()
    {
        return notice;
    }
}
