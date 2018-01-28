/** 生成程序QMRouteHTMLAction.java	2007/05/29
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
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
 * 供应商HTMLAction，获得html客户信息，进行封装
 * <p>Copyright: Copyright (c) 2010</p>
 * <p>Company: 启明公司</p>
 * @author 刘家坤
 * @version 1.0
 *
 */
public class SupplierHTMLAction
    extends HTMLActionSupport {
  //动作
  protected final static String ACTION = "action";

  //添加更改对象动作
  protected final static String ADD_ACTION = "add";

  //更新更改对象动作
  protected final static String UPDATE_ACTION = "update";

  //删除更改对象动作
  protected final static String DELETE_ACTION = "delete";


  // 请求动作类型
  protected String actionType;

  // 包含请求流信息。
  protected HashMap requestMap;


//request解析后的数据
  boolean VERBOSE = true;

  public SupplierHTMLAction() {

  }

  /**
   * 该方法执行供应商请求。
   */
  public Event perform(HttpServletRequest request) throws HTMLActionException,
      HTMLActionException, QMException {
    if (VERBOSE) {
      System.out.println("SupplierHTMLAction.perform() begin......");
      //  webaction和ejbaction之间的过渡类
    }
    SupplierEvent docEvent = null;

    //  为了获取值对象信息的类
    SupplierFormData formData = null;

    // action动作
    String actionType = "";
   
    actionType = (String) request.getParameter("action");
 
    if (VERBOSE) {
      System.out.println("表单动作为 =" + actionType);

      //  如果action动作为空则返回空
    }
    if (actionType == null || actionType.equals("")) {
      return null;
    }

    //  如果为“新建”动作
    if (actionType.equals("add")) {
      formData = gainMessage(request);
      docEvent = new SupplierEvent(SupplierEvent.ADD, formData);
    }
    //  如果为“更新”动作
    if (actionType.equals("update"))
    {
        formData = updateGainMessage(request);
        docEvent = new SupplierEvent(SupplierEvent.UPDATE, formData);
    }

    //  如果为“删除”动作
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
   * 该方法从文档请求中获取数据。
   * @param evevt
   * @return com.faw_qm.doc.util.DocFormData
   * 修改后，对文件夹进行校验，使文档不能创建在根文件夹下。
   */
  public SupplierFormData gainMessage(HttpServletRequest request)
          throws QMException
  {
	  if (VERBOSE) {
	      System.out.println("SupplierHTMLAction.gainMessage()  begin..... ");
	    }
	    SupplierFormData formData = null;
	    // try {
	    //  创建QMRouteFormData对象，目的是为了传递信息
	    formData = new SupplierFormData();
      
	       String codename = request.getParameter("textcodename");
		   String code = request.getParameter("textcode");
		   String jname = request.getParameter("textjname");
		   if (codename == null || codename.trim().length() == 0 ||
	                (codename.replace('　', ' ')).trim().length() == 0)
	            {
	            	// 王健旭于20070622因为TD问题736修改了这里，
	            	// 在V3R11对环境的整个JSP页面异常做了处理以后，需要抛出DocException
	                throw new QMException("供应商名称不能为空或全部为空格");
	            }
	        if (code == null || code.trim().length() == 0 ||
	                (code.replace('　', ' ')).trim().length() == 0)
	            {
	                throw new QMException("供应商代码不能为空或全部为空格");
	            }
	        if(jname == null || jname.trim().length() == 0 ||
	                (jname.replace('　', ' ')).trim().length() == 0)
	            {
	                throw new QMException("供应商简称不能为空或全部为空格");
	            }
	        //判断是否有重复编号
	        Collection coll = SupplierHelper.getSupplierInfo(code);
	        if(coll.size()>0){
	        	throw new QMException("供应商编号有重复,请重新录入");
	        }
		  
		   String people = request.getParameter("textpeople");
		 
		   String telephone = request.getParameter("texttelephone");
		   
//		  设置供应商属性
		   formData.setAttribute("codename",codename);
		   formData.setAttribute("code",code);
		   formData.setAttribute("people",people);
		   formData.setAttribute("jname",jname);
		   formData.setAttribute("telephone",telephone);
	    
	    

	    if (VERBOSE) {
	      System.out.println("SupplierHTMLAction.gainMessage()  end..... ");
	    }
	    //  返回对象
	    return formData;


  }
  
  /**
   * 该方法从文档请求中获取数据。
   * @param evevt
   * @return com.faw_qm.doc.util.DocFormData
   * 修改后，对文件夹进行校验，使文档不能创建在根文件夹下。
   */
  public SupplierFormData updateGainMessage(HttpServletRequest request)
          throws QMException
  {
	  if (VERBOSE) {
	      System.out.println("SupplierHTMLAction.gainMessage()  begin..... ");
	    }
	    SupplierFormData formData = null;
	    // try {
	    //  创建QMRouteFormData对象，目的是为了传递信息
	    formData = new SupplierFormData();
      
	       String bsoID = request.getParameter("bsoID");
	       System.out.println(" bsoID:" +bsoID);
	       String codename = request.getParameter("textcodename");
		   String code = request.getParameter("textcode");
		   String jname=request.getParameter("textjname");
		   if (codename == null || codename.trim().length() == 0 ||
	                (codename.replace('　', ' ')).trim().length() == 0)
	            {
	            	// 王健旭于20070622因为TD问题736修改了这里，
	            	// 在V3R11对环境的整个JSP页面异常做了处理以后，需要抛出DocException
	                throw new QMException("供应商名称不能为空或全部为空格");
	            }
	        if (code == null || code.trim().length() == 0 ||
	                (code.replace('　', ' ')).trim().length() == 0)
	            {
	                throw new QMException("供应商代码不能为空或全部为空格");
	            }
	        if (jname == null || jname.trim().length() == 0 ||
	                (jname.replace('　', ' ')).trim().length() == 0)
	            {
	                throw new QMException("供应商简称不能为空或全部为空格");
	            }
	        //判断是否有重复编号
	        Collection coll = SupplierHelper.getSupplierInfo(code);
	       
	        System.out.println(" coll.size:" +coll.size());
	        if(coll.size()>1){
	        	throw new QMException("该供应商编号在系统中已存在,请重新录入");
	        }
		   String people = request.getParameter("textpeople");
		   String address = request.getParameter("textaddress");
		   String telephone = request.getParameter("texttelephone");
//		  设置供应商属性
		   formData.setAttribute("bsoID",bsoID);
		   formData.setAttribute("codename",codename);
		   formData.setAttribute("code",code);
		   formData.setAttribute("people",people);
		   formData.setAttribute("jname",jname);
		   formData.setAttribute("telephone",telephone);
	    
	    

	    if (VERBOSE) {
	      System.out.println("SupplierHTMLAction.gainMessage()  end..... ");
	    }
	    //  返回对象
	    return formData;


  }
  public SupplierFormData deleteGainMessage(HttpServletRequest request) throws QMException
  {
      if (VERBOSE)
      {
          System.out.println("SupplierHTMLAction.deleteGainMessage()  begin..... ");
      }
      SupplierFormData formData = null;
      //  创建文档值对象
	  formData = new SupplierFormData();
	  //  设置文档的ID值
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
        //获取操作文档的bsoID
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
