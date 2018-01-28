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
public interface GYBomAdoptNoticeHome extends ManagedHome{
	
	
	/**
	 * ���ڸ��������ָ�����
	 */
	public GYBomAdoptNotice findByPrimaryKey(String bsoID)
			throws FinderException;

	/**
	 * ����ֵ������ҵ�����
	 */
	public GYBomAdoptNotice create(BaseValueIfc info)
			throws CreateException;

	/**
	 * ����ֵ�����ָ��ʱ�������ҵ�����
	 */
	public GYBomAdoptNotice create(BaseValueIfc info, Timestamp ct,
			Timestamp mt) throws CreateException;

}
