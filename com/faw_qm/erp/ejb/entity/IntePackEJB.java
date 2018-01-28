/**
 * ���ɳ���IntePackEJB.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import com.faw_qm.enterprise.ejb.entity.ItemEJB;
import com.faw_qm.erp.model.IntePackInfo;
import com.faw_qm.erp.util.IntePackSourceType;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public abstract class IntePackEJB extends ItemEJB
{
    /**
     * ���캯����
     */
    public IntePackEJB()
    {
        super();
    }

    /**
     * ��ȡҵ��������
     * @return "QMPart".
     */
    public String getBsoName()
    {
        return "IntePack";
    }



    /**
     * setBsoID
     *
     * @param string String
     */
    public abstract void setBsoID(String string);

    /**
     * ���ü��ɰ�����ʱ�䡣
     * @param timestamp Timestamp
     */
    public abstract void setCreateTime(Timestamp timestamp);

    /**
     * ���ü��ɰ����ơ�
     * @param name String
     */
    public abstract void setName(String name);

    /**
     * ���ü��ɰ���Դ���͡�
     * @param name IntePackSourceType
     */
    public abstract void setSourceTypeStr(String sourceTypeStr);

    /**
     * ���ü��ɰ���Դ���͡�
     * @param name IntePackSourceType
     */
    public void setSourceType(IntePackSourceType sourceType)
    {
        setSourceTypeStr(sourceType.toString());
    }

    /**
     * ���ü��ɰ���Դ��
     * @param name String
     */
    public abstract void setSource(String source);

    /**
     * ���ü��ɰ�״̬��
     * @param name int
     */
    public abstract void setState(int state);

    /**
     * ���ü��ɰ������ߡ�
     * @param name String
     */
    public abstract void setCreator(String creator);

    /**
     * ���ü��ɰ������ߡ�
     * @param name String
     */
    public abstract void setPublisher(String publisher);

    /**
     * ���ü��ɰ�����ʱ�䡣
     * @param name String
     */
    public abstract void setPublishTime(Timestamp publishTime);

    /**
     * getBsoID
     *
     * @return String
     */
    public abstract String getBsoID();

    /**
     * getCreateTime
     *
     * @return Timestamp
     */
    public abstract Timestamp getCreateTime();

    /**
     * ��ü��ɰ����ơ�
     * @return String
     */
    public abstract String getName();

    /**
     * ��ü��ɰ���Դ���͡�
     * @return String
     */
    public abstract String getSourceTypeStr();

    /**
     * ��ü��ɰ���Դ���͡�
     * @return String
     */
    public IntePackSourceType getSourceType()
    {
        return IntePackSourceType.toIntePackSourceType(getSourceTypeStr());
    }

    /**
     * ��ü��ɰ���Դ��
     * @return String
     */
    public abstract String getSource();

    /**
     * ��ü��ɰ�״̬��
     * @return String
     */
    public abstract int getState();

    /**
     * ��ü��ɰ������ߡ�
     * @return String
     */
    public abstract String getCreator();

    /**
     * ��ü��ɰ������ߡ�
     * @return String
     */
    public abstract String getPublisher();

    /**
     * ��ü��ɰ�����ʱ�䡣
     * @return String
     */
    public abstract Timestamp getPublishTime();

    /**
     * ��ȡҵ������Ӧ��ֵ����
     * @return BaseValueIfc
     * @throws QMException
     */
    public BaseValueIfc getValueInfo() throws QMException
    {
        IntePackInfo info = new IntePackInfo();
        setValueInfo(info);
        return info;
    }

    /**
     * ����ҵ������Ӧ��ֵ����
     * @param info BasevalueIfc
     * @throws QMException
     */
    public void setValueInfo(BaseValueIfc info) throws QMException
    {
        super.setValueInfo(info);
        IntePackInfo inteInfo = (IntePackInfo) info;
        inteInfo.setCreator(this.getCreator());
        inteInfo.setName(this.getName());
        inteInfo.setPublisher(this.getPublisher());
        inteInfo.setPublishTime(this.getPublishTime());
        inteInfo.setSource(this.getSource());
        inteInfo.setSourceType(this.getSourceType());
        inteInfo.setState(this.getState());
    }

    /**
     * ����ֵ���󴴽�ҵ�����
     * @param info BaseValueIfc
     * @throws CreateException
     */
    public void createByValueInfo(BaseValueIfc info) throws CreateException
    {
        super.createByValueInfo(info);
        IntePackInfo inteInfo = (IntePackInfo) info;
        //��Ҫ��������У�顣
        try
        {
            inteInfo.attrValidate();
        }
        catch (QMException ex)
        {
            ex.printStackTrace();
            throw new CreateException(ex.getClientMessage());
        }
        this.setCreator(inteInfo.getCreator());
        this.setName(inteInfo.getName());
        this.setPublisher(inteInfo.getPublisher());
        this.setPublishTime(inteInfo.getPublishTime());
        this.setSource(inteInfo.getSource());
        IntePackSourceType sourceTypeStr=inteInfo.getSourceType();
        if(sourceTypeStr!=null){
        	 this.setSourceType(inteInfo.getSourceType());
        }
        this.setState(inteInfo.getState());
    }

    /**
     * ����ֵ�������ҵ�����
     * @param info BaseValueIfc
     * @throws QMException
     */
    public void updateByValueInfo(BaseValueIfc info) throws QMException
    {
        super.updateByValueInfo(info);
        IntePackInfo inteInfo = (IntePackInfo) info;
        //��Ҫ��������У�顣
        inteInfo.attrValidate();
        this.setCreator(inteInfo.getCreator());
        this.setName(inteInfo.getName());
        this.setPublisher(inteInfo.getPublisher());
        this.setPublishTime(inteInfo.getPublishTime());
        this.setSource(inteInfo.getSource());
        this.setSourceType(inteInfo.getSourceType());
        this.setState(inteInfo.getState());
    }
}
