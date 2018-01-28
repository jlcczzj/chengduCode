package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceHome;

public interface ShortCutRouteHome extends BsoReferenceHome
{
	 /**
     * 用于根据主键恢复对象
     */
    public ShortCutRoute findByPrimaryKey(String bsoID) throws FinderException;

    /**
     * 根据值对象建立业务对象
     */
    public ShortCutRoute create(BaseValueIfc info) throws CreateException;

    /**
     * 根据值对象和指定时间戳建立业务对象
     */
    public ShortCutRoute create(BaseValueIfc info, Timestamp ct, Timestamp mt) throws CreateException;
}
