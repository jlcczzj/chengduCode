/**
 * ���ɳ���IntePackHome.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.cderp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.faw_qm.enterprise.ejb.entity.ItemHome;
import com.faw_qm.framework.service.BaseValueIfc;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public interface IntePackHome extends ItemHome
{
    /**
     * ��������ļ��ɰ�ֵ���������ݿ��д������ɰ�����
     * @param basevalueifc �����ֵ����
     * @return IntePack
     * @throws CreateException
     */
    public abstract IntePack create(BaseValueIfc basevalueifc)
            throws CreateException;

    /**
     * ��������ļ��ɰ�ֵ�����ʱ��������ݿ��д������ɰ�����
     * @param basevalueifc ����ļ��ɰ�ֵ����
     * @param timestamp ����ʱ�����
     * @param timestamp1 ����ʱ�����
     * @return IntePack
     * @throws CreateException
     */
    public abstract IntePack create(BaseValueIfc basevalueifc,
            Timestamp timestamp, Timestamp timestamp1) throws CreateException;

    /**
     * �������ؼ��������ݿ��в��Ҽ��ɰ�����
     * @param s ���ؼ��֡�
     * @return IntePack
     * @throws FinderException
     */
    public abstract IntePack findByPrimaryKey(String s) throws FinderException;
}
