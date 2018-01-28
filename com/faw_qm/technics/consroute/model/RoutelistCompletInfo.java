/**
 * 生成程序  ResourceManagedInfo	1.9  2003/8/10
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
 * <p>Description: 资源管理抽象类的值对象,继承ManagedInfo,同时实现ExtendAttriedIfc的方法
 * 继承此类的持久化类的值对象,必须实现ExtendAttriedIfc的getSecondClassification()方法</p>
 * @author 赵辉
 * @version 1.0
 */
public class RoutelistCompletInfo
    extends ManagedInfo
    implements RoutelistCompletIfc {

  /**
   * 获得业务对象名。
   */
  public String getBsoName() {
    return "consRoutelistComplet";
  }

  /**
   * 获得加锁日期
   * @return Timestamp 加锁日期
   */
  public Timestamp getDate() {
    return getLockMap().getDate();
  }

  /**
   * 设置加锁日期
   * @param date 加锁日期
   */
  public void setDate(Timestamp date) {
    getLockMap().setDate(date);
  }

  /**
   * 获得加锁注释
   * @return String 加锁注释
   */
  public String getNote() {
    return getLockMap().getNote();
  }

  /**
   * 设置加锁注释
   * @param note 加锁注释
   */
  public void setNote(String note) {
    getLockMap().setNote(note);
  }

  /**
   * 获得加锁者
   * @return String 加锁者
   */
  public String getLocker() {
    return getLockMap().getLocker();
  }

  /**
   * 设置加锁者
   * @param locker 加锁者
   * @throws LockException
   */
  public void setLocker(String locker) throws LockException {
    getLockMap().setLocker(locker);
  }

  /**
   * 获得锁Map
   * @return LockMap 锁Map
   */
  public LockMap getLockMap() {
    if (lockMap == null) {
      setLockMap(new LockMap());
    }
    return lockMap;
  }

  /**
   * 设置锁Map
   * @param map 锁Map
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
   * 获得内容和容器连接Map
   * @return 内容和容器连接Map
   */
  public FormatContentHolderMap getFormatContentHolderMap()
  {
    if (formatContentHolderMap == null)
      setFormatContentHolderMap(new FormatContentHolderMap());
    //endif
    return formatContentHolderMap;
  }//end getFormatContentHolderMap()

  /**
   * 设置内容和容器连接Map
   * @param map 内容和容器连接Map
   */
  public void setFormatContentHolderMap(FormatContentHolderMap map)
  {
    this.formatContentHolderMap = map;
  }//end setFormatContentHolderMap(FormatContentHolderMap)

  /**
   * 获得内容格式BsoID
   * @return 内容格式BsoID
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
   * 设置内容格式BsoID
   * @param dataFormatID 内容格式BsoID
   */
  public void setDataFormatID(String dataFormatID)
  {
    getFormatContentHolderMap().setDataFormatID(dataFormatID);
  }//end setDataFormatID(String)

  /**
   * 获得内容格式名称
   * @return 内容格式名称
   */
  public String getDataFormatName()
  {
    return getFormatContentHolderMap().getDataFormatName();
  }//end getDataFormatName()

  /**
   * 设置内容格式名称
   * @param dataFormatName 内容格式名称
   */
  public void setDataFormatName(String dataFormatName)
  {
    getFormatContentHolderMap().setDataFormatName(dataFormatName);
  } //end setDataFormatName(String)

  /**
       * 实现Identified 接口的方法getIdentifyObject().
       * @return IdentifyObject
       * @throws QMException
       */
      public IdentifyObject getIdentifyObject()
              throws QMException
      {
          return new RoutelistCompletIdentity(this.getCompletNum());
      }



  /**
   * 锁Map
   */
  private LockMap lockMap;
  private String completNum;
  private String completName;
  private String routeCompletType;
  private String routeCompletState;
  private String completeNote;
     /**
       * 内容和容器连接Map
       */
      private FormatContentHolderMap formatContentHolderMap;

  static final long serialVersionUID = 1L;

}
