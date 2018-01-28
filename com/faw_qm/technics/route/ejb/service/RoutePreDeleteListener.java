/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
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
 * �뿼���Ƿ��б�ʹ�ò��ܱ�ɾ�����������ʱδ���֡�
 * 1.deleteRouteListListener
 * 2����PartMaster����Ʒ�������ɾ��ʱ����ɾ����Ӧ��·�߱�Ͷ�Ӧ��·����Ϣ��
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
      //�����Ƿ��б�ʹ�ò��ܱ�ɾ�����������ʱδ���֡�
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
        //ɾ��ʱ����ɾ����Ӧ��·�߱�Ͷ�Ӧ��·����Ϣ��
        
        //CCBegin by liunan 2011-05-16 ɾ���㲿��ʱ������㲿����������׼������ʾ��ɾ����׼��
        //service.deletePartMasterListener( (QMPartMasterIfc) obj1);
        QMPartMasterIfc part = (QMPartMasterIfc) obj1;
        TechnicsRouteListInfo trlInfo = (TechnicsRouteListInfo)service.getRouteListByProduct(part.getBsoID());
        if(trlInfo!=null)
        {
        	throw new TechnicsRouteException("��ǰ�㲿����"+part.getPartNumber()+"����������׼��"+trlInfo.getRouteListNumber()+"������ɾ����׼����ɾ�����㲿����");
        }
        //CCEnd by liunan 2011-05-16
        //CCBegin by liunan 2011-09-20 ɾ���㲿��ʱ������㲿��������·�ߣ�����ʾ��ɾ��������
        String temp = service.getPartRoutesNew(part.getBsoID());        
        if(!temp.equals("")){
        	throw new TechnicsRouteException("��ǰ�㲿����"+part.getPartNumber()+"������׼��"+temp+"���б༭��·�ߣ�����ɾ����׼����㲿���Ĺ�����");
        	
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
