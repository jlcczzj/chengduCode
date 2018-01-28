/**
 * ���ɳ���IntePack.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.ejb.entity;

import java.sql.Timestamp;

import com.faw_qm.cderp.util.IntePackSourceType;
import com.faw_qm.enterprise.ejb.entity.Item;

/**
 * <p>Title: ���ɰ��Ľӿڡ�</p>
 * <p>Description:���ɰ��Ľӿڡ�</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public interface IntePack extends Item
{
    /**
     * ���ü��ɰ����ơ�
     * @param name String
     */
    public void setName(String name);

    /**
     * ���ü��ɰ���Դ���͡�
     * @param name IntePackSourceType
     */
    public void setSourceType(IntePackSourceType sourceType);

    /**
     * ���ü��ɰ���Դ���͡�
     * @param name IntePackSourceType
     */
    public void setSourceTypeStr(String sourceTypeStr);

    /**
     * ���ü��ɰ���Դ��
     * @param name String
     */
    public void setSource(String source);

    /**
     * ���ü��ɰ�״̬��
     * @param name int
     */
    public void setState(int state);

    /**
     * ���ü��ɰ������ߡ�
     * @param name String
     */
    public void setCreator(String creator);

    /**
     * ���ü��ɰ������ߡ�
     * @param name String
     */
    public void setPublisher(String publisher);

    /**
     * ���ü��ɰ�����ʱ�䡣
     * @param name String
     */
    public void setPublishTime(Timestamp publishTime);

    /**
     * ��ü��ɰ����ơ�
     * @return String
     */
    public String getName();

    /**
     * ��ü��ɰ���Դ���͡�
     * @return String
     */
    public IntePackSourceType getSourceType();

    /**
     * ��ü��ɰ���Դ���͡�
     * @return String
     */
    public String getSourceTypeStr();

    /**
     * ��ü��ɰ���Դ��
     * @return String
     */
    public String getSource();

    /**
     * ��ü��ɰ�״̬��
     * @return String
     */
    public int getState();

    /**
     * ��ü��ɰ������ߡ�
     * @return String
     */
    public String getCreator();

    /**
     * ��ü��ɰ������ߡ�
     * @return String
     */
    public String getPublisher();

    /**
     * ��ü��ɰ�����ʱ�䡣
     * @return String
     */
    public Timestamp getPublishTime();
}
