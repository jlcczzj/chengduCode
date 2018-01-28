/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */

/**
 *CR1 2011/4/21  ������   �μ���������:product;TD��2386
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
 * �뿼���Ƿ��б�ʹ�ò��ܱ�ɾ�����������ʱδ���֡� 1.deleteRouteListListener 2����PartMaster����Ʒ�������ɾ��ʱ����ɾ����Ӧ��·�߱�Ͷ�Ӧ��·����Ϣ��
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
            //�����Ƿ��б�ʹ�ò��ܱ�ɾ�����������ʱδ���֡�
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
                //ɾ��ʱ����ɾ����Ӧ��·�߱�Ͷ�Ӧ��·����Ϣ��
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
