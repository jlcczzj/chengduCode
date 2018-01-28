/**
 * 版权归一汽启明公司所有
 * 本程序属一汽启明公司的私有机要资料
 * 未经本公司授权，不得非法传播和盗用
 * 可在本公司授权范围内，使用本程序
 * 保留所有权利
 */

package com.faw_qm.technics.consroute.ejb.service;
 
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import com.faw_qm.query.*;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.BaseServiceImp;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.technics.consroute.model.SupplierInfo;
import com.faw_qm.technics.consroute.util.RouteHelper;
import com.faw_qm.technics.consroute.util.SupplierFormData;
import com.faw_qm.technics.route.ejb.service.TechnicsRouteService;
import com.faw_qm.technics.route.model.ListRoutePartLinkIfc;
import com.faw_qm.technics.route.model.TechnicsRouteListIfc;
import com.faw_qm.technics.route.util.RouteListLevelType;
import com.faw_qm.util.EJBServiceHelper;

import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.iba.value.ejb.entity.IBAHolder;
import com.faw_qm.iba.value.ejb.service.IBAValueObjectsFactory;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.definition.model.AbstractAttributeDefinitionIfc;
import com.faw_qm.iba.definition.model.StringDefinitionIfc;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.model.StringValueInfo;
import com.faw_qm.iba.value.util.AttributeContainer;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.StringValueDefaultView;
/**
 * <p>Title: 供应商服务</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: 一汽启明</p>
 * @author 刘扬
 * @version 1.0
 */

public class SupplierServiceEJB
    extends BaseServiceImp {
  static boolean VERBOSE = true;
  /**
   * 资源名
   */
  private static final String RESOURCE = "com.faw_qm.doc.util.DocResource";
  static final long serialVersionUID = 1L;
  /**
   * 获得服务名
   * @return String 服务名
   */
  public String getServiceName() {
    return "SupplierService";
  }
  /**
   * 创建文档
   * @param formData 文档表单数据
   * @return DocInfo 创建之后的文档值对象
   * @see DocFormData
   * @throws DocException
   * @throws ServiceLocatorException
   * @throws QMException
   */
  public SupplierInfo createsupplier(SupplierFormData formData)
          throws QMException,
          ServiceLocatorException, QMException
  {
      if (VERBOSE)
      {
          System.out.println(
                  "supplierServiceEJB createsupplier(DocFormData formData)" +
                  " begain ... DocFormData is" + formData);
      } //endif
      
      //  将Hashtable里的值设置到文档的值对象中
      SupplierInfo supplierInfo = formData.hashToAimInfo();
      SupplierInfo aimInfo1 = null;
      //  判断对象是否为空
      System.out.println(" supplierInfo:" +supplierInfo);
     
      
	      if (supplierInfo == null)
	      {
	          throw new QMException(RESOURCE, "101", null);
	          //错误信息:supplierInfo supplierInfo = formData.hashToDocInfo();返回值为空
	      } //endif
	      else
	      {
	          //  调用创建文档方法
	    	  aimInfo1 = createsupplier(supplierInfo);
	          if (VERBOSE)
	          {
	              System.out.println(" aimInfo1 aimInfo1:" +aimInfo1);
	
	          }
	          
	          if (VERBOSE)
	          {
	              System.out.println("supplierServiceEJB createsupplier(DocFormData" +
	                                 " formData) end ... return is" + aimInfo1);
	          } //endif
	         
	      } //end else
		
      return aimInfo1;
  } //end createSupplier(SupplierFormData formData)
  
  /**
    * 根据SupplierFormData更新供应商
    * @param SupplierFormData 供应商表单数据
    * @return SupplierInfo 更新之后的供应商
   * @throws DocException
   * @throws ServiceLocatorException
   * @throws QMException
   * <br>如果供应商值对象为空，则抛出："出现 com.faw_qm.doc 错误。系统信息如下: *"。
   */
  public SupplierInfo updateSupplier(SupplierFormData formData)
          throws QMException,
          ServiceLocatorException
  {
      if (VERBOSE)
      {
          System.out.println(
                  "SupplierService updateSupplier(SupplierFormData formData)" +
                  " begain ... DocFormData is" + formData);
      } //endif
      SupplierInfo supplierInfo1 = null;
      //  获取持久化服务
      PersistService pService = (PersistService)
                                EJBServiceHelper.getService("PersistService");
      //根据bsoID得到供应商值对象
      SupplierInfo supplierInfo = (SupplierInfo) pService.refreshInfo((String) formData.
              getAttributes("bsoID"));
      //  如果值对象为空，抛出异常
      if (supplierInfo == null)
      {
          Object[] obj =
                         {
                         "供应商为空!"};
          throw new QMException(RESOURCE, "0", obj);
          //错误信息:出现 com.faw_qm.doc 错误。系统信息如下:供应商为空
      } //endif
      else
      {
          //formData.hashToDocInfo();
          //  formData.hashToDocInfo();
          /**    以下为设值供应商属性值   */

          try
          {

          
        	  supplierInfo.setCode((String) formData.getAttributes(
                      "code"));

             
        	  supplierInfo.setCodename((String) formData.getAttributes(
                      "codename"));

             
       
        	  supplierInfo.setJName((String) formData.getAttributes(
                      "jname"));
//        	
        	  supplierInfo.setTelephone((String) formData.getAttributes(
                      "telephone"));
//        	
        	  supplierInfo.setPeople((String) formData.getAttributes(
                      "people"));

          } //end try
          catch (Exception e)
          {
              throw new QMException(e, RESOURCE, "34", null);
              //错误信息:设置属性数值时出错
          } //end catch
          if (VERBOSE)
          {
              System.out.println("[ghy] SupplierInfo DocName: ++" +
            		  supplierInfo);
          }
          supplierInfo1 = updateSupplier(supplierInfo);
 
      } //end else
      if (VERBOSE)
      {
          System.out.println(
                  "SupplierServiceEJB updateDoc(DocFormData formData)" +
                  " end ... return is" + supplierInfo1);
      } //endif
      //  返回值对象
      return supplierInfo1;
  } //end updateDoc(DocFormData)
  /**
   * 更新供应商
   * @param  docInfo 供应商值对象
   * @return DocInfo 更新之后的供应商值对象
   * @see DocInfo
   * @exception DocException
   */
  public SupplierInfo updateSupplier(SupplierInfo supplierInfo)
          throws QMException
  {

      if (VERBOSE)
      {
          System.out.println("SupplierServiceEJB updateDoc(DocInfo docInfo)" +
                             " begain ... DocInfo is" + supplierInfo);
      } //endif
      if (supplierInfo == null)
      {
          return null;
      }
      SupplierInfo info;
      
      try
      {
          //  获得持久化服务
          PersistService persistService = (PersistService)
                                          EJBServiceHelper.getService(
                  "PersistService");
          //  将供应商对象存储到数据库中
          info = (SupplierInfo) persistService.updateValueInfo(supplierInfo);
      } //end try
      catch (Exception e)
      {
          throw new QMException(e);
      } //end catch
      if (VERBOSE)
      {
          System.out.println(
                  "SupplierServiceEJB updateDoc(SupplierInfo SupplierInfo) end" +
                  " ... return is" + info);
      } //endif
      //  返回供应商对象
      return info;
  } //end updateDoc(SupplierInfo)

  /**
   *  根据DocFormData删除供应商
   *  @param  String id 要删除供应商的BsoID
   *  @throws DocException
   */
  public void deleteSupplier(String id)
          throws QMException
  {
      if (VERBOSE)
      {
          System.out.println(
                  "SupplierServiceEJB deleteDoc(String id) begain ... String is" +
                  id);
      } //end if
    

          PersistService persistService = null;
          try
          {
              //获得持久化服务
              persistService = (PersistService) EJBServiceHelper.getService(
                      "PersistService");
             
          }
          catch (QMException ex)
          {
              throw new QMException(ex);
          }


          //  删除供应商值对象及其关联类的相关纪录
          persistService.deleteBso(id);
       //end try
 

      if (VERBOSE)
      {
          System.out.println(
                  "SupplierServiceEJB deleteDoc(String id) end ... ");
      } //endif
  } //end deleteDoc(String)

  /**
   * 创建供应商
   * @param formData 供应商表单数据
   * @return SupplierInfo 创建之后的供应商值对象
   * @see SupplierFormData
   * @throws SupplierException
   * @throws ServiceLocatorException
   * @throws QMException
   */
 
  
  /**
   * 创建供应商
   * @param supplierInfo 供应商值对象
   * @return SupplierInfo 创建之后的供应商值对象
   * @see SupplierInfo
   * @throws SupplierException
   */
  public SupplierInfo createsupplier(SupplierInfo supplierInfo)
          throws QMException
  {
      if (VERBOSE)
      {
          System.out.println("SupplierServiceEJB createAim(supplierInfo SupplierInfo)" +
                             " begain ... supplierInfo is = " + supplierInfo);
      } //end if
      SupplierInfo info = null;
      try
      {
          
          //  获得持久化服务
          PersistService persistService = (PersistService)
                                          EJBServiceHelper.getService(
                  "PersistService");
          //  将供应商对象存储到数据库中
//          System.out.println("SupplierInfo.getcode="+supplierInfo.getAddress());
//          System.out.println("SupplierInfo.getcode="+supplierInfo.getBsoName());
//          System.out.println("SupplierInfo.getcode="+SupplierInfo.getCode());
//          System.out.println("SupplierInfo.getcode="+SupplierInfo.getCodename());
//          System.out.println("SupplierInfo.getcode="+SupplierInfo.getPeople());
//          System.out.println("SupplierInfo.getcode="+SupplierInfo.getTelephone());
       
          info = (SupplierInfo) persistService.storeValueInfo(supplierInfo);
      } //end try
      catch (QMException e)
      {
          //////sessionContext.setRollbackOnly();
          throw new QMException(e);
      } //end catch
      if (VERBOSE)
      {
          System.out.println("SupplierServiceEJB createAim(supplierInfo supplierInfo)" +
                             " end ... return is" + info);
      } //endif
      //  返回供应商值对象
      return info;
  } //end createDoc(SupplierInfo)
  






      

      /**
       * 根据供应商文件柜查询数据库
       * @param  QMQuery query1,String folder, String checkboxfolder
       * @return QMQuery
       * @exception Exception
       */
      public QMQuery getFindPartInfoByFolder(QMQuery query1, String folder,
                                             String checkboxfolder) throws
          Exception {
          if (VERBOSE) {
              System.out.println("getFindSupplierInfoByFolder(QMQuery query1,String folder, String checkboxfolder) begain");
              System.out.println("folder =" + folder + " checkboxfolder=" +
                                 checkboxfolder);
          } //endif
          QMQuery query = query1;

          //  如果供应商编号不为空
          if (folder != null && !folder.trim().equals("")) {
              if (folder.equals("\\"))
                  folder = "\\Root";
              if (folder.indexOf("\\Root") != 0) {
                  //不以Root开头
                  if (folder.indexOf("\\Root") == 0)
                      folder = "\\Root" + folder;
                  else
                      folder = "\\Root\\" + folder;
              }
              if (folder.lastIndexOf("\\") == folder.length() - 1)
                  folder = folder.substring(0, folder.length() - 1);

              if (query.getConditionCount() > 0)
                  query.addAND();
                  //  如果用户查询的供应商中不想包括此供应商编号
              if (checkboxfolder.trim().equals("false")) {
                  QueryCondition cond = new QueryCondition("location", "=",
                      folder);
                  query.addCondition(0, cond);
              }
              //  如果用户想查询此供应商编号
              else {
                  QueryCondition cond = new QueryCondition("location", "<>",
                      folder);
                  query.addCondition(0, cond);
              }
          }
          if (VERBOSE) {
              System.out.println("getFindSupplierInfoByFolder(QMQuery query1,String folder, String checkboxfolder) end");

          } //endif
          return query;
      }


      /**
       * 根据供应商创建者查询数据库
       * @param  QMQuery query1,String textcreator,String checkboxcreator
       * @return QMQuery
       * @exception Exception
       */
      public QMQuery getFindPartInfoByCreator(QMQuery query1, String textcreator,
                                              String checkboxcreator) throws
          Exception {
          if (VERBOSE) {
              System.out.println("getFindSupplierInfoByCreator(QMQuery query1,String textcreator,String checkboxcreator) begain");
              System.out.println("textcreator =" + textcreator +
                                 " checkboxcreator=" + checkboxcreator);
          } //endif
          QMQuery query = query1;

          //  如果供应商编号不为空
          if (textcreator != null && !textcreator.trim().equals("")) {
              if (query.getConditionCount() > 0)
                  query.addAND();
              int j = query.appendBso("User", false);
              QueryCondition qc1 = new QueryCondition("iterationCreator", "bsoID");
              query.addCondition(0, j, qc1);
              query.addAND();

              //  如果用户查询的供应商中不想包括此供应商编号
              if (checkboxcreator != null &&
                  checkboxcreator.trim().equals("false")) {
                  //modify by ShangHaiFeng 2003.12.15
                  query.addLeftParentheses();
                  QueryCondition cond = new QueryCondition("usersName", "like",
                      getLikeSearchString(textcreator));
                  query.addCondition(j, cond);
                  QueryCondition cond2 = new QueryCondition("usersDesc", "like",
                      getLikeSearchString(textcreator));
                  query.addOR();
                  query.addCondition(j, cond2);
                  query.addRightParentheses();
              }
              else {

                  query.addLeftParentheses();
                  QueryCondition cond = new QueryCondition("usersName",
                      "not like", getLikeSearchString(textcreator));
                  query.addCondition(j, cond);
                  QueryCondition cond2 = new QueryCondition("usersDesc",
                      "not like", getLikeSearchString(textcreator));
                  query.addAND();
                  query.addCondition(j, cond2);
                  query.addRightParentheses();

              }
          }
          if (VERBOSE) {
              System.out.println("getFindSupplierInfoByCreator(QMQuery query1,String textcreator,String checkboxcreator)  end");
          } //endif
          return query;
      }


      /**
       * 根据供应商创建时间查询数据库
       * @param  QMQuery query1,String textcreator,String checkboxcreator
       * @return QMQuery
       * @exception Exception
       */
      public QMQuery getFindPartInfoByTime(QMQuery query1, String textcreateTime,
                                           String checkboxcreateTime,
                                           String timeType) throws
                                           QMException {
          if (VERBOSE) {
              System.out.println("getFindSupplierInfoByCreator(QMQuery query1,String textcreator,String checkboxcreator) begain");
              System.out.println("textCreateTime =" + textcreateTime +
                                 " checkboxcreateTime=" +
                                 checkboxcreateTime);
          } //endif
          QMQuery query = query1;

          //  如果供应商编号不为空
          if (textcreateTime != null && !textcreateTime.trim().equals("")) {
              String javaFormat = "yyyy:MM:dd" + ':' + "HH:mm:ss";
              String oracleFormat = "YYYY:MM:DD:HH24:MI:SS";
              String toDateStr = "TO_DATE";
              String timeStr = textcreateTime;
              boolean betweenFlag = false;
              String beginTimeStr = "";
              String endTimeStr = "";
              String beginFormatTimeStr = null;
              String endFormatTimeStr = null;
              DateHelper dateHelperBegin = null;
              DateHelper dateHelperEnd = null;
              Timestamp beginTime = null;
              Timestamp endTime = null;
              int i = timeStr.indexOf(";");
              if (i > -1) {
                  //有明确的起止时间分隔符
                  beginTimeStr = timeStr.substring(0, i);
                  endTimeStr = timeStr.substring(i + 1);
                  try {
                      if (!beginTimeStr.trim().equals(""))
                          dateHelperBegin = new DateHelper(beginTimeStr);
                      if (!endTimeStr.trim().equals(""))
                          dateHelperEnd = new DateHelper(endTimeStr);
                  }
                  catch (Exception ex) {
                      throw new QMException(ex);
                  }
                  //System.out.println("beginTime ="+beginTime);
                  //System.out.println("endTime   ="+endTime);
              }
              else {
                  //无明确的起止时间分隔符
                  beginTimeStr = timeStr;
                  try {
                      dateHelperBegin = new DateHelper(beginTimeStr);
                  }
                  catch (Exception ex) {
                      throw new QMException(ex);
                  }
              }
              //如果有明确的起止时间分隔符，获取起止时间，
              //如果没有明确的起止时间分隔符，进一步判断是否是完成时间（如2003-04-28 10:00:00.0）
              //如不是，则算出对应起止时间。
              //如指定搜索“2003-04-28 10”，
              //begin: 2003-04-28 10:00:00
              //end:   2003-04-28 10:59:59

              if (dateHelperBegin != null && dateHelperEnd != null) {
                  betweenFlag = true;
                  beginTime = new Timestamp(dateHelperBegin.getDate().getTime());
                  endTime = new Timestamp(dateHelperEnd.getDate().getTime());
              }
              else if (dateHelperBegin != null && dateHelperEnd == null) {
                  if (dateHelperBegin.fullDate()) {
                      betweenFlag = false;
                      beginTime = new Timestamp(dateHelperBegin.getDate().getTime());
                      endTime = null;
                  }
                  else {
                      betweenFlag = true;
                      beginTime = new Timestamp(dateHelperBegin.instantOfDate(
                          DateHelper.
                          firstInstant).getTime());
                      endTime = new Timestamp(dateHelperBegin.instantOfDate(
                          DateHelper.
                          lastInstant).getTime());
                  }
              }
              else if (dateHelperBegin == null && dateHelperEnd != null) {
                  if (dateHelperEnd.fullDate()) {
                      betweenFlag = false;
                      beginTime = new Timestamp(dateHelperEnd.getDate().getTime());
                      endTime = null;
                  }
                  else {
                      betweenFlag = true;
                      beginTime = new Timestamp(dateHelperEnd.instantOfDate(
                          DateHelper.
                          firstInstant).getTime());
                      endTime = new Timestamp(dateHelperEnd.instantOfDate(
                          DateHelper.
                          lastInstant).getTime());
                  }
              }
              //比较两个时间大小，如果开始时间大于终止时间，时间要调换
              if (beginTime != null && endTime != null) {
                  if (beginTime.compareTo(endTime) > 0) {
                      Timestamp temp = beginTime;
                      beginTime = endTime;
                      endTime = temp;
                  }
              }

              if (beginTime != null)
                  beginFormatTimeStr = getDateString(beginTime, javaFormat);
              if (endTime != null)
                  endFormatTimeStr = getDateString(endTime, javaFormat);

              if (VERBOSE) {
                  if (betweenFlag) {
                      System.out.println("beginTime =" + beginTime);
                      System.out.println("endTime   =" + endTime);
                      System.out.println("beginFormatTimeStr =" +
                                         beginFormatTimeStr);
                      System.out.println("endFormatTimeStr   =" +
                                         endFormatTimeStr);
                  }
                  else {
                      System.out.println("beginFormatTimeStr =" +
                                         beginFormatTimeStr);
                  }
              }

              if (beginFormatTimeStr == null)
                  return query;
              if (query.getConditionCount() > 0)
                  query.addAND();
                  //  如果用户查询 +
              if (checkboxcreateTime != null &&
                  checkboxcreateTime.trim().equals("false")) {
                  try {
                      if (betweenFlag) {
                          query.addLeftParentheses();
                          //QueryCondition cond = new QueryCondition(timeType, ">=",toDateStr + "(" + beginFormatTimeStr + "," + oracleFormat + ")");
                          QueryCondition cond = new QueryCondition(timeType, ">=",
                              beginTime);
                          query.addCondition(0, cond);
                          query.addAND();
                          //cond = new QueryCondition(timeType, "<=",toDateStr + "(" + endFormatTimeStr + "," + oracleFormat + ")");
                          cond = new QueryCondition(timeType, "<=", endTime);
                          query.addCondition(0, cond);
                          query.addRightParentheses();
                      }
                      else {
                          //QueryCondition cond = new QueryCondition(timeType, "=",toDateStr + "(" + beginFormatTimeStr + "," + oracleFormat + ")");
                          QueryCondition cond = new QueryCondition(timeType, "=",
                              beginTime);
                          query.addCondition(0, cond);
                      }
                  }
                  catch (Exception ex) {
                      throw new QMException(ex);
                  }
              }
              //  如果用户想查询 -
              else {
                  try {
                      if (betweenFlag) {
                          query.addLeftParentheses();
                          //QueryCondition cond = new QueryCondition(timeType, "<=",toDateStr + "(" + beginFormatTimeStr + "," + oracleFormat + ")");
                          QueryCondition cond = new QueryCondition(timeType, "<=",
                              beginTime);
                          query.addCondition(0, cond);
                          query.addOR();
                          //cond = new QueryCondition(timeType, ">=",toDateStr + "(" + endFormatTimeStr + "," +oracleFormat + ")");
                          cond = new QueryCondition(timeType, ">=", endTime);
                          query.addCondition(0, cond);
                          query.addRightParentheses();
                      }
                      else {
                          //QueryCondition cond = new QueryCondition(timeType, "<>",toDateStr + "(" + beginFormatTimeStr + "," + oracleFormat + ")");
                          QueryCondition cond = new QueryCondition(timeType, "<>",
                              beginTime);
                          query.addCondition(0, cond);
                      }
                  }
                  catch (Exception ex) {
                      throw new QMException(ex);
                  }
              }

          }
          if (VERBOSE) {
              System.out.println("getFindSupplierInfoByCreator(QMQuery query1,String textcreator,String checkboxcreator)  end");
          } //endif
          return query;
     
  }

  /**
   * 匹配字符查询处理。将字符串oldStr中的"/*"转化成"*"，"*"转化成"%"，"%"不处理。
    * 例 "shf/*pdm%cax*"  转化成 "shf*pdm%cax%"
     * @param oldStr
     * @return 转化后的匹配字符查询串
     */
    private String getLikeSearchString(String oldStr) {
        if (oldStr == null || oldStr.trim().equals(""))
            return oldStr;
        char ac[] = oldStr.toCharArray();
        int i = ac.length;
        for (int j = 0; j < i; j++) {
            if (ac[j] == '*')
                if (j > 0 && ac[j - 1] == '/') {
                    for (int k = j - 1; k < i - 1; k++)
                        ac[k] = ac[k + 1];
                    i--;
                    ac[i] = ' ';
                }
                else {
                    ac[j] = '%';
                }
            //modify by ShangHaiFeng 2003.12.15
            if (ac[j] == '?')
                ac[j] = '_';
        }

        String resultStr = (new String(ac)).trim();
        return resultStr;
    }
    /**
     * 根据时间格式字符串，转化成指定格式的时间字符串
     * @param date 要转化的时间
     * @param javaFormat 时间格式字符串
     * @return 按指定格式转化后的时间字符串
     * @throws AdoptNoticeException
     */
    private String getDateString(Date date, String javaFormat) throws
    QMException {
        if (VERBOSE)
            System.out.println("SupplierServiceEJB.getDateString(Date date, String javaFormat) begin...");
        String resultStr = null;
        SimpleDateFormat simpledateformat = new SimpleDateFormat(javaFormat);
        resultStr = simpledateformat.format(date);
        if (VERBOSE)
            System.out.println("SupplierServiceEJB.getDateString(Date date, String javaFormat) end... resultStr ="
                               + resultStr);
        return resultStr;
    }
    


    	   /**
 	       * 根据供应商代码查询数据库
 	       * @param  QMQuery query1,String projectteam,String checkboxprojTeam
 	       * @return QMQuery
 	       * @exception Exception
 	       */
 	      public SupplierInfo findSupplierByCode(String code,String name, String people,String address,String telephone) throws
 	          Exception {
 	          if (VERBOSE) {
 	              System.out.println("findSupplierByCode(String partID) begain code= "+code);
 	              
 	          } //endif
 	         SupplierInfo linkinfo = null;
 	          QMQuery query = new QMQuery("Supplier");
 	          query.addCondition(new QueryCondition("code", "=", code));

 	          PersistService service = (PersistService)
 	              EJBServiceHelper.getService(
 	              "PersistService");
 	          if (VERBOSE) {
 	            System.out.println("查找语句是：" + query.getDebugSQL());
 	          }
 	          Collection col = service.findValueInfo(query);
 	          if (col != null && col.size() != 0) {
 	             
 	              for (Iterator iterator = col.iterator(); iterator.hasNext(); ) {
 	            	  
 	            	  linkinfo = (SupplierInfo) iterator.next();
 	            	 
 	              }
 	            }
 	          if (VERBOSE) {
 	            System.out.println("查找的结果是：" + col);
 	          }
 	         
 	         
 	          if (VERBOSE) {
 	              System.out.println("findSupplierByCode( end");

 	          } //endif
 	          return linkinfo;
 	      }
 	     public Vector findSupplier(String code, String codename, boolean codeFlag, boolean nameFlag)throws QMException
 	    {
 	        PersistService pservice = (PersistService)EJBServiceHelper.getPersistService();
 	        Vector result1 = null;
 	        try
 	        {
 	            QMQuery query = new QMQuery("Supplier");
 	            query.setChildQuery(false);
 	            if(code != null && code.trim().length() != 0)
 	            {
 	                QueryCondition cond = RouteHelper.handleWildcard("code", code, codeFlag);
 	                query.addCondition(cond);
 	            }
 	            if(codename != null && codename.trim().length() != 0)
 	            {
 	                QueryCondition cond1 = RouteHelper.handleWildcard("codename", codename, nameFlag);
 	                query.addAND();
 	                query.addCondition(cond1);
 	            }
 	            result1 = (Vector)pservice.findValueInfo(query, false);	       
 	        }catch(Exception e)
 	        {
 	            e.printStackTrace();
 	        }
 	        return result1;
 	    }
 		    
 }
