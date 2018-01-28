/** ���ɳ���QMRouteEvent.java	2007/05/29
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.client;
 
import com.faw_qm.technics.consroute.util.SupplierFormData;
import com.faw_qm.framework.event.EventSupport;

/**
 * ��Ӧ�����¼��Ļ��࣬ͨ���¼���HTMLAction����EJBAction
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: ������˾</p>
 * @author ������
 * @version 1.0
 */
public class SupplierEvent
    extends EventSupport {
  // ��������������
  public static int ADD = 1;

  // �������¶�����
  public static int UPDATE = 2;

  // ����ɾ��������
  public static int DELETE = 3;

  // �������͡�
  protected int actionType = -1;
  protected SupplierFormData formData;

  public SupplierEvent() {

  }

  /**
   * ���췽��
   * @param actionType ��������
   * @param formData ���ݷ�װ��
   */
  public SupplierEvent(int actionType, SupplierFormData formData) {
    this.actionType = actionType;
    this.formData = formData;
  }

  /**
   * ��ö�������
   */
  public int getActionType() {
    return actionType;
  }

  /**
   * ������ݷ�װ��
   */
  public SupplierFormData getsupplierFormData() {
    return formData;
  }

  /**
   * ��ȡ�¼����ơ�
   * @return java.lang.String
   */
  public String getEventName() {
    return "com.faw_qm.technics.consroute.client.SupplierEJBAction";
  }

}
