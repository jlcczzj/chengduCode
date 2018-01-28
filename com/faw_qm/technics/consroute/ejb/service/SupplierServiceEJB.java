/**
 * ��Ȩ��һ��������˾����
 * ��������һ��������˾��˽�л�Ҫ����
 * δ������˾��Ȩ�����÷Ƿ������͵���
 * ���ڱ���˾��Ȩ��Χ�ڣ�ʹ�ñ�����
 * ��������Ȩ��
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
 * <p>Title: ��Ӧ�̷���</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: һ������</p>
 * @author ����
 * @version 1.0
 */

public class SupplierServiceEJB
    extends BaseServiceImp {
  static boolean VERBOSE = true;
  /**
   * ��Դ��
   */
  private static final String RESOURCE = "com.faw_qm.doc.util.DocResource";
  static final long serialVersionUID = 1L;
  /**
   * ��÷�����
   * @return String ������
   */
  public String getServiceName() {
    return "SupplierService";
  }
  /**
   * �����ĵ�
   * @param formData �ĵ�������
   * @return DocInfo ����֮����ĵ�ֵ����
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
      
      //  ��Hashtable���ֵ���õ��ĵ���ֵ������
      SupplierInfo supplierInfo = formData.hashToAimInfo();
      SupplierInfo aimInfo1 = null;
      //  �ж϶����Ƿ�Ϊ��
      System.out.println(" supplierInfo:" +supplierInfo);
     
      
	      if (supplierInfo == null)
	      {
	          throw new QMException(RESOURCE, "101", null);
	          //������Ϣ:supplierInfo supplierInfo = formData.hashToDocInfo();����ֵΪ��
	      } //endif
	      else
	      {
	          //  ���ô����ĵ�����
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
    * ����SupplierFormData���¹�Ӧ��
    * @param SupplierFormData ��Ӧ�̱�����
    * @return SupplierInfo ����֮��Ĺ�Ӧ��
   * @throws DocException
   * @throws ServiceLocatorException
   * @throws QMException
   * <br>�����Ӧ��ֵ����Ϊ�գ����׳���"���� com.faw_qm.doc ����ϵͳ��Ϣ����: *"��
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
      //  ��ȡ�־û�����
      PersistService pService = (PersistService)
                                EJBServiceHelper.getService("PersistService");
      //����bsoID�õ���Ӧ��ֵ����
      SupplierInfo supplierInfo = (SupplierInfo) pService.refreshInfo((String) formData.
              getAttributes("bsoID"));
      //  ���ֵ����Ϊ�գ��׳��쳣
      if (supplierInfo == null)
      {
          Object[] obj =
                         {
                         "��Ӧ��Ϊ��!"};
          throw new QMException(RESOURCE, "0", obj);
          //������Ϣ:���� com.faw_qm.doc ����ϵͳ��Ϣ����:��Ӧ��Ϊ��
      } //endif
      else
      {
          //formData.hashToDocInfo();
          //  formData.hashToDocInfo();
          /**    ����Ϊ��ֵ��Ӧ������ֵ   */

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
              //������Ϣ:����������ֵʱ����
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
      //  ����ֵ����
      return supplierInfo1;
  } //end updateDoc(DocFormData)
  /**
   * ���¹�Ӧ��
   * @param  docInfo ��Ӧ��ֵ����
   * @return DocInfo ����֮��Ĺ�Ӧ��ֵ����
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
          //  ��ó־û�����
          PersistService persistService = (PersistService)
                                          EJBServiceHelper.getService(
                  "PersistService");
          //  ����Ӧ�̶���洢�����ݿ���
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
      //  ���ع�Ӧ�̶���
      return info;
  } //end updateDoc(SupplierInfo)

  /**
   *  ����DocFormDataɾ����Ӧ��
   *  @param  String id Ҫɾ����Ӧ�̵�BsoID
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
              //��ó־û�����
              persistService = (PersistService) EJBServiceHelper.getService(
                      "PersistService");
             
          }
          catch (QMException ex)
          {
              throw new QMException(ex);
          }


          //  ɾ����Ӧ��ֵ��������������ؼ�¼
          persistService.deleteBso(id);
       //end try
 

      if (VERBOSE)
      {
          System.out.println(
                  "SupplierServiceEJB deleteDoc(String id) end ... ");
      } //endif
  } //end deleteDoc(String)

  /**
   * ������Ӧ��
   * @param formData ��Ӧ�̱�����
   * @return SupplierInfo ����֮��Ĺ�Ӧ��ֵ����
   * @see SupplierFormData
   * @throws SupplierException
   * @throws ServiceLocatorException
   * @throws QMException
   */
 
  
  /**
   * ������Ӧ��
   * @param supplierInfo ��Ӧ��ֵ����
   * @return SupplierInfo ����֮��Ĺ�Ӧ��ֵ����
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
          
          //  ��ó־û�����
          PersistService persistService = (PersistService)
                                          EJBServiceHelper.getService(
                  "PersistService");
          //  ����Ӧ�̶���洢�����ݿ���
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
      //  ���ع�Ӧ��ֵ����
      return info;
  } //end createDoc(SupplierInfo)
  






      

      /**
       * ���ݹ�Ӧ���ļ����ѯ���ݿ�
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

          //  �����Ӧ�̱�Ų�Ϊ��
          if (folder != null && !folder.trim().equals("")) {
              if (folder.equals("\\"))
                  folder = "\\Root";
              if (folder.indexOf("\\Root") != 0) {
                  //����Root��ͷ
                  if (folder.indexOf("\\Root") == 0)
                      folder = "\\Root" + folder;
                  else
                      folder = "\\Root\\" + folder;
              }
              if (folder.lastIndexOf("\\") == folder.length() - 1)
                  folder = folder.substring(0, folder.length() - 1);

              if (query.getConditionCount() > 0)
                  query.addAND();
                  //  ����û���ѯ�Ĺ�Ӧ���в�������˹�Ӧ�̱��
              if (checkboxfolder.trim().equals("false")) {
                  QueryCondition cond = new QueryCondition("location", "=",
                      folder);
                  query.addCondition(0, cond);
              }
              //  ����û����ѯ�˹�Ӧ�̱��
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
       * ���ݹ�Ӧ�̴����߲�ѯ���ݿ�
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

          //  �����Ӧ�̱�Ų�Ϊ��
          if (textcreator != null && !textcreator.trim().equals("")) {
              if (query.getConditionCount() > 0)
                  query.addAND();
              int j = query.appendBso("User", false);
              QueryCondition qc1 = new QueryCondition("iterationCreator", "bsoID");
              query.addCondition(0, j, qc1);
              query.addAND();

              //  ����û���ѯ�Ĺ�Ӧ���в�������˹�Ӧ�̱��
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
       * ���ݹ�Ӧ�̴���ʱ���ѯ���ݿ�
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

          //  �����Ӧ�̱�Ų�Ϊ��
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
                  //����ȷ����ֹʱ��ָ���
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
                  //����ȷ����ֹʱ��ָ���
                  beginTimeStr = timeStr;
                  try {
                      dateHelperBegin = new DateHelper(beginTimeStr);
                  }
                  catch (Exception ex) {
                      throw new QMException(ex);
                  }
              }
              //�������ȷ����ֹʱ��ָ�������ȡ��ֹʱ�䣬
              //���û����ȷ����ֹʱ��ָ�������һ���ж��Ƿ������ʱ�䣨��2003-04-28 10:00:00.0��
              //�粻�ǣ��������Ӧ��ֹʱ�䡣
              //��ָ��������2003-04-28 10����
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
              //�Ƚ�����ʱ���С�������ʼʱ�������ֹʱ�䣬ʱ��Ҫ����
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
                  //  ����û���ѯ +
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
              //  ����û����ѯ -
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
   * ƥ���ַ���ѯ�������ַ���oldStr�е�"/*"ת����"*"��"*"ת����"%"��"%"������
    * �� "shf/*pdm%cax*"  ת���� "shf*pdm%cax%"
     * @param oldStr
     * @return ת�����ƥ���ַ���ѯ��
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
     * ����ʱ���ʽ�ַ�����ת����ָ����ʽ��ʱ���ַ���
     * @param date Ҫת����ʱ��
     * @param javaFormat ʱ���ʽ�ַ���
     * @return ��ָ����ʽת�����ʱ���ַ���
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
 	       * ���ݹ�Ӧ�̴����ѯ���ݿ�
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
 	            System.out.println("��������ǣ�" + query.getDebugSQL());
 	          }
 	          Collection col = service.findValueInfo(query);
 	          if (col != null && col.size() != 0) {
 	             
 	              for (Iterator iterator = col.iterator(); iterator.hasNext(); ) {
 	            	  
 	            	  linkinfo = (SupplierInfo) iterator.next();
 	            	 
 	              }
 	            }
 	          if (VERBOSE) {
 	            System.out.println("���ҵĽ���ǣ�" + col);
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
