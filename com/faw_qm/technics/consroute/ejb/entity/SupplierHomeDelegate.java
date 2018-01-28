/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.technics.consroute.ejb.entity;
 
import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoHomeDelegate;
import com.faw_qm.framework.service.BsoReference;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author not attributable
 * @version 1.0
 */

public class SupplierHomeDelegate implements BsoHomeDelegate
{

    private SupplierHome home = null;

    public void init(Object obj)
    {
        if (!(obj instanceof SupplierHome))
        {
            Object[] objs =
                    {
                    obj.getClass(), "SupplierServiceHome"};
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (SupplierHome) obj;
    }


    /**
     ���ڸ��������ָ�����
     */
    public BsoReference findByPrimaryKey(String bsoID)
            throws FinderException
    {
        return home.findByPrimaryKey(bsoID);
    }


    /**
       ����ֵ������ҵ�����
     */
    public BsoReference create(BaseValueIfc info)
            throws CreateException
    {
        return home.create(info);
    }


    /**
       ����ֵ�����ָ��ʱ�������ҵ�����
     */
    public BsoReference create(BaseValueIfc info, Timestamp ct, Timestamp mt)
            throws
            CreateException
    {
        return home.create(info, ct, mt);
    }

}
