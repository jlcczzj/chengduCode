

package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoReferenceHome;

/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public interface RoutelistCompletHome
    extends BsoReferenceHome
{
    /**
     ���ڸ��������ָ�����
     */
    public RoutelistComplet findByPrimaryKey(String bsoID) throws FinderException;

    /**
     ����ֵ������ҵ�����
     */
    public RoutelistComplet create(BaseValueIfc info) throws CreateException;

    /**
     ����ֵ�����ָ��ʱ�������ҵ�����
     */
    public RoutelistComplet create(BaseValueIfc info, Timestamp ct, Timestamp mt) throws CreateException;
}
