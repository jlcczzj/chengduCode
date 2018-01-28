/** ����RouteEventResponse.java	1.0  2007.05.29
  * ��Ȩ��һ��������˾����
  * ��������һ��������˾��˽�л�Ҫ����
  * δ������˾��Ȩ�����÷Ƿ������͵���
  * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
  * ��������Ȩ��
  */
package com.faw_qm.technics.consroute.client;
 
import com.faw_qm.framework.event.EventResponseSupport;

/**
 *
 * <p>Title: RouteEventResponse</p>
 * <p>Description:������Ӧ�̵��¼���Ӧ </p>
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: ������˾</p>
 * @author ������
 * @version 1.0
 */
public class SupplierEventResponse extends EventResponseSupport
{

   private Object object;              //���صĶ���
   private String eventName;           //�¼�������
   private String supperBosID;

   /**
    * ���촴��·���¼���Ӧ����
    * @param obj ���صĶ���
    * @param enventName �¼�������
    * */
   public SupplierEventResponse(Object obj)
   {
     super(obj);
     object = obj;
     eventName = "QMRouteEvent";
   }

   /**
    * ���촴��·���¼���Ӧ����
    * @param obj ���صĶ���
    * @param enventName �¼�������
    * */
   public SupplierEventResponse(String envetName, Object obj)
   {
     super(obj);
     object = obj;
     this.eventName = eventName;
   }



   /**
    * ��ȡ�¼���Ӧ���ơ�
    * @return java.lang.String
    */
   public String getEventName()
   {
    return eventName;
   }
   

   /**
    * ���ò����㲿����bsoID��
    * @param streamIDHashMap
    */
   public void setOperationSupperBsoID(String supperBosID)
   {
    this.supperBosID = supperBosID;
   }

   /**
    * ��ȡ�����㲿����bsoID��
    * @param streamIDHashMap
    */
   public String getOperationSupperBsoID()
   {
    return supperBosID;
   }
   

}
