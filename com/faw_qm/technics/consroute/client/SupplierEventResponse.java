/** 程序RouteEventResponse.java	1.0  2007.05.29
  * 版权归一汽启明公司所有
  * 本程序属一汽启明公司的私有机要资料
  * 未经本公司授权，不得非法传播和盗用
  * 可在本公司授权范围内，使用本程序
  * 保留所有权利
  */
package com.faw_qm.technics.consroute.client;
 
import com.faw_qm.framework.event.EventResponseSupport;

/**
 *
 * <p>Title: RouteEventResponse</p>
 * <p>Description:创建供应商的事件响应 </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 启明公司</p>
 * @author 刘家坤
 * @version 1.0
 */
public class SupplierEventResponse extends EventResponseSupport
{

   private Object object;              //返回的对象
   private String eventName;           //事件的名字
   private String supperBosID;

   /**
    * 构造创建路线事件响应对象
    * @param obj 返回的对象
    * @param enventName 事件的名字
    * */
   public SupplierEventResponse(Object obj)
   {
     super(obj);
     object = obj;
     eventName = "QMRouteEvent";
   }

   /**
    * 构造创建路线事件响应对象
    * @param obj 返回的对象
    * @param enventName 事件的名字
    * */
   public SupplierEventResponse(String envetName, Object obj)
   {
     super(obj);
     object = obj;
     this.eventName = eventName;
   }



   /**
    * 获取事件相应名称。
    * @return java.lang.String
    */
   public String getEventName()
   {
    return eventName;
   }
   

   /**
    * 设置操作零部件的bsoID。
    * @param streamIDHashMap
    */
   public void setOperationSupperBsoID(String supperBosID)
   {
    this.supperBosID = supperBosID;
   }

   /**
    * 获取操作零部件的bsoID。
    * @param streamIDHashMap
    */
   public String getOperationSupperBsoID()
   {
    return supperBosID;
   }
   

}
