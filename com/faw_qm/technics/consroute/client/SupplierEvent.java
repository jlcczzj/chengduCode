/** 生成程序QMRouteEvent.java	2007/05/29
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */
package com.faw_qm.technics.consroute.client;
 
import com.faw_qm.technics.consroute.util.SupplierFormData;
import com.faw_qm.framework.event.EventSupport;

/**
 * 供应商中事件的基类，通过事件把HTMLAction传到EJBAction
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 启明公司</p>
 * @author 刘家坤
 * @version 1.0
 */
public class SupplierEvent
    extends EventSupport {
  // 激发创建动作。
  public static int ADD = 1;

  // 激发更新动作。
  public static int UPDATE = 2;

  // 激发删除动作。
  public static int DELETE = 3;

  // 动作类型。
  protected int actionType = -1;
  protected SupplierFormData formData;

  public SupplierEvent() {

  }

  /**
   * 构造方法
   * @param actionType 动作类型
   * @param formData 数据封装类
   */
  public SupplierEvent(int actionType, SupplierFormData formData) {
    this.actionType = actionType;
    this.formData = formData;
  }

  /**
   * 获得动作类型
   */
  public int getActionType() {
    return actionType;
  }

  /**
   * 获得数据封装类
   */
  public SupplierFormData getsupplierFormData() {
    return formData;
  }

  /**
   * 获取事件名称。
   * @return java.lang.String
   */
  public String getEventName() {
    return "com.faw_qm.technics.consroute.client.SupplierEJBAction";
  }

}
