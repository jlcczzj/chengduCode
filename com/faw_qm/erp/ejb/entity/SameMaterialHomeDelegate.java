/**
 * ���ɳ���PromulgateNotifyHTMLAction.java   1.0              2006-11-6
 * ��Ȩ��������Ϣ�����ɷ����޹�˾����
 * ��������������Ϣ�����ɷ����޹�˾��˽�л�Ҫ����
 * δ������˾��Ȩ���÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ��ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.erp.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoHomeDelegate;
import com.faw_qm.framework.service.BsoReference;


public class SameMaterialHomeDelegate implements BsoHomeDelegate
{
    private SameMaterialHome home = null;

    public void init(Object obj)
    {
        if(!(obj instanceof SameMaterialHome))
        {
            Object[] objs = {obj.getClass(), "SameMaterialHome"};
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (SameMaterialHome) obj;
    }

    /**
     ���ڸ��������ָ�����
     */
    public BsoReference findByPrimaryKey(String bsoID) throws FinderException
    {
        return home.findByPrimaryKey(bsoID);
    }

    /**
     ����ֵ������ҵ�����
     */
    public BsoReference create(BaseValueIfc info) throws CreateException
    {
        return home.create(info);
    }

    /**
     ����ֵ�����ָ��ʱ�������ҵ�����
     */
    public BsoReference create(BaseValueIfc info, Timestamp ct, Timestamp mt)
            throws CreateException
    {
        return home.create(info, ct, mt);
    }
}