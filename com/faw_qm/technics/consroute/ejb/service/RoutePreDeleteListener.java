/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */

/**
 *CR1 2011/4/21  郭晓亮   参见：测试域:product;TD号2386
 *
 */

package com.faw_qm.technics.consroute.ejb.service;

import java.util.Collection;

import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.ServiceHandleSignalException;
import com.faw_qm.framework.service.Signal;
import com.faw_qm.framework.service.SignalListenerIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;

/**
 * 须考虑是否有被使用不能被删除的情况。暂时未发现。 1.deleteRouteListListener 2．当PartMaster（产品或零件）删除时，需删除对应的路线表和对应的路线信息。
 */
public class RoutePreDeleteListener implements SignalListenerIfc
{
    private static final boolean VERBOSE = (Boolean.valueOf(RemoteProperty.getProperty("com.faw_qm.technics.consroute.verbose", "true"))).booleanValue();

    /**
     * @roseuid 403B03B801F5
     */
    public RoutePreDeleteListener()
    {}

    public void handleSignal(Signal signal, BaseServiceImp ejb) throws ServiceHandleSignalException
    {
        if(VERBOSE)
        {
            System.out.println("RoutePreDeleteListener.handleSignal - IN......: " + "receive " + signal.getSignalName() + " signal");
        }
        try
        {
            TechnicsRouteServiceEJB service = (TechnicsRouteServiceEJB)ejb;
            Object obj1 = signal.getSignalTarget();
            //考虑是否有被使用不能被删除的情况。暂时未发现。
            if(obj1 instanceof TechnicsRouteListIfc)
            {
                if(VERBOSE)
                {
                    System.out.println(((TechnicsRouteListIfc)obj1).getBsoName() + " BsoID = " + ((TechnicsRouteListIfc)obj1).getBsoID());
                }
                service.deleteRouteListListener((TechnicsRouteListIfc)obj1);
            }
            if((obj1 instanceof QMPartMasterIfc))
            {
                if(VERBOSE)
                {
                    System.out.println(((QMPartMasterIfc)obj1).getBsoName() + " BsoID = " + ((QMPartMasterIfc)obj1).getBsoID());
                }

                //CR1 begin
                //删除时，需删除对应的路线表和对应的路线信息。
                //service.deletePartMasterListener( (QMPartMasterIfc) obj1);

                Collection col = service.getPartRoutes(((QMPartMasterIfc)obj1).getBsoID());

                if(col.size() != 0)
                {
                    throw new ServiceHandleSignalException("12", null);

                }
                //CR1 end
            }
            if(VERBOSE)
            {
                System.out.println("RoutePreDeleteListener.handleSignal - OUT.......");
            }
        }catch(Exception ex)
        {
            throw new ServiceHandleSignalException(ex);
        }
    }

   
    public void handleSignal(Signal arg0)
    {
   

    }
}
