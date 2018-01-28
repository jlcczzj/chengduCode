/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 * SS1 ���Ӳ�ɫ����ʶ liuyang 2014-6-6
 */

package com.faw_qm.technics.route.ejb.entity;

import com.faw_qm.framework.service.BinaryLink;

/**
 * ����·�߶�Ӧ�Ĳ�Ʒ�ṹ����ѡ�񡣲�Ʒ�������
 * <p>Title: </p>
 * <p>Description: leftID = routeListID, rightID = partMasterID.</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:faw_qm </p>
 * @author �ܴ�Ԫ
 * @version 1.0 2005/07/07
 */
public interface ListRoutePartLink
    extends BinaryLink {

  /**
   * ���ù���·��ID��
   * @roseuid 4039BA590094
   */
  public void setRouteID(String routeID);

  /**
   * �õ�·�ߵ�id
   * @return String
   */
  public String getRouteID();

  /**
   * �ж�����Ƿ��й���·�ߡ���Ϊ�����Ӧ�Ĺ���·��ͨ������ɾ�������Դ˴��Ĺ���·��״
   * ̬��Ϊ�־û����ԡ�
   * ע�⣺��RoutePartLinkɾ��ʱӦά�������ԣ��������ݵ�һ���ԡ�
   * ���ݵ�һ������saveRoute��RoutePostDeleteListener��ά����
   * @return boolean
   */
  public String getAdoptStatus();

  /**
   * ��������Ĺ���·���Ƿ񱻲��á�
   * @param status ���á�ȡ����
   */
  public void setAdoptStatus(String status);

  //st skybird 2005.3.1 �㲿���ĸ������
  // gcy add in 2005.4.26 for reinforce requirement  start

  /**
   * �õ������ı��
   * @return String �����ı��
   */
  public String getParentPartNum();

  /**
   * ���ø����ı��
   * @param parentPartNum String
   */
  public void setParentPartNum(String parentPartNum);

  /**
   * �õ�������
   * @return String ����������
   */
  public String getParentPartName();

  /**
   * ���ø���������
   * @param parentPartNum String
   */
  public void setParentPartName(String parentPartNum);

  /**
   * �õ�������id
   * @return String
   */
  public String getParentPartID();

  /**
   * ���ø�����id
   * @param parentPartNum String ������id
   */
  public void setParentPartID(String parentPartNum);

  /**
   * �õ���ǰ�㲿���ڸ������ڲ�Ʒ��ʹ����������һ����ֱ���ϼ���
   * @return int  �㲿���ڸ�����ʹ����������һ����ֱ���ϼ���
   */
  public int getCount();

  /**
   * �����㲿���ڸ������ڲ�Ʒ��ʹ�õ���������һ����ֱ���ϼ���
   * @param count int
   */
  public void setCount(int count);

  // gcy add in 2005.4.26 for reinforce requirement  end


  /**
   * �˱�ʶ�����ж���·�߱������·���Ƿ���ʾ���������嵥�У����Ƿ�����á�ȡ����
   * @return int =0 ��ʾ�Ǵ���һ�汾�̳������ģ�
   * int=1���ӱ��汾���ɵġ��漰��·���Ƿ��������ɣ�
   * int=2���˰汾ɾ���ģ���·�߱���ʱ�������ƴ˹�����
   */
  public int getAlterStatus();

  /**
   * �����޸�״̬
   * @param alterStatus int int =0 ��ʾ�Ǵ���һ�汾�̳������ģ�
   * int=1���ӱ��汾���ɵġ��漰��·���Ƿ��������ɣ�
   * int=2���˰汾ɾ���ģ���·�߱���ʱ�������ƴ˹�����
   */
  public void setAlterStatus(int alterStatus);

  /**
   * �õ�·�߱��id
   * @return String ·�߱��id
   */
  public String getRouteListMasterID();

  /**
   * ����·�߱��id
   * @param listMasterID String ·�߱��id
   */
  public void setRouteListMasterID(String listMasterID);

  /**
   * �õ�������·�߱��С�汾
   * @return String
   */
  public String getRouteListIterationID();

  /**
   * ���ù�����·�߱��С�汾
   * @param listIterationID String
   */
  public void setRouteListIterationID(String listIterationID);
  
  
//CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
  /**
   * �õ������������С�汾
   * @return String
   */
  public abstract String getPartBranchID();

  /**
   * ���ù����������С�汾
   * @param listIterationID String
   */
  public abstract void setPartBranchID(String partBranchID);
  
//CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id

  /**
   * ��ʼ���ð汾��A, B.��汾�š�
   * @return String
   */
  public abstract String getInitialUsed();

  /**
   * ���ò��ð汾��A, B.��汾��
   * @param version String
   */
  public abstract void setInitialUsed(String version);
//CCBegin SS1
  /**
   * ��ɫ��ʶ
   */
  public String getColorFlag();
  public void setColorFlag(String colorFlag);
  //CCEnd SS1
}
