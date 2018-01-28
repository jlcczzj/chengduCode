/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */


package com.faw_qm.technics.consroute.ejb.service;

import java.util.Vector;

import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.ServiceHandleSignalException;
import com.faw_qm.framework.service.Signal;
import com.faw_qm.framework.service.SignalListenerIfc;
import com.faw_qm.technics.consroute.model.TechnicsRouteListIfc;
import com.faw_qm.wip.model.WorkableIfc;

/**
 * ������·�߱����ʱ��ListRoutePartLink��setRouteListIterationID�����á�
 */
public class RoutePostCheckInListener
    implements SignalListenerIfc {
  private static final boolean VERBOSE = (Boolean.valueOf(RemoteProperty.
      getProperty(
      "com.faw_qm.technics.route.verbose", "true"))).booleanValue();

  /**
   * @roseuid 403B069501C7
   */
  public RoutePostCheckInListener() {

  }

  public void handleSignal(Signal signal, BaseServiceImp ejb) throws
      ServiceHandleSignalException {
    if (VERBOSE) {
      System.out.println("RoutePostCheckInListener.handleSignal - IN: " +
                         "--------receive " + signal.getSignalName() +
                         " signal---------");
    }
    try {
      TechnicsRouteServiceEJB service = (TechnicsRouteServiceEJB) ejb;
      Vector vec = (Vector) signal.getSignalTarget();
      //ԭ��
      WorkableIfc workableinfo = (WorkableIfc) vec.elementAt(0);
      //�����汾��
      WorkableIfc workableinfo1 = (WorkableIfc) vec.elementAt(1);

      if (workableinfo1 instanceof TechnicsRouteListIfc) {
        service.checkinListener( (TechnicsRouteListIfc) workableinfo1);
      }
      if (VERBOSE) {
        System.out.println(
            "RoutePostCheckInListener.handleSignal - OUT:  no exception");
      }
    }
    catch (Exception ex) {
      throw new ServiceHandleSignalException(ex);
    }
  }
}
