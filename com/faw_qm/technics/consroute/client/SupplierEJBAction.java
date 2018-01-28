/** ���ɳ���QMRouteEJBAction.java	2007/05/29
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.client;
 
import com.faw_qm.technics.consroute.ejb.service.SupplierService;
import com.faw_qm.technics.consroute.model.SupplierInfo;
import com.faw_qm.technics.consroute.util.SupplierFormData;
import com.faw_qm.framework.controller.ejb.action.EJBActionSupport;
import com.faw_qm.framework.event.EventException;
import com.faw_qm.framework.event.EventResponse;
import com.faw_qm.framework.event.Event;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.util.EJBServiceHelper;



/**
 * ����·����EJBAction��ͨ�������յ���ҵ������� 
 * @version  1.0
 * @author ������
 */
public class SupplierEJBAction
    extends EJBActionSupport {
  // �Ƿ������ϸ��Ϣ
  boolean VERBOSE = true;
  static final long serialVersionUID = 1L;
  public SupplierEJBAction() { 
  }

  /**
   * @param e - ִ������  
   * @return com.faw_qm.framework.event.EventResponse
   */
  public EventResponse perform(Event e) throws EventException {
    if (VERBOSE) {
      System.out.println("����" + this.getClass().getName() + "\n" +
                         "����: public EventResponse perform(Event e)" + "\n" +
                         "����: e " + e + "\n" +
                         "����: ִ��supplierEJBAction��ʼ");
    }

    SupplierEvent de = (SupplierEvent) e;
    
    try {
      //  ��ó־û�����


      if (de.getActionType() == SupplierEvent.ADD) {
        //  ���QMRouteFormData����
    	  SupplierFormData formData = de.getsupplierFormData();
        //  ����㲿������
    	  SupplierService service = (SupplierService) EJBServiceHelper.
            getService("SupplierService");
    	  
        // ͨ��������ô���·�߷���
    	
    	SupplierInfo aimInfo =  service.createsupplier(formData);
    	
      
        //�����¼���Ӧ
        SupplierEventResponse eventResponse = new SupplierEventResponse("������Ӧ����Ϣ");
        eventResponse.setOperationSupperBsoID(aimInfo.getBsoID());
        return eventResponse;
      }

      if (de.getActionType() == SupplierEvent.UPDATE) {
        //  ���QMRouteFormData����
        SupplierFormData formData = de.getsupplierFormData();
        //  ����㲿������
        SupplierService service = (SupplierService) EJBServiceHelper.
        getService("SupplierService");
        // ͨ��������ô���·�߷���
        SupplierInfo aimInfo =  service.updateSupplier(formData);
      
        //�����¼���Ӧ
        SupplierEventResponse eventResponse = new SupplierEventResponse("���¹�Ӧ����Ϣ");
        eventResponse.setOperationSupperBsoID(aimInfo.getBsoID());
        return eventResponse;

      }

      if (de.getActionType() == SupplierEvent.DELETE) {

        //  ���DocFormData����
        SupplierFormData formData = de.getsupplierFormData();

        //  ����ĵ�����
        SupplierService service = (SupplierService) EJBServiceHelper.
        getService("SupplierService");

        //  ͨ���������ɾ���ĵ�����
          service.deleteSupplier((String)formData.getAttributes("bsoID"));
        return null;
      }

      if (VERBOSE) {
        System.out.println("����" + this.getClass().getName() + "\n" +
                           "����: public EventResponse perform(Event e)" + "\n" +
                           "����: ִ��supplierEJBAction������");
      }
    }
    catch (Exception ue) {
      ue.printStackTrace();
      if (VERBOSE) { 
        System.out.println("����" + this.getClass().getName() + "\n" +
                           "����: public EventResponse perform(Event event)" +
                           "\n" +
                           "����: ִ��supplierEJBAction�쳣��");
      }
      Object[] objs = {
          de.getEventName()};
      throw new EventException(ue, "22", objs);
    }

    return null;
  }

}
