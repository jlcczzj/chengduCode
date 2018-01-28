package com.faw_qm.technics.consroute.ejb.entity;
import javax.ejb.*;
import java.sql.Timestamp;
import com.faw_qm.framework.service.*;

/**
 * <p>Title: </p>
* <p>Description:专为解放公司设计 20061101 </p>
* <p>Copyright: Copyright (c) 2006</p>
* <p>Company: 一汽启明</p>
* @author 刘明
* @version 1.0
 */

public interface ReportResultHome
  extends BsoReferenceHome {

  public abstract ReportResult create ( BaseValueIfc basevalueifc )
      throws CreateException ;

  public abstract ReportResult create ( BaseValueIfc basevalueifc
                                       , Timestamp timestamp
                                       , Timestamp timestamp1 )
      throws CreateException ;

  public abstract ReportResult findByPrimaryKey ( String s )
      throws FinderException ;


}
