
package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.CreateException;
import javax.ejb.FinderException;

import com.faw_qm.framework.exceptions.QMRuntimeException;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BsoHomeDelegate;
import com.faw_qm.framework.service.BsoReference;



/**
 *
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class RoutelistCompletHomeDelegate
    implements BsoHomeDelegate
{
    private RoutelistCompletHome home = null;
    public void init(Object obj)
    {
        if(!(obj instanceof RoutelistCompletHome))
        {
            Object[] objs =
                {
                obj.getClass(), "TechnicsRouteListHome"};
            throw new QMRuntimeException("com.faw_qm.framework.util.FrameworkResource", "70", objs);
        }
        home = (RoutelistCompletHome)obj;
    }

    /**
     ���ڸ��������ָ�����
     */
    public BsoReference findByPrimaryKey(String bsoID) throws FinderException
    {
        return home.findByPrimaryKey(bsoID);
    }

    /**
       ����ֵ������ҵ�����
     */
    public BsoReference create(BaseValueIfc info) throws CreateException
    {
        return home.create(info);
    }

    /**
       ����ֵ�����ָ��ʱ�������ҵ�����
     */
    public BsoReference create(BaseValueIfc info, Timestamp ct, Timestamp mt) throws CreateException
    {
        return home.create(info, ct, mt);
    }

}
