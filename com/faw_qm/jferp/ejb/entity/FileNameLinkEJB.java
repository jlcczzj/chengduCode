/**
 * ���ɳ���PromulgateNotifyHTMLAction.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import com.faw_qm.jferp.model.FileNameLinkInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;

/**
 * <p>Title:�������ļ����ƵĹ���ʵ��</p>
 * <p>Description: �������ļ����ƵĹ���ʵ��</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public abstract class FileNameLinkEJB extends BsoReferenceEJB
{
    public FileNameLinkEJB()
    {
    }

    /**
     * getBsoName
     *
     * @return String
     */
    public String getBsoName()
    {
        return "JFFileNameLink";
    }

    /**
     * getBsoID
     *
     * @return String
     */
    public abstract String getBsoID();

    /**
     * setBsoID
     *
     * @param string String
     */
    public abstract void setBsoID(String string);

    /**
     * getCreateTime
     *
     * @return Timestamp
     */
    public abstract Timestamp getCreateTime();

    /**
     * setCreateTime
     *
     * @param timestamp Timestamp
     */
    public abstract void setCreateTime(Timestamp timestamp);

    /**
     * getModifyTime
     *
     * @return Timestamp
     */
    public abstract Timestamp getModifyTime();

    /**
     * setModifyTime
     *
     * @param timestamp Timestamp
     */
    public abstract void setModifyTime(Timestamp timestamp);

    /**
     * �����ļ���
     * @param fileName String
     */
    public abstract void setFileName(String fileName);

    /**
     * ����ļ���
     * @return String
     */
    public abstract String getFileName();

    public abstract void setNotice(String notice);

    public abstract String getNotice();

    /**
     *��ȡҵ������Ӧ��ֵ����
     * @throws QMException
     * @return BaseValueIfc
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        FileNameLinkInfo info = new FileNameLinkInfo();
        setValueInfo(info);
        return info;
    }

    /**
     *����ҵ������Ӧ��ֵ������Ҫ�趨���������Ϣ
     * @throws QMException
     * @param info BaseValueIfc
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        FileNameLinkInfo minfo = (FileNameLinkInfo) info;
        minfo.setFileName(this.getFileName());
        minfo.setNotice(this.getNotice());
    }

    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        FileNameLinkInfo minfo = (FileNameLinkInfo) info;
        //��Ҫ��������У��,
        setFileName(minfo.getFileName());
        setNotice(minfo.getNotice());
    }

    /**
     *���ظ���ķ��������ȵ��ø������Ӧ�����������Ϊ�����ж��Ƶ�ҵ�����Ը�ֵ��
     * @throws QMException
     * @param info BaseValueIfc
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        FileNameLinkInfo minfo = (FileNameLinkInfo) info;
        setFileName(minfo.getFileName());
        setNotice(minfo.getNotice());
    }
}
