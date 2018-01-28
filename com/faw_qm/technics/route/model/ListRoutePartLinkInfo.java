/**
 * ���ɳ��� ListRoutePartLinkInfo.java    1.0    2005/07/01
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * SS1 ���Ӳ�ɫ����ʶ liuyang 2014-6-6
 * SS2 �ɶ�����·�����⴦�����ʶ��Ϊ�ɶ����㲿�����а汾ת�� ����� 2018-01-11
 */
package com.faw_qm.technics.route.model;

import com.faw_qm.framework.service.BinaryLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteServiceEJB;
import com.faw_qm.technics.route.util.RouteAdoptedType;

/**
 * <p>Title:ListRoutePartLinkInfo.java</p>
 * <p>Description:·���㲿����ϵֵ���� </p>
 * <p>Package:com.faw_qm.technics.route.model</p>
 * <p>ProjectName:CAPP</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:һ������</p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public class ListRoutePartLinkInfo
    extends BinaryLinkInfo
    implements ListRoutePartLinkIfc {

  /**
   * �㲿������Ϣֵ����
   */
  private QMPartMasterIfc partMasterInfo;

  /**
   * ����ֵ����
   */
  private QMPartIfc parentPartInfo;

  /**
   * ·��id
   */
  private String routeID;

  /**
   * �޸�״̬
   */
  private int alterStatus;

  /**
   * ·��״̬
   */
  private String status;

  //st skybird 2005.3.1

  /**
   * �������
   */
  private String parentPartNum;

  /**
   * ��������
   */
  private String parentPartName;

  /**
   * ����id
   */
  private String parentPartID;

  /**
   * �㲿���ڸ����ڲ�Ʒ��ʹ�õ�����
   */
  private int count;

  /**
   * ��ʼ·�߱��汾
   */
  private String vesion;

  /**
   * ·�߱�����Ϣid
   */
  private String listMasterID;

  /**
   * ·�߱�ľ���汾
   */
  private String listIterationID;
  
//CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
  private String PartBranchID;
  
  private QMPartIfc partBranchInfo;
//CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
  
  private static final long serialVersionUID = 1L;
  //CCBegin SS1
  private String colorFlag;
  //CCEnd SS1
//CCBegin SS2
  private String specialFlag;
  //CCEnd SS2
  /**
   * ���캯��
   *
   */
  protected ListRoutePartLinkInfo() {
  }

  //��֧��API.
  public static ListRoutePartLinkInfo newListRoutePartLinkInfo() {
    ListRoutePartLinkInfo listLinkInfo = new ListRoutePartLinkInfo();
    return listLinkInfo;
  }

  /**
   * �ͻ��˵��÷���������һ���µ�ֵ����
   * @param routeListInfo TechnicsRouteListIfc
   * @param partMasterID String
   * @return ListRoutePartLinkInfo
   */
  public static ListRoutePartLinkInfo newListRoutePartLinkInfo(
      TechnicsRouteListIfc routeListInfo,
      String partMasterID) {
    ListRoutePartLinkInfo listLinkInfo = new ListRoutePartLinkInfo();
    listLinkInfo.setPartMasterID(partMasterID);
    listLinkInfo.setRouteListID(routeListInfo.getBsoID());
    listLinkInfo.setInitialUsed(routeListInfo.getVersionID());
    //·��δ���ɣ���ʼ״̬Ϊ�̳С�
    listLinkInfo.setAlterStatus(TechnicsRouteServiceEJB.INHERIT);
    listLinkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
    listLinkInfo.setRouteListIterationID(routeListInfo.getVersionValue());
    listLinkInfo.setRouteListMasterID(routeListInfo.getMaster().getBsoID());
    return listLinkInfo;
  }
//CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id 
  public static ListRoutePartLinkInfo newListRoutePartLinkInfo(
	      TechnicsRouteListIfc routeListInfo,
	      String partMasterID,String partid) {
	    ListRoutePartLinkInfo listLinkInfo = new ListRoutePartLinkInfo();
	    listLinkInfo.setPartMasterID(partMasterID);
	    listLinkInfo.setRouteListID(routeListInfo.getBsoID());
	    listLinkInfo.setInitialUsed(routeListInfo.getVersionID());
	    //·��δ���ɣ���ʼ״̬Ϊ�̳С�
	    listLinkInfo.setAlterStatus(TechnicsRouteServiceEJB.INHERIT);
	    listLinkInfo.setAdoptStatus(RouteAdoptedType.CANCEL.getDisplay());
	    listLinkInfo.setRouteListIterationID(routeListInfo.getVersionValue());
	    listLinkInfo.setRouteListMasterID(routeListInfo.getMaster().getBsoID());
//	  CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
	    listLinkInfo.setPartBranchID(partid);
//	  CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id

	    return listLinkInfo;
	  }
//CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
  /**
   * getBsoName
   * @return String
   */
  public String getBsoName() {
    return "ListRoutePartLink";
  }

  /**
   * �õ�·��id
   * @return String
   */
  public String getRouteID() {
    return this.routeID;
  }

  /**
   * ����·��id
   */
  public void setRouteID(String routeID) {
    this.routeID = routeID;
  }

  /**
   * �õ��㲿������Ϣid
   * @return String
   */
  public String getPartMasterID() {
    return this.getRightBsoID();
  }

  /**
   *�����㲿������Ϣid
   */
  public void setPartMasterID(String partMasterID) {
    this.setRightBsoID(partMasterID);
  }

  /**
   * �ж�����Ƿ��й���·�ߡ���Ϊ�����Ӧ�Ĺ���·��ͨ������ɾ�������Դ˴��Ĺ���·��״ ̬��Ϊ�־û����ԡ�
   * ע�⣺��RoutePartLinkɾ��ʱӦά�������ԣ��������ݵ�һ���ԡ�
   * ���ݵ�һ������saveRoute��RoutePostDeleteListener��ά����
   */
  public String getAdoptStatus() {
    return this.status;
  }

  /**
   * ��������Ĺ���·���Ƿ񱻲��á�
   * @param status
   *            ���á�ȡ����
   */
  public void setAdoptStatus(String status) {
    this.status = status;
  }

  //st skybird 2005.3.1
  // gcy add in 2005.4.26 for reinforce requirement  start

  /**
   * �����㲿������㲿���ĸ������
   * @param status
   *            ���á�ȡ����
   */
  public String getParentPartNum() {
    return this.parentPartNum;
  }

  /**
   * �����㲿���ĸ������
   * @param parentPartNum String
   */
  public void setParentPartNum(String parentPartNum) {
    this.parentPartNum = parentPartNum;
  }

  /**
   * �õ�������
   * @return String
   */
  public String getParentPartName() {
    return this.parentPartName;
  }

  /**
   * ���ø�����
   * @param parentPartNum String
   */
  public void setParentPartName(String parentPartNum) {
    this.parentPartName = parentPartNum;
  }

  /**
   * �õ�������id
   * @return String
   */
  public String getParentPartID() {
    return this.parentPartID;
  }

  /**
   * ���ø�����id
   * @param parentPartNum String
   */
  public void setParentPartID(String parentPartNum) {
    this.parentPartID = parentPartNum;
  }

  /**
   * �õ�����ֵ����
   * @return String
   */
  public QMPartIfc getParentPart() {
    return this.parentPartInfo;
  }

  /**
   * ���ø���ֵ����
   * @param part QMPartIfc
   */
  public void setParentPart(QMPartIfc part) {
    this.parentPartInfo = part;
  }

  /**
   * �õ���ǰ�㲿���ڸ�����ʹ����������һ����ֱ���ϼ���
   * @return String
   */
  public int getCount() {
    return this.count;
  }

  /**
   * ���õ�ǰ�㲿���ڸ�����ʹ����������һ����ֱ���ϼ���
   * @param count int
   */
  public void setCount(int count) {
    this.count = count;
  }

// gcy add in 2005.4.26 for reinforce requirement  end


  //ed

  /**
   * ��ʼ���ð汾��A, B.��汾�š�
   * @return String
   */
  public String getInitialUsed() {
    return this.vesion;
  }

  /**
   * ���ó�ʼ���ð汾��A, B.��汾�š�
   * @param vesion String
   */
  public void setInitialUsed(String version) {
    this.vesion = version;
  }

  /**
   * �˱�ʶ�����ж���·�߱������·���Ƿ���ʾ���������嵥�У����Ƿ�����á�ȡ����
   * @return int =0
   *         ��ʾ�Ǵ���һ�汾�̳������ģ�int=1���ӱ��汾���ɵġ��漰��·���Ƿ��������ɣ�int=2���˰汾ɾ���ģ���·�߱���ʱ�������ƴ˹�����
   */
  public int getAlterStatus() {
    return this.alterStatus;
  }

  /**
   * �����޸�״̬
   * @param alterStatus int int =0 ��ʾ�Ǵ���һ�汾�̳������ģ�
   * int=1���ӱ��汾���ɵġ��漰��·���Ƿ��������ɣ�
   * int=2���˰汾ɾ���ģ���·�߱���ʱ�������ƴ˹�����
   */
  public void setAlterStatus(int alterStatus) {
    this.alterStatus = alterStatus;
  }

  /**
   * �õ�����·�߱�ID��
   * @return String
   */
  public String getRouteListID() {
    return this.getLeftBsoID();
  }

  /**
   * ���ù���·�߱�ID��
   */
  public void setRouteListID(String routeListID) {
    this.setLeftBsoID(routeListID);
  }

  /**
   * �õ�·�߱��id
   * @return String ·�߱��id
   */
  public String getRouteListMasterID() {
    return this.listMasterID;
  }

  /**
   * ����·�߱��id
   * @param listMasterID String ·�߱��id
   */
  public void setRouteListMasterID(String listMasterID) {
    this.listMasterID = listMasterID;
  }

  /**
   * �õ�������·�߱��С�汾
   * @return String
   */
  public String getRouteListIterationID() {
    return this.listIterationID;
  }

  /**
   * ���ù�����·�߱��С�汾
   * @param listIterationID String
   */
  public void setRouteListIterationID(String listIterationID) {
    this.listIterationID = listIterationID;
  }
//CCBegin by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
  /**
   * �õ������������С�汾
   * @return String
   */
  public String getPartBranchID() {
    return this.PartBranchID;
  }

  /**
   * ���ù����������С�汾
   * @param listIterationID String
   */
  public void setPartBranchID(String partBranchID) {
    this.PartBranchID = partBranchID;
  }
  
  /**
   * �õ������������С�汾
   * @return String
   */
  public QMPartIfc getPartBranchInfo() {
    return this.partBranchInfo;
  }

  /**
   * ���ù����������С�汾
   * @param listIterationID String
   */
  public void setPartBranchInfo(QMPartIfc partBranchInfo) {
    this.partBranchInfo = partBranchInfo;
  }
//CCEnd by leixiao 2009-1-12 ԭ�򣺽����������·�ߣ���¼��ǰ���id
  /**
   * �ڿͻ��˵��á�
   * @return QMPartMasterIfc
   */
  public QMPartMasterIfc getPartMasterInfo() {
    return this.partMasterInfo;
  }

  /**
   * partMasterInfo��EJB��ʼ����
   * @param partMasterInfo QMPartMasterIfc
   */
  public void setPartMasterInfo(QMPartMasterIfc partMasterInfo) {
    this.partMasterInfo = partMasterInfo;
  }

//CCBegin SS1
public String getColorFlag() {
	
	return this.colorFlag;
}


public void setColorFlag(String colorFlag) {
	this.colorFlag=colorFlag;
}
//CCEnd SS1

//CCBegin SS2
public String getSpecialFlag() {
	
	return this.specialFlag;
}


public void setSpecialFlag(String specialFlag) {
	this.specialFlag=specialFlag;
}
//CCEnd SS2
}