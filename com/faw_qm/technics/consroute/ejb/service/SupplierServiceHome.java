/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ��: Tue Dec 09 12:06:09 CST 2006
 */

package com.faw_qm.technics.consroute.ejb.service;
 
import javax.ejb.CreateException;

import com.faw_qm.framework.service.BaseServiceHome;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author �� ��
 * @version 1.0
 */

public interface SupplierServiceHome extends BaseServiceHome
{

    /**
     * ��������ӿ�
     * @return RationService ����ӿ�ʵ��
     */
    public SupplierService create()
            throws CreateException;


}
