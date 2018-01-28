/**
 * ���ɳ���IntePackHomeDelegate.java	1.0              2007-9-25
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.jferp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoHomeDelegate;
import com.faw_qm.framework.service.BsoReference;

/**
 * <p>Title: ��</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: ������Ϣ�����ɷ����޹�˾</p>
 * @author ��ǿ
 * @version 1.0
 */
public class IntePackHomeDelegate implements BsoHomeDelegate
{
    /**
     * ���캯����
     */
    public IntePackHomeDelegate()
    {
    }

    private IntePackHome home = null;

    /**
     * ��ʼ����
     * @param obj Object
     */
    public void init(Object obj)
    {
        if(!(obj instanceof IntePackHome))
        {
            Object[] objs = {obj.getClass(), "JFIntePackHome"};
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (IntePackHome) obj;
    }

    /**
     * ���ڸ��������ָ�����
     * @param bsoID String
     * @return BsoReference
     * @throws FinderException
     */
    public BsoReference findByPrimaryKey(String bsoID) throws FinderException
    {
        return home.findByPrimaryKey(bsoID);
    }

    /**
     * ����ֵ������ҵ�����
     * @param info ֵ����
     * @return �����ɹ���ҵ�����
     * @throws CreateException
     */
    public BsoReference create(BaseValueIfc info) throws CreateException
    {
        return home.create(info);
    }

    /**
     * ����ֵ�����ָ��ʱ�������ҵ�����
     * @param info ֵ����
     * @param ct ����ʱ�����
     * @param mt ����ʱ�����
     * @return �����ɹ���ҵ�����
     * @throws CreateException
     */
    public BsoReference create(BaseValueIfc info, Timestamp ct, Timestamp mt)
            throws CreateException
    {
        return home.create(info, ct, mt);
    }
}
