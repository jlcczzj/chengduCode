package com.faw_qm.gybomNotice.ejb.entity;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import java.sql.Timestamp;
import com.faw_qm.enterprise.ejb.entity.ManagedHome;
import com.faw_qm.framework.service.BaseValueIfc;


/**
 * 发布单零件请求抽象接口的Home 接口
 * 
 * 
 */
public interface GYBomAdoptNoticePartLinkHome extends ManagedHome{
	
	
	/**
	 * 用于根据主键恢复对象
	 */
	public GYBomAdoptNoticePartLink findByPrimaryKey(String bsoID)
			throws FinderException;

	/**
	 * 根据值对象建立业务对象
	 */
	public GYBomAdoptNoticePartLink create(BaseValueIfc info)
			throws CreateException;

	/**
	 * 根据值对象和指定时间戳建立业务对象
	 */
	public GYBomAdoptNoticePartLink create(BaseValueIfc info, Timestamp ct,
			Timestamp mt) throws CreateException;

}
