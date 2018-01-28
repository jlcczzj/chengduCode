/**
 * ���ɳ���PromulgateNotifyHTMLAction.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;

import com.faw_qm.cderp.model.FilterPartInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceEJB;

/**
 * <p>Title: ���������ʵ��</p>
 * <p>Description: ���������ʵ��</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ������
 * @version 1.0
 */
public abstract class FilterPartEJB extends BsoReferenceEJB
{
    public FilterPartEJB()
    {
    }

    /**
     * getBsoName
     *
     * @return String
     */
    public String getBsoName()
    {
        return "JFFilterPart";
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
     * ���ò�������
     * @param noticeType String
     */
    public abstract void setNoticeType(String noticeType);

    /**
     * ��ò�������
     * @return String
     */
    public abstract String getNoticeType();
    /**
     * ���ò��ñ��
     * @param noticeNumber String
     */
    public abstract void setNoticeNumber(String noticeNumber);
    /**
     * ��ò��ñ��
     * @return String
     */
    public abstract String getNoticeNumber();
    /**
     * ����״̬
     * @param state String
     */
    public abstract void setState(String state);
    /**
     * ���״̬
     * @return String
     */
    public abstract String getState();
    /**
     * ���ð汾��Ϣ
     * @param versionValue String
     */
    public abstract void setVersionValue(String versionValue);
    /**
     * ��ð汾��Ϣ
     * @return String
     */
    public abstract String getVersionValue();
    /**
     * ���������Ϣ
     * @param partNumber String
     */
    public abstract void setPartNumber(String partNumber);
    /**
     * ��������Ϣ
     * @return String
     */
    public abstract String getPartNumber();
    
    public abstract String getRoute();
    
    public abstract void setRoute(String route);

    /**
     *��ȡҵ������Ӧ��ֵ����
     * @throws QMException
     * @return BaseValueIfc
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        FilterPartInfo info = new FilterPartInfo();
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
        FilterPartInfo minfo = (FilterPartInfo) info;
        minfo.setNoticeNumber(this.getNoticeNumber());
        minfo.setNoticeType(this.getNoticeType());
        minfo.setPartNumber(this.getPartNumber());
        minfo.setState(this.getState());
        minfo.setVersionValue(this.getVersionValue());
        minfo.setRoute(this.getRoute());
    }

    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        FilterPartInfo minfo = (FilterPartInfo) info;
        //��Ҫ��������У��,
        setNoticeNumber(minfo.getNoticeNumber());
        setNoticeType(minfo.getNoticeType());
        setPartNumber(minfo.getPartNumber());
        setState(minfo.getState());
        setVersionValue(minfo.getVersionValue());
        setRoute(minfo.getRoute());
    }

    /**
     *���ظ���ķ��������ȵ��ø������Ӧ�����������Ϊ�����ж��Ƶ�ҵ�����Ը�ֵ��
     * @throws QMException
     * @param info BaseValueIfc
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        FilterPartInfo minfo = (FilterPartInfo) info;
        setNoticeNumber(minfo.getNoticeNumber());
        setNoticeType(minfo.getNoticeType());
        setPartNumber(minfo.getPartNumber());
        setState(minfo.getState());
        setVersionValue(minfo.getVersionValue());
        setRoute(minfo.getRoute());
    }
}
