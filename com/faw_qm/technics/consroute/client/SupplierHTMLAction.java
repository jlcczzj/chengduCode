/** ���ɳ���QMRouteHTMLAction.java	2007/05/29
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
 */
package com.faw_qm.technics.consroute.client;
 
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import java.util.*;
import com.faw_qm.technics.consroute.util.SupplierFormData;
import com.faw_qm.technics.consroute.util.SupplierHelper;
import com.faw_qm.framework.controller.web.action.HTMLActionException;
import com.faw_qm.framework.controller.web.action.HTMLActionSupport;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.event.Event;
import com.faw_qm.framework.event.EventResponse;
import java.util.*;


/**
 * ��Ӧ��HTMLAction�����html�ͻ���Ϣ�����з�װ
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: ������˾</p>
 * @author ������
 * @version 1.0
 *
 */
public class SupplierHTMLAction
    extends HTMLActionSupport {
  //����
  protected final static String ACTION = "action";

  //��Ӹ��Ķ�����
  protected final static String ADD_ACTION = "add";

  //���¸��Ķ�����
  protected final static String UPDATE_ACTION = "update";

  //ɾ�����Ķ�����
  protected final static String DELETE_ACTION = "delete";


  // ����������
  protected String actionType;

  // ������������Ϣ��
  protected HashMap requestMap;


//request�����������
  boolean VERBOSE = true;

  public SupplierHTMLAction() {

  }

  /**
   * �÷���ִ�й�Ӧ������
   */
  public Event perform(HttpServletRequest request) throws HTMLActionException,
      HTMLActionException, QMException {
    if (VERBOSE) {
      System.out.println("SupplierHTMLAction.perform() begin......");
      //  webaction��ejbaction֮��Ĺ�����
    }
    SupplierEvent docEvent = null;

    //  Ϊ�˻�ȡֵ������Ϣ����
    SupplierFormData formData = null;

    // action����
    String actionType = "";
   
    actionType = (String) request.getParameter("action");
 
    if (VERBOSE) {
      System.out.println("������Ϊ =" + actionType);

      //  ���action����Ϊ���򷵻ؿ�
    }
    if (actionType == null || actionType.equals("")) {
      return null;
    }

    //  ���Ϊ���½�������
    if (actionType.equals("add")) {
      formData = gainMessage(request);
      docEvent = new SupplierEvent(SupplierEvent.ADD, formData);
    }
    //  ���Ϊ�����¡�����
    if (actionType.equals("update"))
    {
        formData = updateGainMessage(request);
        docEvent = new SupplierEvent(SupplierEvent.UPDATE, formData);
    }

    //  ���Ϊ��ɾ��������
    if (actionType.equals("delete"))
    {
        formData = deleteGainMessage(request);
        docEvent = new SupplierEvent(SupplierEvent.DELETE, formData);
    }

    if (VERBOSE) {
      System.out.println("SupplierHTMLAction.perform() end..... return " +
                         docEvent +
                         "");
    }
    return docEvent;
  }


  /**
   * �÷������ĵ������л�ȡ���ݡ�
   * @param evevt
   * @return com.faw_qm.doc.util.DocFormData
   * �޸ĺ󣬶��ļ��н���У�飬ʹ�ĵ����ܴ����ڸ��ļ����¡�
   */
  public SupplierFormData gainMessage(HttpServletRequest request)
          throws QMException
  {
	  if (VERBOSE) {
	      System.out.println("SupplierHTMLAction.gainMessage()  begin..... ");
	    }
	    SupplierFormData formData = null;
	    // try {
	    //  ����QMRouteFormData����Ŀ����Ϊ�˴�����Ϣ
	    formData = new SupplierFormData();
      
	       String codename = request.getParameter("textcodename");
		   String code = request.getParameter("textcode");
		   String jname = request.getParameter("textjname");
		   if (codename == null || codename.trim().length() == 0 ||
	                (codename.replace('��', ' ')).trim().length() == 0)
	            {
	            	// ��������20070622��ΪTD����736�޸������
	            	// ��V3R11�Ի���������JSPҳ���쳣���˴����Ժ���Ҫ�׳�DocException
	                throw new QMException("��Ӧ�����Ʋ���Ϊ�ջ�ȫ��Ϊ�ո�");
	            }
	        if (code == null || code.trim().length() == 0 ||
	                (code.replace('��', ' ')).trim().length() == 0)
	            {
	                throw new QMException("��Ӧ�̴��벻��Ϊ�ջ�ȫ��Ϊ�ո�");
	            }
	        if(jname == null || jname.trim().length() == 0 ||
	                (jname.replace('��', ' ')).trim().length() == 0)
	            {
	                throw new QMException("��Ӧ�̼�Ʋ���Ϊ�ջ�ȫ��Ϊ�ո�");
	            }
	        //�ж��Ƿ����ظ����
	        Collection coll = SupplierHelper.getSupplierInfo(code);
	        if(coll.size()>0){
	        	throw new QMException("��Ӧ�̱�����ظ�,������¼��");
	        }
		  
		   String people = request.getParameter("textpeople");
		 
		   String telephone = request.getParameter("texttelephone");
		   
//		  ���ù�Ӧ������
		   formData.setAttribute("codename",codename);
		   formData.setAttribute("code",code);
		   formData.setAttribute("people",people);
		   formData.setAttribute("jname",jname);
		   formData.setAttribute("telephone",telephone);
	    
	    

	    if (VERBOSE) {
	      System.out.println("SupplierHTMLAction.gainMessage()  end..... ");
	    }
	    //  ���ض���
	    return formData;


  }
  
  /**
   * �÷������ĵ������л�ȡ���ݡ�
   * @param evevt
   * @return com.faw_qm.doc.util.DocFormData
   * �޸ĺ󣬶��ļ��н���У�飬ʹ�ĵ����ܴ����ڸ��ļ����¡�
   */
  public SupplierFormData updateGainMessage(HttpServletRequest request)
          throws QMException
  {
	  if (VERBOSE) {
	      System.out.println("SupplierHTMLAction.gainMessage()  begin..... ");
	    }
	    SupplierFormData formData = null;
	    // try {
	    //  ����QMRouteFormData����Ŀ����Ϊ�˴�����Ϣ
	    formData = new SupplierFormData();
      
	       String bsoID = request.getParameter("bsoID");
	       System.out.println(" bsoID:" +bsoID);
	       String codename = request.getParameter("textcodename");
		   String code = request.getParameter("textcode");
		   String jname=request.getParameter("textjname");
		   if (codename == null || codename.trim().length() == 0 ||
	                (codename.replace('��', ' ')).trim().length() == 0)
	            {
	            	// ��������20070622��ΪTD����736�޸������
	            	// ��V3R11�Ի���������JSPҳ���쳣���˴����Ժ���Ҫ�׳�DocException
	                throw new QMException("��Ӧ�����Ʋ���Ϊ�ջ�ȫ��Ϊ�ո�");
	            }
	        if (code == null || code.trim().length() == 0 ||
	                (code.replace('��', ' ')).trim().length() == 0)
	            {
	                throw new QMException("��Ӧ�̴��벻��Ϊ�ջ�ȫ��Ϊ�ո�");
	            }
	        if (jname == null || jname.trim().length() == 0 ||
	                (jname.replace('��', ' ')).trim().length() == 0)
	            {
	                throw new QMException("��Ӧ�̼�Ʋ���Ϊ�ջ�ȫ��Ϊ�ո�");
	            }
	        //�ж��Ƿ����ظ����
	        Collection coll = SupplierHelper.getSupplierInfo(code);
	       
	        System.out.println(" coll.size:" +coll.size());
	        if(coll.size()>1){
	        	throw new QMException("�ù�Ӧ�̱����ϵͳ���Ѵ���,������¼��");
	        }
		   String people = request.getParameter("textpeople");
		   String address = request.getParameter("textaddress");
		   String telephone = request.getParameter("texttelephone");
//		  ���ù�Ӧ������
		   formData.setAttribute("bsoID",bsoID);
		   formData.setAttribute("codename",codename);
		   formData.setAttribute("code",code);
		   formData.setAttribute("people",people);
		   formData.setAttribute("jname",jname);
		   formData.setAttribute("telephone",telephone);
	    
	    

	    if (VERBOSE) {
	      System.out.println("SupplierHTMLAction.gainMessage()  end..... ");
	    }
	    //  ���ض���
	    return formData;


  }
  public SupplierFormData deleteGainMessage(HttpServletRequest request) throws QMException
  {
      if (VERBOSE)
      {
          System.out.println("SupplierHTMLAction.deleteGainMessage()  begin..... ");
      }
      SupplierFormData formData = null;
      //  �����ĵ�ֵ����
	  formData = new SupplierFormData();
	  //  �����ĵ���IDֵ
	  formData.setAttribute("bsoID", request.getParameter("bsoID"));
      if (VERBOSE)
      {
          System.out.println("SupplierHTMLAction.deleteGainMessage()  end..... ");
      }
      return formData;
  }

  public void doEnd(HttpServletRequest request, EventResponse eventResponse) {

    if (eventResponse != null) {
      if (eventResponse instanceof SupplierEventResponse) {
        //��ȡ�����ĵ���bsoID
         String SupplierBsoID;
        SupplierEventResponse reponse = (
        		SupplierEventResponse)
            eventResponse;
         SupplierBsoID = (String) reponse.getOperationSupperBsoID();
         if (SupplierBsoID != null)
         {
             //docBsoID = "Doc_0000";
             request.setAttribute("operationSupplierBsoID", SupplierBsoID);
         }
  
      }
    }
  }

 
}
