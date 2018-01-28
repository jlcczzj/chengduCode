/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 * ����ʱ�� 2003/09/09
 */


package com.faw_qm.technics.route.model;

import com.faw_qm.codemanage.ejb.entity.Coding;
import com.faw_qm.codemanage.model.CodingIfc;
import com.faw_qm.framework.service.BaseValueInfo;

/**
 * ����·��ֵ����
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company:faw-qm </p>
 * @author �ܴ�Ԫ
 * @version 1.0
 */
public class TechnicsRouteInfo
    extends BaseValueInfo
    implements TechnicsRouteIfc {
  //����
  private String description;
  //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��
  private CodingIfc modefyIdenty;
  private String defaultDescreption;
  private boolean isAdopt;
  //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��  
  private static final long serialVersionUID = 1L;

  /**
   * ���캯��
   */
  public TechnicsRouteInfo() {

  }

  /**
   * ·��˵��
   * @return java.lang.String
   */
  public String getRouteDescription() {
    return this.description;
  }

  /**
   * ·��˵����
   * @param description
   */
  public void setRouteDescription(String description) {
    this.description = description;
  }

  /**
   * �õ���ʶ
   * @return java.lang.String
   */
  public String getIdentity() {
    if (this.getBsoID() == null) {
      return this.getBsoName();
    }
    else {
      return this.getBsoID();
    }
  }
  
  //CCBegin by leixiao 2008-8-4 ԭ�򣺽����������·��
       public CodingIfc getModefyIdenty(){
	    return this.modefyIdenty;
	   }

	    public void setModefyIdenty(CodingIfc identy){
	      this.modefyIdenty = identy;
	    }

	    public boolean getIsAdopt(){
	      return this.isAdopt;
	    }

	   public void setIsAdopt(boolean adopt){
	     this.isAdopt = adopt;
	   }
	   /**
	      * Ĭ����Ч��˵��.
	      */
	     public java.lang.String getDefaultDescreption(){
	       return this.defaultDescreption;
	     }

	     /**
	      * Ĭ����Ч��˵��.
	      */
	     public void setDefaultDescreption(String des){
	       this.defaultDescreption = des;
	     }
	     //CCEnd by leixiao 2008-8-4 ԭ�򣺽����������·��  

  /**
   * �õ�ҵ������
   * @return String
   */
  public String getBsoName() {
    return "TechnicsRoute";
  }
}
