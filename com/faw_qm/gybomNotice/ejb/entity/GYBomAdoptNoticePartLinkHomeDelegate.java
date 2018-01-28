package com.faw_qm.gybomNotice.ejb.entity;


import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.FinderException;
import com.faw_qm.framework.exceptions.QMRuntimeException;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoHomeDelegate;
import com.faw_qm.framework.service.BsoReference;

/**
 
 * <p>Title:发布单Home方法代理实现类 </p>
 * <p>Description: 变更请求Home方法代理实现类</p>
 * @version 1.0
 */
public class GYBomAdoptNoticePartLinkHomeDelegate implements BsoHomeDelegate{
	
	
	private GYBomAdoptNoticePartLinkHome home = null;

	 /**
     * 根据值对象建立业务对象
     * @param info 值对象
     * @return  创建的业务对象
     * @throws CreateException
     */
	public BsoReference create(BaseValueIfc info) throws CreateException 
	{
		
		 return home.create(info);
	}

	 /**
     * 根据值对象和指定时间戳建立业务对象
     * @param info 值对象
     * @param ct 创建时间戳
     * @param mt 修改时间戳
     * @return 创建的业务对象
     * @throws CreateException
     */
	public BsoReference create(BaseValueIfc info, Timestamp ct, Timestamp mt)
			throws CreateException 
	{
		 return home.create(info, ct, mt);
	}
	
	 /**
     * 用于根据主键恢复对象
     * @param bsoID 业务对象或值对象的主键
     * @return 恢复的业务对象
     * @throws FinderException
     */

	public BsoReference findByPrimaryKey(String bsoID) throws FinderException 
	{
		 return home.findByPrimaryKey(bsoID);
	}

	/**
     * 实现HomeDelegate接口方法。用一个对象初始化Home接口
     * @param obj 初始化对象
     */
	public void init(Object obj)  
	{
        if (!(obj instanceof GYBomAdoptNoticePartLinkHome))
        {
            Object[] objs ={obj.getClass(), "GYBomAdoptNoticePartLinkHome"};
            throw new QMRuntimeException(
                    "com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (GYBomAdoptNoticePartLinkHome) obj;
		
	}

}
