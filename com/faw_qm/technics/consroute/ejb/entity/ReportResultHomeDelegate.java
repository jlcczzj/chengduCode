package com.faw_qm.technics.consroute.ejb.entity;

import java.sql.Timestamp;
import javax.ejb.*;
import com.faw_qm.framework.service.*;
import com.faw_qm.framework.exceptions.*;

/**
 * <p>Title: </p>
* <p>Description:רΪ��Ź�˾��� 20061101 </p>
* <p>Copyright: Copyright (c) 2006</p>
* <p>Company: һ������</p>
* @author ����
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
  *  ���ڸ��������ָ�����
  **/
  public BsoReference findByPrimaryKey(String bsoID) throws FinderException
  {
           return home.findByPrimaryKey(bsoID);
  }

  /**
   * ����ֵ������ҵ�����
   */
  public BsoReference create(BaseValueIfc info) throws CreateException
  {
          return home.create(info);
  }

  /**
   *  ����ֵ�����ָ��ʱ�������ҵ�����
   */
  public BsoReference create(BaseValueIfc info,Timestamp ct,Timestamp mt) throws CreateException
  {
          return home.create(info,ct,mt);
  }


}
