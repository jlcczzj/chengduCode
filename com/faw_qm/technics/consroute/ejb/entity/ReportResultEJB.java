package com.faw_qm.technics.consroute.ejb.entity;

import java.util.* ;
import javax.ejb.* ;
import com.faw_qm.framework.service.* ;
//CCBegin by wanghonglian 2008-07-30 
//注释掉不使用的导入路径
//import com.faw_qm.util.EJBServiceHelper ;
//import com.faw_qm.persist.ejb.service.PersistService ;
//CCEnd by wanghonglian 2008-07-30
import com.faw_qm.framework.exceptions.QMException ;
import com.faw_qm.technics.consroute.model.ReportResultInfo;

/**
 * <p>Title:报表结果 </p>
 * <p>Description:专为解放公司设计 20061101 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 刘明
 * @version 1.0
 */


public abstract class ReportResultEJB extends BsoReferenceEJB{
  public ReportResultEJB() {
  }

  public String getBsoName () {
      return "consReportResult" ;
  }


  /**
   * 设置路线表的编号
   * @param num String
   */
  public abstract void setRouteListNum(String num);

  /**
   * 获得路线表编号
   * @return String
   */
  public abstract String getRouteListNum();

  /**
   * 设置报表内容（Blob字段）
   * @param v Vector
   */
  public abstract void setContent(Vector v);

  /**
   * 获得报表内容（Blob字段）
   * @return Vector
   */
  public abstract Vector getContent();

  /**
   * 获取业务对象对应的值对象
   * @throws QMException
   * @return BaseValueIfc
   */
  public BaseValueIfc getValueInfo ()
      throws QMException {
      ReportResultInfo info = new ReportResultInfo() ;
      setValueInfo ( info ) ;
      return info ;
  }

  /**
   * 设置业务对象对应的值对象，需要设定锁服务的信息
   * @throws QMException
   * @param info BaseValueIfc
   */
  public void setValueInfo ( BaseValueIfc info )
      throws QMException {
      super.setValueInfo ( info ) ;
      ReportResultInfo tri = ( ReportResultInfo ) info ;
      tri.setRouteListNum (this.getRouteListNum() ) ;
      tri.setContent(this.getContent());
  }

  /**
   * 由传入的值对象转化为对应的业务类的对象
   * @exception CreateException 创建异常
   * @param info BaseValueIfc
   */
  public void createByValueInfo ( BaseValueIfc info )
      throws CreateException
  {
      if(info != null){
          super.createByValueInfo ( info ) ;
          ReportResultInfo tri = ( ReportResultInfo ) info ;
          this.setRouteListNum(tri.getRouteListNum());
          this.setContent(tri.getContent());
      }
  }

  /**
   * 过载父类的方法。首先调用父类的相应方法，其后在为本类中定制的业务属性赋值。
   * @throws QMException
   * @param info BaseValueIfc
   */
  public void updateByValueInfo ( BaseValueIfc info )
      throws QMException
  {
      if(info != null)
      {
          super.updateByValueInfo ( info ) ;
          ReportResultInfo tri = ( ReportResultInfo ) info ;
          this.setRouteListNum(tri.getRouteListNum());
          this.setContent(tri.getContent());
      }
  }

}
