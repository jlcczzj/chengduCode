package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.*;
import com.faw_qm.framework.service.*;
import com.faw_qm.framework.exceptions.*;

/**
 * <p>Title: </p>
* <p>Description:专为解放公司设计 20061101 </p>
* <p>Copyright: Copyright (c) 2006</p>
* <p>Company: 一汽启明</p>
* @author 刘明
* @version 1.0
 */


public class ReportResultHomeDelegate implements BsoHomeDelegate {

  private ReportResultHome home = null;

  public void init(Object obj)
  {
            if(!(obj instanceof ReportResultHome))
            {
                    Object[] objs={obj.getClass(),"ReportResultHome"};
             throw new QMRuntimeException("com.faw_qm.framework.util.FrameworkResource","70",objs);
            }
       home = (ReportResultHome)obj;
  }

 /**
  *  用于根据主键恢复对象
  **/
  public BsoReference findByPrimaryKey(String bsoID) throws FinderException
  {
           return home.findByPrimaryKey(bsoID);
  }

  /**
   * 根据值对象建立业务对象
   */
  public BsoReference create(BaseValueIfc info) throws CreateException
  {
          return home.create(info);
  }

  /**
   *  根据值对象和指定时间戳建立业务对象
   */
  public BsoReference create(BaseValueIfc info,Timestamp ct,Timestamp mt) throws CreateException
  {
          return home.create(info,ct,mt);
  }


}
