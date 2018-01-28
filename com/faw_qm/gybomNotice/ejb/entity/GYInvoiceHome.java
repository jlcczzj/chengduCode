package com.faw_qm.gybomNotice.ejb.entity;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.sql.Timestamp;
import com.faw_qm.enterprise.ejb.entity.ManagedHome;
import com.faw_qm.framework.service.BaseValueIfc;


/**
 * ����������ӿڵ�Home �ӿ�
 * 
 * 
 */
public interface GYInvoiceHome extends ManagedHome{
	
	
	/**
	 * ���ڸ��������ָ�����
	 */
	public GYInvoice findByPrimaryKey(String bsoID)
			throws FinderException;

	/**
	 * ����ֵ������ҵ�����
	 */
	public GYInvoice create(BaseValueIfc info)
			throws CreateException;

	/**
	 * ����ֵ�����ָ��ʱ�������ҵ�����
	 */
	public GYInvoice create(BaseValueIfc info, Timestamp ct,
			Timestamp mt) throws CreateException;

}
