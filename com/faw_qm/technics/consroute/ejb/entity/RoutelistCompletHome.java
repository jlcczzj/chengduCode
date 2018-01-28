

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
     用于根据主键恢复对象
     */
    public RoutelistComplet findByPrimaryKey(String bsoID) throws FinderException;

    /**
     根据值对象建立业务对象
     */
    public RoutelistComplet create(BaseValueIfc info) throws CreateException;

    /**
     根据值对象和指定时间戳建立业务对象
     */
    public RoutelistComplet create(BaseValueIfc info, Timestamp ct, Timestamp mt) throws CreateException;
}
