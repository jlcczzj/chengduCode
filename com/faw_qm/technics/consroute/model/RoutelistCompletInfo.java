/**
 * ���ɳ���  ResourceManagedInfo	1.9  2003/8/10
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */

package com.faw_qm.technics.consroute.model;

import java.sql.Timestamp;

import com.faw_qm.enterprise.model.ManagedInfo;
import com.faw_qm.lock.exception.LockException;
import com.faw_qm.lock.model.LockMap;
import com.faw_qm.content.model.FormatContentHolderMap;
import com.faw_qm.unique.ejb.entity.*;
import com.faw_qm.technics.consroute.util.*;
import com.faw_qm.framework.exceptions.*;


/**
 * <p>Title: ResourceManagedInfo </p>
 * <p>Description: ��Դ����������ֵ����,�̳�ManagedInfo,ͬʱʵ��ExtendAttriedIfc�ķ���
 * �̳д���ĳ־û����ֵ����,����ʵ��ExtendAttriedIfc��getSecondClassification()����</p>
 * @author �Ի�
 * @version 1.0
 */
public class RoutelistCompletInfo
    extends ManagedInfo
    implements RoutelistCompletIfc {

  /**
   * ���ҵ���������
   */
  public String getBsoName() {
    return "consRoutelistComplet";
  }

  /**
   * ��ü�������
   * @return Timestamp ��������
   */
  public Timestamp getDate() {
    return getLockMap().getDate();
  }

  /**
   * ���ü�������
   * @param date ��������
   */
  public void setDate(Timestamp date) {
    getLockMap().setDate(date);
  }

  /**
   * ��ü���ע��
   * @return String ����ע��
   */
  public String getNote() {
    return getLockMap().getNote();
  }

  /**
   * ���ü���ע��
   * @param note ����ע��
   */
  public void setNote(String note) {
    getLockMap().setNote(note);
  }

  /**
   * ��ü�����
   * @return String ������
   */
  public String getLocker() {
    return getLockMap().getLocker();
  }

  /**
   * ���ü�����
   * @param locker ������
   * @throws LockException
   */
  public void setLocker(String locker) throws LockException {
    getLockMap().setLocker(locker);
  }

  /**
   * �����Map
   * @return LockMap ��Map
   */
  public LockMap getLockMap() {
    if (lockMap == null) {
      setLockMap(new LockMap());
    }
    return lockMap;
  }

  /**
   * ������Map
   * @param map ��Map
   */
  public void setLockMap(LockMap map) {
    lockMap = map;
  }

  public String getCompletNum() {
    return this.completNum;
  }

  public void setCompletNum(String num) {
    this.completNum = num;
  }

  public String getCompletName() {
    return this.completName;
  }

  public void setCompletName(String name) {
    this.completName = name;
  }

  public String getCompletType() {
    return this.routeCompletType;
  }

  public void setCompletType(String type) {
    this.routeCompletType = type;
  }

  public String getCompletNote() {
    return this.completeNote;
  }

  public void setCompletNote(String note) {
    this.completeNote = note;
  }

  public String getCompletState() {
    return this.routeCompletState;
  }

  public void setCompletState(String state) {
    this.routeCompletState = state;
  }
  public String getIdentity()
  {
      return getCompletName()+"("+getCompletNum()+")";
  }

  /**
   * ������ݺ���������Map
   * @return ���ݺ���������Map
   */
  public FormatContentHolderMap getFormatContentHolderMap()
  {
    if (formatContentHolderMap == null)
      setFormatContentHolderMap(new FormatContentHolderMap());
    //endif
    return formatContentHolderMap;
  }//end getFormatContentHolderMap()

  /**
   * �������ݺ���������Map
   * @param map ���ݺ���������Map
   */
  public void setFormatContentHolderMap(FormatContentHolderMap map)
  {
    this.formatContentHolderMap = map;
  }//end setFormatContentHolderMap(FormatContentHolderMap)

  /**
   * ������ݸ�ʽBsoID
   * @return ���ݸ�ʽBsoID
   */
  public String getDataFormatID()
  {
    return getFormatContentHolderMap().getDataFormatID();
  }//end getDataFormatID()
  public String getStandardIcon()
  {
    return getIconName("StandardIcon");
  }

  /**
   * �������ݸ�ʽBsoID
   * @param dataFormatID ���ݸ�ʽBsoID
   */
  public void setDataFormatID(String dataFormatID)
  {
    getFormatContentHolderMap().setDataFormatID(dataFormatID);
  }//end setDataFormatID(String)

  /**
   * ������ݸ�ʽ����
   * @return ���ݸ�ʽ����
   */
  public String getDataFormatName()
  {
    return getFormatContentHolderMap().getDataFormatName();
  }//end getDataFormatName()

  /**
   * �������ݸ�ʽ����
   * @param dataFormatName ���ݸ�ʽ����
   */
  public void setDataFormatName(String dataFormatName)
  {
    getFormatContentHolderMap().setDataFormatName(dataFormatName);
  } //end setDataFormatName(String)

  /**
       * ʵ��Identified �ӿڵķ���getIdentifyObject().
       * @return IdentifyObject
       * @throws QMException
       */
      public IdentifyObject getIdentifyObject()
              throws QMException
      {
          return new RoutelistCompletIdentity(this.getCompletNum());
      }



  /**
   * ��Map
   */
  private LockMap lockMap;
  private String completNum;
  private String completName;
  private String routeCompletType;
  private String routeCompletState;
  private String completeNote;
     /**
       * ���ݺ���������Map
       */
      private FormatContentHolderMap formatContentHolderMap;

  static final long serialVersionUID = 1L;

}
