/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 * 创建时间 2003/09/09
 */


package com.faw_qm.technics.route.ejb.service;

import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.ServiceHandleSignalException;
import com.faw_qm.framework.service.Signal;
import com.faw_qm.framework.service.SignalListenerIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
//CCBegin by liunan 2011-05-16
import com.faw_qm.technics.route.model.TechnicsRouteListInfo;
import com.faw_qm.technics.route.exception.TechnicsRouteException;
//CCEnd by liunan 2011-05-16

/**
 * 须考虑是否有被使用不能被删除的情况。暂时未发现。
 * 1.deleteRouteListListener
 * 2．当PartMaster（产品或零件）删除时，需删除对应的路线表和对应的路线信息。
 */
public class RoutePreDeleteListener
    implements SignalListenerIfc {
  private static final boolean VERBOSE = (Boolean.valueOf(RemoteProperty.
      getProperty(
      "com.faw_qm.technics.route.verbose", "true"))).booleanValue();

  /**
   * @roseuid 403B03B801F5
   */
  public RoutePreDeleteListener() {
  }

  public void handleSignal(Signal signal, BaseServiceImp ejb) throws
      ServiceHandleSignalException {
    if (VERBOSE) {
      System.out.println("RoutePreDeleteListener.handleSignal - IN......: " +
                         "receive " + signal.getSignalName() +
                         " signal");
    }
    try {
      TechnicsRouteServiceEJB service = (TechnicsRouteServiceEJB) ejb;
      Object obj1 = signal.getSignalTarget();
      //考虑是否有被使用不能被删除的情况。暂时未发现。
      if (obj1 instanceof TechnicsRouteListIfc) {
        if (VERBOSE) {
          System.out.println( ( (TechnicsRouteListIfc) obj1).getBsoName() +
                             " BsoID = " +
                             ( (TechnicsRouteListIfc) obj1).getBsoID());
        }
        service.deleteRouteListListener( (TechnicsRouteListIfc) obj1);
      }
      if ( (obj1 instanceof QMPartMasterIfc)) {
        if (VERBOSE) {
          System.out.println( ( (QMPartMasterIfc) obj1).getBsoName() +
                             " BsoID = " + ( (QMPartMasterIfc) obj1).getBsoID());
        }
        //删除时，需删除对应的路线表和对应的路线信息。
        
        //CCBegin by liunan 2011-05-16 删除零部件时，如果零部件关联了艺准，则提示先删除艺准。
        //service.deletePartMasterListener( (QMPartMasterIfc) obj1);
        QMPartMasterIfc part = (QMPartMasterIfc) obj1;
        TechnicsRouteListInfo trlInfo = (TechnicsRouteListInfo)service.getRouteListByProduct(part.getBsoID());
        if(trlInfo!=null)
        {
        	throw new TechnicsRouteException("当前零部件（"+part.getPartNumber()+"）创建了艺准（"+trlInfo.getRouteListNumber()+"），请删除艺准后再删除该零部件。");
        }
        //CCEnd by liunan 2011-05-16
        //CCBegin by liunan 2011-09-20 删除零部件时，如果零部件关联了路线，则提示先删除关联。
        String temp = service.getPartRoutesNew(part.getBsoID());        
        if(!temp.equals("")){
        	throw new TechnicsRouteException("当前零部件（"+part.getPartNumber()+"）在艺准（"+temp+"）中编辑了路线，请先删除艺准与该零部件的关联。");
        	
          }
          //CCEnd by liunan 2011-09-20
      }
      if (VERBOSE) {
        System.out.println("RoutePreDeleteListener.handleSignal - OUT.......");
      }
    }
    catch (Exception ex) {
      throw new ServiceHandleSignalException(ex);
    }
  }
}
