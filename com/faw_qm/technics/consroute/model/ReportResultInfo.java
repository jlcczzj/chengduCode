package com.faw_qm.technics.consroute.model;
import com.faw_qm.framework.service.*;
import java.util.Vector;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class ReportResultInfo extends BaseValueInfo implements ReportResultIfc{

  private String listNumber;
  private Vector content;

  /**
   * getBsoName
   *
   * @return String
   */
  public String getBsoName() {
    return "consReportResult";
  }

  /**
   * setRouteListNum
   *
   * @param num String
   */
  public void setRouteListNum(String num) {
    listNumber = num;
  }


  /**
   * getRouteListNum
   *
   * @return String
   */
  public String getRouteListNum() {
    return listNumber;
  }

  /**
   * setContent
   *
   * @param v Vector
   */
  public void setContent(Vector v) {
    content = v;
  }

  /**
   * getContent
   *
   * @return Vector
   */
  public Vector getContent() {
    return content;
  }

static final long serialVersionUID = 1L;

}
