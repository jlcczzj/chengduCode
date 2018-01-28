/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
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
 * 负责工艺路线表检入时，ListRoutePartLink的setRouteListIterationID的设置。
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
      //原本
      WorkableIfc workableinfo = (WorkableIfc) vec.elementAt(0);
      //升级版本。
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
