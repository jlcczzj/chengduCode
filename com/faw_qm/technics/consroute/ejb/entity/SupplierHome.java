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
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceHome;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author not attributable
 * @version 1.0
 */

public interface SupplierHome extends BsoReferenceHome
{
    /**
        ���ڸ��������ָ�����
     */
    public Supplier findByPrimaryKey(String bsoID)
            throws 
            FinderException;


    /**
     ����ֵ������ҵ�����
     */
    public Supplier create(BaseValueIfc info)
            throws CreateException;


    /**
     ����ֵ�����ָ��ʱ�������ҵ�����
     */
    public Supplier create(BaseValueIfc info, Timestamp ct,
                                     Timestamp mt)
            throws CreateException;

}
