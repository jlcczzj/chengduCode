package com.faw_qm.technics.consroute.ejb.entity;

import java.util.* ;
import javax.ejb.* ;
import com.faw_qm.framework.service.* ;
//CCBegin by wanghonglian 2008-07-30 
//ע�͵���ʹ�õĵ���·��
//import com.faw_qm.util.EJBServiceHelper ;
//import com.faw_qm.persist.ejb.service.PersistService ;
//CCEnd by wanghonglian 2008-07-30
import com.faw_qm.framework.exceptions.QMException ;
import com.faw_qm.technics.consroute.model.ReportResultInfo;

/**
 * <p>Title:������ </p>
 * <p>Description:רΪ��Ź�˾��� 20061101 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */


public abstract class ReportResultEJB extends BsoReferenceEJB{
  public ReportResultEJB() {
  }

  public String getBsoName () {
      return "consReportResult" ;
  }


  /**
   * ����·�߱�ı��
   * @param num String
   */
  public abstract void setRouteListNum(String num);

  /**
   * ���·�߱���
   * @return String
   */
  public abstract String getRouteListNum();

  /**
   * ���ñ������ݣ�Blob�ֶΣ�
   * @param v Vector
   */
  public abstract void setContent(Vector v);

  /**
   * ��ñ������ݣ�Blob�ֶΣ�
   * @return Vector
   */
  public abstract Vector getContent();

  /**
   * ��ȡҵ������Ӧ��ֵ����
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
   * ����ҵ������Ӧ��ֵ������Ҫ�趨���������Ϣ
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
   * �ɴ����ֵ����ת��Ϊ��Ӧ��ҵ����Ķ���
   * @exception CreateException �����쳣
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
   * ���ظ���ķ��������ȵ��ø������Ӧ�����������Ϊ�����ж��Ƶ�ҵ�����Ը�ֵ��
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
