/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */

package com.faw_qm.technics.consroute.ejb.service;

import com.faw_qm.framework.remote.RemoteProperty;

import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.Signal;
import com.faw_qm.framework.service.SignalListenerIfc;

public class RouteNewVersionListener implements SignalListenerIfc
{
    private static final boolean VERBOSE = (Boolean.valueOf(RemoteProperty.getProperty("com.faw_qm.technics.consroute.verbose", "true"))).booleanValue();

    /**
     * @roseuid 403B03B801F5
     */
    public RouteNewVersionListener()
    {}

    public void handleSignal(Signal signal, BaseServiceImp ejb)
    {
        return;
        /*
         * if(VERBOSE) { System.out.println("RouteNewVersionListener.handleSignal - IN: "+"--------receive "+signal.getSignalName()+ " signal---------"); } try { TechnicsRouteServiceEJB service =
         * (TechnicsRouteServiceEJB)ejb; //obj1--�µĴ�汾�� Object obj1 = signal.getSignalTarget(); if(obj1 instanceof TechnicsRouteListIfc) { if(VERBOSE)
         * System.out.println("��ʼ����newVersionListener......"); service.newVersionListener((TechnicsRouteListIfc)obj1); } if(obj1 instanceof TechnicsRouteList) { if(VERBOSE)
         * System.out.println("��ʼ����newVersionListener......"); TechnicsRouteListIfc listInfo = (TechnicsRouteListIfc) ((TechnicsRouteList)obj1).getValueInfo(); service.newVersionListener(listInfo); }
         * if(VERBOSE) { System.out.println("RouteNewVersionListener.handleSignal - OUT:  no exception"); } } catch(Exception ex) { throw new ServiceHandleSignalException(ex); }
         */
    }

    
    public void handleSignal(Signal arg0)
    {

    }
}
