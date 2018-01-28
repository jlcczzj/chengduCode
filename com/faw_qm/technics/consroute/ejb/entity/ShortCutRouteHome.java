package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;

import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceHome;

public interface ShortCutRouteHome extends BsoReferenceHome
{
	 /**
     * ���ڸ��������ָ�����
     */
    public ShortCutRoute findByPrimaryKey(String bsoID) throws FinderException;

    /**
     * ����ֵ������ҵ�����
     */
    public ShortCutRoute create(BaseValueIfc info) throws CreateException;

    /**
     * ����ֵ�����ָ��ʱ�������ҵ�����
     */
    public ShortCutRoute create(BaseValueIfc info, Timestamp ct, Timestamp mt) throws CreateException;
}
