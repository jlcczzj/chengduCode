/** 生成程序QMRouteEJBAction.java	2007/05/29
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
 * 工艺路线中EJBAction，通过它最终调用业务服务处理 
 * @version  1.0
 * @author 唐树涛
 */
public class SupplierEJBAction
    extends EJBActionSupport {
  // 是否输出详细信息
  boolean VERBOSE = true;
  static final long serialVersionUID = 1L;
  public SupplierEJBAction() { 
  }

  /**
   * @param e - 执行请求。  
   * @return com.faw_qm.framework.event.EventResponse
   */
  public EventResponse perform(Event e) throws EventException {
    if (VERBOSE) {
      System.out.println("类名" + this.getClass().getName() + "\n" +
                         "方法: public EventResponse perform(Event e)" + "\n" +
                         "参数: e " + e + "\n" +
                         "描述: 执行supplierEJBAction开始");
    }

    SupplierEvent de = (SupplierEvent) e;
    
    try {
      //  获得持久化服务


      if (de.getActionType() == SupplierEvent.ADD) {
        //  获得QMRouteFormData对象
    	  SupplierFormData formData = de.getsupplierFormData();
        //  获得零部件服务
    	  SupplierService service = (SupplierService) EJBServiceHelper.
            getService("SupplierService");
    	  
        // 通过服务调用创建路线方法
    	
    	SupplierInfo aimInfo =  service.createsupplier(formData);
    	
      
        //创建事件响应
        SupplierEventResponse eventResponse = new SupplierEventResponse("创建供应商信息");
        eventResponse.setOperationSupperBsoID(aimInfo.getBsoID());
        return eventResponse;
      }

      if (de.getActionType() == SupplierEvent.UPDATE) {
        //  获得QMRouteFormData对象
        SupplierFormData formData = de.getsupplierFormData();
        //  获得零部件服务
        SupplierService service = (SupplierService) EJBServiceHelper.
        getService("SupplierService");
        // 通过服务调用创建路线方法
        SupplierInfo aimInfo =  service.updateSupplier(formData);
      
        //创建事件响应
        SupplierEventResponse eventResponse = new SupplierEventResponse("更新供应商信息");
        eventResponse.setOperationSupperBsoID(aimInfo.getBsoID());
        return eventResponse;

      }

      if (de.getActionType() == SupplierEvent.DELETE) {

        //  获得DocFormData对象
        SupplierFormData formData = de.getsupplierFormData();

        //  获得文档服务
        SupplierService service = (SupplierService) EJBServiceHelper.
        getService("SupplierService");

        //  通过服务调用删除文档方法
          service.deleteSupplier((String)formData.getAttributes("bsoID"));
        return null;
      }

      if (VERBOSE) {
        System.out.println("类名" + this.getClass().getName() + "\n" +
                           "方法: public EventResponse perform(Event e)" + "\n" +
                           "描述: 执行supplierEJBAction结束。");
      }
    }
    catch (Exception ue) {
      ue.printStackTrace();
      if (VERBOSE) { 
        System.out.println("类名" + this.getClass().getName() + "\n" +
                           "方法: public EventResponse perform(Event event)" +
                           "\n" +
                           "描述: 执行supplierEJBAction异常！");
      }
      Object[] objs = {
          de.getEventName()};
      throw new EventException(ue, "22", objs);
    }

    return null;
  }

}
