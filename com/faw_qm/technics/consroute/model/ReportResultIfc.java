package com.faw_qm.technics.consroute.model;

import com.faw_qm.framework.service.*;
import java.util.Vector;
/**
 * <p>Title:������ </p>
 * <p>Description:רΪ��Ź�˾��� 20061101 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */


public interface ReportResultIfc extends BaseValueIfc{

  /**
   * ����·�߱�ı��
   * @param num String
   */
  public void setRouteListNum(String num);

  /**
   * ���·�߱���
   * @return String
   */
  public String getRouteListNum();

  /**
   * ���ñ������ݣ�Blob�ֶΣ�
   * @param v Vector
   */
  public void setContent(Vector v);

  /**
   * ��ñ������ݣ�Blob�ֶΣ�
   * @return Vector
   */
  public Vector getContent();

}
