/**
 * ���ɳ��� ListRoutePartLinkIfc.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ���Ӳ�ɫ����ʶ liuyang 2014-6-6
 */
package com.faw_qm.technics.route.model;

import com.faw_qm.framework.service.BinaryLinkIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;

/**
 * <p> Title:ListRoutePartLinkIfc.java </p>
 * <p> Description:·�����ӽӿ� leftID = routeListID, rightID = partMasterID. </p>
 * <p> Package:com.faw_qm.technics.route.model </p>
 * <p> ProjectName:CAPP </p>
 * <p> Copyright: Copyright (c) 2005 </p>
 * <p> Company:һ������ </p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public interface ListRoutePartLinkIfc
    extends BinaryLinkIfc {
  /**
   * �������ID��
   */
  public void setPartMasterID(String partMasterID);

  /**
   * �õ��첿����ID
   * @return String
   */
  public String getPartMasterID();

  /**
   * ���ù���·��ID��
   */
  public void setRouteID(String routeID);

  /**
   * �õ�����·��ID��
   * @return String
   */
  public String getRouteID();

  /**
   * ���ù���·�߱�ID��
   */
  public void setRouteListID(String routeListID);

  /**
   * �õ�����·�߱�ID��
   * @return String
   */
  public String getRouteListID();

  /**
   * �ж�����Ƿ��й���·�ߡ���Ϊ�����Ӧ�Ĺ���·��ͨ������ɾ�������Դ˴��Ĺ���·��״ ̬��Ϊ�־û����ԡ�
   * ע�⣺��RoutePartLinkɾ��ʱӦά�������ԣ��������ݵ�һ���ԡ�
   * ���ݵ�һ������saveRoute��RoutePostDeleteListener��ά����
   * @return String
   */
  public String getAdoptStatus();

  /**
   * ��������Ĺ���·���Ƿ񱻲��á�
   * @param status
   *            ���á�ȡ����
   */
  public void setAdoptStatus(String status);

  //st skybird 2005.3.1 �㲿����ĸ������
  // gcy add in 2005.4.26 for reinforce requirement start
  public String getParentPartNum();

  /**
   * �����㲿������㲿���ĸ������
   * @param status
   *            ���á�ȡ����
   */
  public void setParentPartNum(String parentPartNum);

  /**
   * �õ�������
   * @return String
   */
  public String getParentPartName();

  /**
   * ���ø�����
   * @param parentPartNum String
   */
  public void setParentPartName(String parentPartNum);

  /**
   * �õ�������id
   */
  public String getParentPartID();

  /**
   * ���ø�����id
   * @param parentPartNum String
   */
  public void setParentPartID(String parentPartNum);

  /**
   * �õ�����ֵ����
   * @return QMPartIfc
   */
  public QMPartIfc getParentPart();

  /**
   * ���ø���ֵ����
   * @param part QMPartIfc
   */
  public void setParentPart(QMPartIfc part);

  /**
   * �õ���ǰ�㲿���ڸ�����ʹ����������һ����ֱ���ϼ���
   * @return String
   */
  public int getCount();

  /**
   * ���õ�ǰ�㲿���ڸ�����ʹ����������һ����ֱ���ϼ���
   * @param count int
   */
  public void setCount(int count);

  // gcy add in 2005.4.26 for reinforce requirement end

  /**
   * �˱�ʶ�����ж���·�߱������·���Ƿ���ʾ���������嵥�У����Ƿ�����á�ȡ����
   * @return int =0
   *         ��ʾ�Ǵ���һ�汾�̳������ģ�int=1���ӱ��汾���ɵġ��漰��·���Ƿ��������ɣ�int=2���˰汾ɾ���ģ���·�߱���ʱ�������ƴ˹�����
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
  public String getPartBranchID();

  /**
   * ���ù����������С�汾
   * @param listIterationID String
   */
  public void setPartBranchID(String partBranchID) ;
  
  /**
   * �õ������������С�汾
   * @return String
   */
  public QMPartIfc getPartBranchInfo();

  /**
   * ���ù����������С�汾
   * @param listIterationID String
   */
  public void setPartBranchInfo(QMPartIfc partBranchInfo) ;
//CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
  

  /**
   * ��ʼ���ð汾��A, B.��汾�š�
   * @return String
   */
  public String getInitialUsed();

  /**
   * ���ò��ð汾��A, B.��汾��
   * @param version String
   */
  public void setInitialUsed(String version);

  /**
   * �����㲿������Ϣֵ����
   * @return QMPartMasterIfc
   */
  public QMPartMasterIfc getPartMasterInfo();

  /**
   * �����㲿������Ϣֵ����
   * @param partMasterInfo QMPartMasterIfc
   */
  public void setPartMasterInfo(QMPartMasterIfc partMasterInfo);
//CCBegin SS1
  /**
   * ��ɫ��ʶ
   */
  public String getColorFlag();
  public void setColorFlag(String colorFlag);
  //CCEnd SS1
}
