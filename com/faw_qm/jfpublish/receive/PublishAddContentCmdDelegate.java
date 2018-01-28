package com.faw_qm.jfpublish.receive;

/**
 * �����������ݵĴ�����
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author ������
 * @version 1.0
 * SS1 ����ļ������������߼� jiahx 2013-12-10
 * SS2 ȥ���ļ������пո񣬷�����ӻ��������⡣ liunan 2014-3-21
 * SS3 ����windchill����10.2��������epm�������ļ�������part�ϡ� liunan 2015-8-21
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import com.faw_qm.adapter.BaseCommandDelegate;
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentHolderIfc;
import com.faw_qm.content.model.ContentItemIfc;
import com.faw_qm.content.model.FormatContentHolderIfc;
import com.faw_qm.content.model.URLDataInfo;
import com.faw_qm.content.util.ContentClientHelper;
import com.faw_qm.content.util.StreamUtil;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.domain.ejb.service.DomainService;
import com.faw_qm.epm.build.model.EPMBuildLinksRuleInfo;
import com.faw_qm.epm.epmdocument.model.EPMDocumentIfc;
import com.faw_qm.epm.epmdocument.model.EPMDocumentInfo;
import com.faw_qm.epm.epmdocument.model.EPMDocumentMasterIfc;
import com.faw_qm.epm.epmdocument.util.EPMApplicationType;
import com.faw_qm.epm.epmdocument.util.EPMAuthoringAppType;
import com.faw_qm.epm.epmdocument.util.EPMDocumentType;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.integration.model.Command;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.integration.model.Script;
import com.faw_qm.integration.model.object.ContentHeader;
import com.faw_qm.integration.model.object.InteMultipartInputStream;
import com.faw_qm.integration.util.QMProperties;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.viewmarkup.model.DerivedImageInfo;
import com.faw_qm.viewmarkup.model.GraphicallyRepresentableIfc;
import com.faw_qm.viewmarkup.model.SourceToImageIfc;
import com.faw_qm.viewmarkup.model.SourceToImageInfo;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;

//CCBegin by liunan 2008-08-07
import com.faw_qm.content.model.ContentItemIfc;
//CCEnd by liunan 2008-08-07

public class PublishAddContentCmdDelegate
    extends BaseCommandDelegate {
  //public PublishPartsLog logger = null;
  
  private String userName = null;

  private String pass = null;

  private static boolean LOG = true;

  private static boolean DEBUG = false;

  private static SimpleDateFormat logDateFormat = new SimpleDateFormat(
      "yyyy/MM/dd-HH:mm:ss.SSS");

  private static String _DEFAULT_USER = "anonymous";

  private static String _DEFAULT_PASS = "anonymous";

  private static String _DEFAULT_TMPFILE_PATH = "c:/PhosphorPDM/temp";

  private static long _DEFAULT_FILESIZE_LIMIT = 5242880L;

  private String default_user;

  private String default_pass;

  private String fileUploadSave = null;

  private long fileSizeLimit = 0L;

  // �����ļ�ת���б��ļ�Ŀ¼
  private String cmlFilePath = null;

  private static final String RESOURCE = "com.faw_qm.adapter.AdapterResource";

  private boolean createLink = true;

  private DerivedImageInfo dimage = null;

  private boolean isimage = false;
  
  //CCBegin SS1
  static boolean fileVaultUsed = (RemoteProperty.getProperty(
          "registryFileVaultStoreMode", "true")).equals("true");
  //CCEnd SS1

  private void log(Object msg) {
    PublishPartsLog.log(msg);
  }

  private void errorLog(Object msg) {
    if (msg instanceof Throwable) {
      PublishPartsLog.log( (Throwable) msg);
    }
    else {
      PublishPartsLog.log("*****ERROR: " + msg);
    }
  }

  //private void userLog(Object obj) {
    //PublishPartsLog.userLog(obj);
  //}

  public PublishAddContentCmdDelegate() {
    try {
      QMProperties props = QMProperties.getLocalProperties();
      LOG = props.getProperty("com.faw_qm.PublishParts.log", true);
      DEBUG = props.getProperty("com.faw_qm.PublishParts.debug", false);
      default_user = props.getProperty(
          "com.faw_qm.PublishParts.DEFAULT_USER", _DEFAULT_USER);
      default_pass = props.getProperty(
          "com.faw_qm.PublishParts.DEFAULT_PASS", _DEFAULT_PASS);
      cmlFilePath = props.getProperty(
          "com.faw_qm.PublishParts.DEFAULT_CHANGEMIDDLELIST_PATH",
          _DEFAULT_TMPFILE_PATH);
      fileUploadSave = props.getProperty(
          "com.faw_qm.PublishParts.DEFAULT_TMPFILE_PATH",
          _DEFAULT_TMPFILE_PATH);
      String fileSizeLimitStr = props.getProperty(
          "com.faw_qm.PublishParts.DEFAULT_FILESIZE_LIMIT", Long
          .toString(_DEFAULT_FILESIZE_LIMIT));
      fileSizeLimit = Long.parseLong(fileSizeLimitStr);
      createLink = props.getProperty("com.faw_qm.adapter.createlink",
                                     true);
    }
    catch (Exception ex) {
      LOG = true;
      fileSizeLimit = _DEFAULT_FILESIZE_LIMIT;
    }
  }

  public Script _invoke(Script script) throws QMException {
    log("************************* ���δ���ʼ**************************");
    //liunan 2009-09-04 
    System.out.println("PublishAddContentCmdDelegate is begin!");
    Command cmd = script.getCommand();
    String cmdName = cmd.getName();
    String reulstGrpName = cmd.getGroupOutName();
    //String filenamea = "";
    if (reulstGrpName == null || reulstGrpName.trim().equals("")) {
      reulstGrpName = "ResultGrp";

    }
    userName = cmd.paramValue("DBUSER", null);
    pass = cmd.paramValue("PASSWD", null);
    if (userName == null || userName.trim().equals("")) {
      userName = default_user;
    }
    if (pass == null || pass.trim().equals("")) {
      pass = default_pass;
    }
    // userName = "Administrator";
    // pass ="weblogic";
    SessionService sservice = (SessionService) getEJBService("SessionService");
    // UsersService userservice = (UsersService) getEJBService(
    // "UsersService");
    // UserIfc user = sservice.getCurUserInfo();

    // Ϊ��ǰ�û����ù���ԱȨ��

    // sservice.setAccessEnforced(false);
    // System.out.println("333333 :"+sservice.isAccessEnforced());
    sservice.setAdministrator();
    // GroupInfo groupinfo = (GroupInfo) userservice.getActorValueInfo(
    // "Administrators", false);
    // userservice.groupAddUser(groupinfo, (UserInfo) user);
    // sservice.freeAdministrator();
    // ����helper��
    // ����������Ϣ
    Group responseGrp = createOutputGroup();
    responseGrp.setName(reulstGrpName);
    // ��ȡ��������ContentHolder��BsoName��
    String bsoName = cmd.paramValue("CONTENTHOLDER_BSONAME", null);
    String fileSize = cmd.paramValue("FILESIZE", null);

    // ��ȡ����ContentHolder����Ķ�λ����
    String where = cmd.paramValue("WHERE", null);
		//CCBegin by liunan 2008-09-03
		//Ϊ��������ӿ��ء�
		//liunan 2009-09-04 if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
    System.out.println("***** file" + where + "size is :" + fileSize);
    if (bsoName == null) {
      throw new QMException("ȱ�ٱ�Ҫ����:CONTENTHOLDER_BSONAME");
    }
    // ��ȡ��ɫ��Ϣ
    boolean primary = false;
    String s1 = cmd.paramValue("PRIMARY", "false");
    if (s1.equalsIgnoreCase("true") || s1.equalsIgnoreCase("yes")
        || s1.equalsIgnoreCase("on") || s1.equalsIgnoreCase("y")
        || s1.equalsIgnoreCase("t")) {
      primary = true;
    }
    else if (!s1.equalsIgnoreCase("false") && !s1.equalsIgnoreCase("no")
             && !s1.equalsIgnoreCase("off") && !s1.equalsIgnoreCase("f")
             && !s1.equalsIgnoreCase("n")) {
      throw new QMException(null, RESOURCE, "21", new Object[] {
                            "PRIMARY", s1});
    }

    // ��ȡ�Ƿ��ĵ����������޸�Blob��ʶ(���������޸Ļ����)
    boolean isCheckOut = false;
    // s1 = cmd.paramValue("ISCHECKOUT", "false");
    // if(s1.equalsIgnoreCase("true") || s1.equalsIgnoreCase("yes") ||
    // s1.equalsIgnoreCase("on") || s1.equalsIgnoreCase("y") ||
    // s1.equalsIgnoreCase("t"))
    // isCheckOut = true;
    boolean isReplaceSame = true;
    // s1 = cmd.paramValue("ISREPLACESAME", "false");
    // if(s1.equalsIgnoreCase("true") || s1.equalsIgnoreCase("yes") ||
    // s1.equalsIgnoreCase("on") || s1.equalsIgnoreCase("y") ||
    // s1.equalsIgnoreCase("t"))
    // isReplaceSame = true;
    String fid = cmd.paramValue("FID", null);
    // �м��ļ��б��ļ������
    PrintWriter cmlOut = null;
    if (fid != null) {
      cmlOut = initCML(cmlFilePath, fid);
    }
    Enumeration descEnum = cmd.paramValues("DESCRIPTION");
    Enumeration uploadPaths = cmd.paramValues("UPLOADED_FROM_PATH");
    Enumeration urlEnum = cmd.paramValues("URL");
    Enumeration fSizes = cmd.paramValues("FILESIZE");
    String fileName=cmd.paramValue("FILENAME");
    InteMultipartInputStream mulIs = script.getInputStream();

    // ȷ��ContentHolder����
    ContentHolderIfc contentHolder = null;
    boolean isEPM = false;
    boolean checkOutObj = false;
    
    ContentHeader contHeader = null;
    String streamID = null;
    long len = 0L;
    String fileN = null;
    try {

      ContentService cser = (ContentService) getEJBService("ContentService");
      if (bsoName.equalsIgnoreCase("doc")) {

        // �����ĵ�����
        DocInfo doc = lookupDoc(where);
        if (doc == null) {
          log("����ָ������Ϣ��where=" + where + "���Ҳ�����Ӧ��ContenHolder����");
          handelInputstream(mulIs);
          Element node = new Element();
          addAttributeToNode(node, "MESSAGE", "����ָ������Ϣ��where="
                             + where + "���Ҳ�����Ӧ��ContenHolder����");
          addAttributeToNode(node, "RESULTFLAG", "Failure");
          responseGrp.addElement(node);
          Script response = new Script();
          response.addVdb(responseGrp);
          return (response);
        }
        // ���Ҫ�����ö���,�ٶ�Blob���ݽ������/�޸�,���ȼ���ö���
        if (isCheckOut) {
          // ����Ѿ��������ֱ�ӻ�ȡ����ĸ���
          if (WorkInProgressHelper.isCheckedOut(doc)) {
            if (!WorkInProgressHelper.isWorkingCopy(doc)) {
              doc = (DocInfo) PublishHelper.workingCopyOf(doc);
            }
          }
          // ����ĵ�����û�м���Ҳ��ڸ����ļ����У���Ҫ����ö���
          else if (!PublishHelper.inPersonalFolder(doc)) {
            PublishHelper.checkOut( (WorkableIfc) doc, "��������ĵ�");
            checkOutObj = true;
            doc = (DocInfo) PublishHelper.workingCopyOf(doc);
          }
        } // end isCheckOut
        contentHolder = (ContentHolderIfc) doc;
      }

      else if (bsoName.equalsIgnoreCase("epmdoc")) {
      	//CCBegin SS3
      	System.out.println("====�����㲿�������ļ�");

        // �����㲿������
        QMPartIfc part = lookupPart(where);
        if (part == null) {
          log("����ָ������Ϣ��where=" + where + "���Ҳ�����Ӧ��ContenHolder����");
          handelInputstream(mulIs);
          Element node = new Element();
          addAttributeToNode(node, "MESSAGE", "����ָ������Ϣ��where="
                             + where + "���Ҳ�����Ӧ��ContenHolder����");
          addAttributeToNode(node, "RESULTFLAG", "Failure");
          responseGrp.addElement(node);
          Script response = new Script();
          response.addVdb(responseGrp);
          return (response);
        }
        // ���Ҫ�����ö���,�ٶ�Blob���ݽ������/�޸�,���ȼ���ö���
        if (isCheckOut) {
          // ����Ѿ��������ֱ�ӻ�ȡ����ĸ���
          if (WorkInProgressHelper.isCheckedOut(part)) {
            if (!WorkInProgressHelper.isWorkingCopy(part)) {
              part = (QMPartIfc) PublishHelper.workingCopyOf(part);
            }
          }
          // ����ĵ�����û�м���Ҳ��ڸ����ļ����У���Ҫ����ö���
          else if (!PublishHelper.inPersonalFolder(part)) {
            PublishHelper.checkOut( (WorkableIfc) part, "��������㲿��");
            checkOutObj = true;
            part = (QMPartIfc) PublishHelper.workingCopyOf(part);
          }
        } // end isCheckOut
        contentHolder = (ContentHolderIfc) part;
        //CCEnd SS3
      }
      else if (bsoName.equalsIgnoreCase("deriveimage")) {
        isimage = true;

        String imageName = (String) cmd.paramValue("IMAGENAME", null);
        // System.out.println("111111111111111 the image name is
        // ="+imageName);
        dimage = lookupDerivedImage(imageName);
        // System.out.println("1111111111122222 the image is ="+dimage);
        if (dimage == null) {
          // sservice = (SessionService)
          // getEJBService("SessionService");
          sservice.setAdministrator();
          dimage = new DerivedImageInfo();
          DomainService doservice = (DomainService) getEJBService(
              "DomainService");
          // System.out.println("------- the domain ID
          // is:"+doservice.getDomainID("System"));
          dimage.setDomain(doservice.getDomainID("System"));

          sservice.setAdministrator();
          dimage.setImageName(imageName);
          PersistService ps = (PersistService) getEJBService("PersistService");
          dimage = (DerivedImageInfo) ps.saveValueInfo(dimage);
        }
        contentHolder = (ContentHolderIfc) dimage;
        // System.out.println("1111111111122333 the image is ="+dimage);
      }
      
      //CCBegin by liunan 2009-12-04 �����㲿����������ӷ�����
      if (bsoName.equalsIgnoreCase("part")) {
      	System.out.println("====�����㲿������");

        // �����㲿������
        QMPartIfc part = lookupPart(where);
        if (part == null) {
          log("����ָ������Ϣ��where=" + where + "���Ҳ�����Ӧ��ContenHolder����");
          handelInputstream(mulIs);
          Element node = new Element();
          addAttributeToNode(node, "MESSAGE", "����ָ������Ϣ��where="
                             + where + "���Ҳ�����Ӧ��ContenHolder����");
          addAttributeToNode(node, "RESULTFLAG", "Failure");
          responseGrp.addElement(node);
          Script response = new Script();
          response.addVdb(responseGrp);
          return (response);
        }
        // ���Ҫ�����ö���,�ٶ�Blob���ݽ������/�޸�,���ȼ���ö���
        if (isCheckOut) {
          // ����Ѿ��������ֱ�ӻ�ȡ����ĸ���
          if (WorkInProgressHelper.isCheckedOut(part)) {
            if (!WorkInProgressHelper.isWorkingCopy(part)) {
              part = (QMPartIfc) PublishHelper.workingCopyOf(part);
            }
          }
          // ����ĵ�����û�м���Ҳ��ڸ����ļ����У���Ҫ����ö���
          else if (!PublishHelper.inPersonalFolder(part)) {
            PublishHelper.checkOut( (WorkableIfc) part, "��������㲿��");
            checkOutObj = true;
            part = (QMPartIfc) PublishHelper.workingCopyOf(part);
          }
        } // end isCheckOut
        contentHolder = (ContentHolderIfc) part;
      }
      //CCEnd by liunan 2009-12-04

      // ����urlData��������������
      while (urlEnum.hasMoreElements()) {
        String s4 = (String) urlEnum.nextElement();
        if (s4 == null || s4.trim().equals("")) {
          continue;
        }
        /*
         * ���������ͬurlֵ��URLData����������
         */
        URLDataInfo urlDataT = null;
        urlDataT = urlData(contentHolder, s4);
        if (urlDataT != null) {
          if (isReplaceSame) {
            deleteURLData(contentHolder, urlDataT);
          }
          else {
            log("������ͬURLData���� skip! (holder: " + where + "  url:"
                + s4 + ")");
            handelInputstream(mulIs);
            Element node = new Element();
            addAttributeToNode(node, "MESSAGE",
                               "������ͬURLData���� skip!");
            addAttributeToNode(node, "RESULTFLAG", "Failure");
            responseGrp.addElement(node);
            Script response = new Script();
            response.addVdb(responseGrp);
            return (response);
          }
        }

        // ��Ӳ���
        URLDataInfo urldata = new URLDataInfo();
        urldata.setUrlLocation(s4);
        if (descEnum.hasMoreElements()) {
          urldata.setDescription( (String) descEnum.nextElement());

        }

        if (primary && contentHolder instanceof FormatContentHolderIfc) {
          URLDataInfo urlData = (URLDataInfo) cser
              .uploadPrimaryContent(
              (FormatContentHolderIfc) contentHolder,
              urldata);
          primary = false;
        }
        else {
          URLDataInfo urlData = (URLDataInfo) cser.uploadContent(
              contentHolder, urldata);
        }
      } // end urlData

      // ����ApplicationData
      if (mulIs != null) {
        contHeader = mulIs.readBlobInfo();
        if(fileName==null||fileName.length()==0||fileName.indexOf(".")==-1)
        {
        	fileName=contHeader.fileName;
        }
        
        //CCBegin SS2
        fileName = fileName.trim();
        //CCEnd SS2
        
	  		//CCBegin by liunan 2008-09-03
		  	//Ϊ��������ӿ��ء�
		  	//liunan 2009-09-04 if (PublishHelper.VERBOSE)
		  	//CCEnd by liunan 2008-09-03
        System.out.println("=====>fileName is :" + fileName);
        while (contHeader != null && contHeader.fileName != null) {

          // ȷ���ļ���С������ļ��ܴ�Ҫʹ�ô����ݴ�Blob���ݣ�
          boolean isFileSave = true;
          String lenStr = null;
          FileInputStream fis = null;
          byte fileContent[] = null;
          long beginTime = 0L;
          long endTime = 0L;
          if (fSizes.hasMoreElements()) {
            lenStr = (String) fSizes.nextElement();
          }
          if (lenStr == null) {
            len = 0L;
          }
          else {
            try {
              len = Long.parseLong(lenStr);
              log("���յ��ļ���С:" + len);
            }
            catch (Exception ex) {
              len = 0L;
            }
          }
          if (len == 0L || len > fileSizeLimit) {
            // �����ļ���ʱ�洢����
            isFileSave = true;
          }
          /*
           * ���������ж��Ƿ��Ѿ�����ͬ���������� ���������ͬApplicationData����������
           * (�ļ�����ͬ\������ͬ)
           */
          ApplicationDataInfo _data = applicationData(contentHolder,
              fileName, 0);//�˴�Ϊʲôֱ���õ�0��������len  liunan 2010-01-04
          if (_data != null) {
            if (isReplaceSame) {
              deleteApplicationData(contentHolder, _data);

            }
            else {
              log("������ͬApplicationData���� ! (holder: " + where
                  + "  file:" + fileName + ")");
              handelInputstream(mulIs);
              if (createLink) {
                Element node = new Element();
                addAttributeToNode(node, "MESSAGE",
                                   "������ͬApplicationData���� skip! (holder: "
                                   + contentHolder.getIdentity()
                                   + "  file:"
                                   + fileName + ")");
                addAttributeToNode(node, "RESULTFLAG",
                                   "Failure");
                responseGrp.addElement(node);
                Script response = new Script();
                response.addVdb(responseGrp);
                return (response);

              }
              else {
                Element node = new Element();
                addAttributeToNode(node, "MESSAGE",
                                   "������ͬApplicationData��ͻ���ݴ��󣬷���!(holder: "
                                   + contentHolder.getIdentity()
                                   + "  file:"
                                   + fileName + ")"
                                   + "���ɹ�");
                addAttributeToNode(node, "RESULTFLAG",
                                   "Failure");
                responseGrp.addElement(node);
              }
              Script response = new Script();
              response.addVdb(responseGrp);
              return (response);
            }
          } // end _data!= null

          // �������������л������ݵ��ļ����ڴ�
          if (isFileSave) {
            // ȷ����ʱ�ļ���
            int index = fileName.lastIndexOf(".");
            String tempF = System.currentTimeMillis()
                + fileName.substring(index);
            if (fileUploadSave.endsWith("\\")
                || fileUploadSave.endsWith("/")) {
              fileN = fileUploadSave + tempF;
            }
            else {
              fileN = fileUploadSave + "/" + tempF;
            }

            // ʹ�ô��̷�ʽ�ݴ�Blob����
            beginTime = System.currentTimeMillis();

            FileOutputStream oos = new FileOutputStream(new File(
                fileN));

            mulIs.readBlob(oos);

            oos.close();
            // ���¶�����
            File tempFile = new File(fileN);
            // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            // tempFile.mkdir();
            fis = new FileInputStream(tempFile);

            len = tempFile.length();
            log("�ļ��ϴ�ǰ��С:" + len);
            endTime = System.currentTimeMillis();

          }
          else {
            // ֱ�ӽ�Blob�����������ڴ棬�������ֽ����飬��Ҫ��Blob���ݿ�ҪС�������������ڴ治������������
            beginTime = System.currentTimeMillis();
            fileContent = mulIs.readBlob();
            endTime = System.currentTimeMillis();

            len = fileContent.length;
          }
          //CCBegin SS1
		  ApplicationDataInfo appDataInfo = null;
          if(fileVaultUsed)
          {
        	  //begin modify by jiahx 2014-05-05 �޸��ϴ��ļ����Ʋ�һ�������µĴ���
//        	  int index = fileName.lastIndexOf(".");
//              String tempF = System.currentTimeMillis()
//                  + fileName.substring(index);
//              if (fileUploadSave.endsWith("\\")
//                  || fileUploadSave.endsWith("/")) {
//                fileN = fileUploadSave + tempF;
//              }
//              else {
//                fileN = fileUploadSave + "/" + tempF;
//              }
              //end modify by jiahx 2014-05-05 �޸��ϴ��ļ����Ʋ�һ�������µĴ���
              File tempFile = new File(fileN);
        	  ContentClientHelper helper = new ContentClientHelper();
        	  appDataInfo = helper.requestUpload(tempFile);
        	  
              appDataInfo.setFileName(fileName);
              if (descEnum.hasMoreElements()) {
                appDataInfo.setDescription( (String) descEnum
                                           .nextElement());
              }
              if (uploadPaths.hasMoreElements()) {
                appDataInfo.setUploadPath( (String) uploadPaths
                                          .nextElement());
              }
        	  
        	  if (primary
                      && contentHolder instanceof FormatContentHolderIfc) {
                    // �������ļ�����
                    appDataInfo = (ApplicationDataInfo) cser
                        .uploadPrimaryContent(
                        (FormatContentHolderIfc) contentHolder,
                        appDataInfo);
                    primary = false;
                  }
                  else {
                    // ���������ļ�����

        		      	//CCBegin by liunan 2008-09-03
        	       		//Ϊ��������ӿ��ء�
        		      	//liunan 2009-09-04 if (PublishHelper.VERBOSE)
        	      		//CCEnd by liunan 2008-09-03
                    System.out.println("$$$$$$$$$$$$$$$$$$ dd="
                                       + appDataInfo.getFileName());
                    sservice.setAdministrator();
                    appDataInfo = (ApplicationDataInfo) cser.uploadContent(
                        contentHolder, appDataInfo);
                    //begin modify by jiahx 2014-05-05 ��ӿ���̨�ϴ��ļ���Ϣ�����
                    long i = appDataInfo.getFileSize();
                    Long lo = new Long(i);
                    log("�ļ�" + appDataInfo.getFileName() + "�ϴ����С:" + lo);
                    //end modify by jiahx 2014-05-05 ��ӿ���̨�ϴ��ļ���Ϣ�����
                  }
        	  streamID = "";
          }
          else
          {
        	// ����Ӧ�����ݵ�ֵ����
              appDataInfo = new ApplicationDataInfo();
              appDataInfo.setFileName(fileName);
              appDataInfo.setFileSize(len);
              if (descEnum.hasMoreElements()) {
                appDataInfo.setDescription( (String) descEnum
                                           .nextElement());
              }
              if (uploadPaths.hasMoreElements()) {
                appDataInfo.setUploadPath( (String) uploadPaths
                                          .nextElement());
              }
        	  if (primary
                      && contentHolder instanceof FormatContentHolderIfc) {
                    // �������ļ�����
                    appDataInfo = (ApplicationDataInfo) cser
                        .uploadPrimaryContent(
                        (FormatContentHolderIfc) contentHolder,
                        appDataInfo);
                    primary = false;
                  }
                  else {
                    // ���������ļ�����

        		      	//CCBegin by liunan 2008-09-03
        	       		//Ϊ��������ӿ��ء�
        		      	//liunan 2009-09-04 if (PublishHelper.VERBOSE)
        	      		//CCEnd by liunan 2008-09-03
                    System.out.println("$$$$$$$$$$$$$$$$$$ ddddddd="
                                       + appDataInfo.getFileName());
                    sservice.setAdministrator();
                    appDataInfo = (ApplicationDataInfo) cser.uploadContent(
                        contentHolder, appDataInfo);
                  }
              	  // ������ID
                  streamID = appDataInfo.getStreamDataID();
                  long i = appDataInfo.getFileSize();
                  Long lo = new Long(i);
                  log("�ļ�" + appDataInfo.getFileName() + "�ϴ����С:" + lo);
                  if (Long.parseLong(fileSize) != lo.longValue()) {
                    Element node = new Element();
                    addAttributeToNode(node, "equalornot", "notequal");

                    responseGrp.addElement(node);
                  }
                    beginTime = System.currentTimeMillis();
                    // ����������
          			    //CCBegin by liunan 2008-09-03
          		    	//Ϊ��������ӿ��ء�
          	    		//liunan 2009-09-04 if (PublishHelper.VERBOSE)
          	    		//CCEnd by liunan 2008-09-03
                    System.out.println("&&&&&&&&&&&&&&&&&&&&&& isFileSave :"
                                       + isFileSave);
                    if (isFileSave) {
                      StreamUtilExt.writeData(streamID, fis, len);
                      if (fis != null) {
                        fis.close();
                      }
                      File tempFile = new File(fileN);
          		     	  //CCBegin by liunan 2008-09-03
          	     		  //Ϊ��������ӿ��ء�
          	     		  //liunan 2009-09-04 if (PublishHelper.VERBOSE)
          	      		//CCEnd by liunan 2008-09-03
                      System.out.println("****************************");

                      if (tempFile.isHidden()) {

                        // tempFile.mkdir();
                        tempFile.delete();
                      }
                    }
                    else {
                      StreamUtil.writeData(streamID, fileContent);
                    }
                    endTime = System.currentTimeMillis();
          }
          //CCEnd SS1

          fileContent = null;
          contHeader = mulIs.readBlobInfo();
          // ����һ���м��ļ�ת���б��¼
          if (cmlOut != null) {
            cmlOut.print(contentHolder.getBsoID());
            cmlOut.print(",");
            cmlOut.print(appDataInfo.getBsoID());
            cmlOut.print(",");
            cmlOut.print(streamID);
            cmlOut.print(",");
            cmlOut.print("Ready");
            cmlOut.print(",");
            cmlOut.print("\r\n");
          }
        } // end contHeader
      } // ����������

      // �����ContentHolder����ˣ����ڼ���
      if (checkOutObj) {
          PublishHelper.checkIn( (WorkableIfc) contentHolder, "���뷢���ĵ�");
      }
      // ////////////////////////////////////////////////////////////////////////////
      // ���³��������ɺ���Ҫ�ָ�
      // ////////////////////////////////////////////////////////////////////////////////
      /*
       * if (isimage) { log("����EPM�ĵ���URLData���ԣ�������������������"); String epmnum =
       * cmd.paramValue("EPMNUMBER"); String url = cmd.paramValue("URL");
       * EPMDocumentInfo epm = PublishHelper.getEPMDocInfoByNumber(epmnum);
       * //��EPM�ĵ����URLData���� if(epm!=null) { URLDataInfo urldata = new
       * URLDataInfo(); urldata.setUrlLocation(url);
       * urldata.setDescription("EPM�ĵ������ļ�"); URLDataInfo urlData; if
       * (primary) urlData = (URLDataInfo) cser.uploadPrimaryContent(epm,
       * urldata); else urlData = (URLDataInfo) cser.uploadContent(epm,
       * urldata); //�洢EPM�ĵ���DerivedImage�Ĺ��� //ViewMarkUpServiceAdapter
       * delegate = TransformHelper.getDelegate(); SourceToImageIfc link =
       * new SourceToImageInfo(dimage, (GraphicallyRepresentableIfc) epm);
       * PersistService
       * pser1=(PersistService)getEJBService("PersistService");
       * pser1.saveValueInfo(link); } else { errorLog("�����ļ����ڵ�EPM�ĵ�Ϊ��!"); }
       *  }
       */
      // ///////////////////////////////////////////////////////////////
      // back
      // //////////////////////////////////////////////////////////////
      // ///////////////////////////////////////////////////////////////////////////////////
      // ���³����Ϊ����ʹ��
      // /////////////////////////////////////////////////////////////////////////////////////
      if (isimage) {
        String epmnum = cmd.paramValue("EPMNUMBER");
        // System.out.println("111111111111111 *******************
        // ------------"+epmnum);
        String url = cmd.paramValue("URL");
        EPMDocumentInfo epm = null;
        epm = PublishHelper.getEPMDocInfoByNumber(epmnum);
        PersistService pser = (PersistService) getEJBService("PersistService");
        if (epm == null) {
          EPMDocumentType docTypeT = EPMDocumentType.getDefautType();
          EPMAuthoringAppType authAppT = EPMAuthoringAppType
              .getDefaultType();
          epm = EPMDocumentInfo.newEPMDocument(epmnum,
                                               "testmodelfile" + epmnum,
                                               authAppT, docTypeT);
          // EPMDocumentIfc epm = EPMDocumentInfo.newEPMDocument();
          // epm.setDocNumber(epmnum);
          // epm.setDocName("testmodelfile"+epmnum);
          epm.setDescription("dd");
          epm.setLocation("\\Root\\����\\ͼ��");
          DomainService doservice = (DomainService) getEJBService(
              "DomainService");
          // String domainID = doservice.getDomainID("System");
          // epm.setDomain(domainID);
          epm.setOwnerApplication(EPMApplicationType
                                  .toEPMApplicationType("PRODUCTVIEW"));
          epm.setAuthoringApplication(EPMAuthoringAppType
                                      .toAuthoringAppType("PROE"));
          //CCBegin by liunan 2011-03-17 ���ݽ��Ҫ���޸�������������
          //String lifeCyID = PublishHelper.getLifeCyID("��������������");
          String lifeCyID = PublishHelper.getLifeCyID("���ݽ����������ڶ�");
          //CCEnd by liunan 2011-03-17
          epm.setLifeCycleTemplate(lifeCyID);

          epm = (EPMDocumentInfo) pser.saveValueInfo(epm);

        }
        else {
          QMQuery query = new QMQuery("SourceToImage");
          try {
            query.addCondition(new QueryCondition("rightBsoID",
                                                  "=", epmnum));
            Collection result = pser.findValueInfo(query);
            if (result != null && !result.isEmpty()) {
              SourceToImageIfc sourti = (SourceToImageIfc) result
                  .iterator().next();
              pser.removeValueInfo(sourti);
            }
          }
          catch (Exception e) {

          }
        }
        URLDataInfo urldata = new URLDataInfo();
        urldata.setUrlLocation(url);
        urldata.setDescription("EPM�ĵ������ļ�");
        URLDataInfo urlData;
        if (primary) {
          urlData = (URLDataInfo) cser.uploadPrimaryContent(epm,
              urldata);
        }
        else {
          urlData = (URLDataInfo) cser.uploadContent(epm, urldata);
          // �洢EPM�ĵ���DerivedImage�Ĺ���
        }
        // ViewMarkUpServiceAdapter delegate =
        // TransformHelper.getDelegate();
        
        //CCBegin by liunan 2008-08-07
        //SourceToImageInfo���캯�������°汾�ղ�������˳��ı��ˣ���Ϊ�������·�ʽ���ݡ�
        //ԭ��������
        //SourceToImageIfc link = new SourceToImageInfo(dimage,
            //(GraphicallyRepresentableIfc) epm);
        SourceToImageIfc link = new SourceToImageInfo((ContentItemIfc) epm, dimage);
        //CCBegin by liunan 2008-08-07
        
        // System.out.println("22222222222222222 4$**** delegate
        // is:"+delegate);
        // System.out.println("33333333333333333 ****** link is:"+link);
        // delegate.saveValueInfo(link);
        PersistService pser1 = (PersistService) PublishHelper
            .getEJBService("PersistService");
        pser1.saveValueInfo(link);

      }
      // //////////////////////////////////////////////////////////////////////////////////////
      // end
      // ////////////////////////////////////////////////////////////////////////////////////////

      // �����ύ
      log(fileName + "�������ݱ���ɹ�");
    // �ر��м��ļ�ת���б�
      if (cmlOut != null) {
        try {
          cmlOut.flush();
          cmlOut.close();
        }
        catch (Exception ex) {
          ex.printStackTrace();
        }
      }

    }
    catch (Exception ex) {
      ex.printStackTrace();
      errorLog(ex);
      log(fileName + ",ʧ��" + "," + ex.getLocalizedMessage());
      Element node = new Element();
      addAttributeToNode(node, "MESSAGE", ex.getClass().toString());
      addAttributeToNode(node, "RESULTFLAG", "Failure");
      responseGrp.addElement(node);
      Script response = new Script();
      response.addVdb(responseGrp);
			//CCBegin by liunan 2008-09-03
			//Ϊ��������ӿ��ء�
			//liunan 2009-09-04 if (PublishHelper.VERBOSE)
			//CCEnd by liunan 2008-09-03
      System.out.println("the return response is :" + response);
      return (response);
    }
    /*
     * finally { log("*************************
     * ���δ������**************************"); sservice.setAccessEnforced(true); }
     */

    // �����ǰ�û��Ĺ���ԱȨ��
    // �����ǰ�û��Ĺ���ԱȨ��
    // if(userservice.isMember((UserInfo)user,groupinfo))
    // userservice.groupDeleteUser(groupinfo, (UserInfo) user);
    sservice.freeAdministrator();
    Element node = new Element();
    addAttributeToNode(node, "MESSAGE", "Upload OK!");
    addAttributeToNode(node, "RESULTFLAG", "Success");
    responseGrp.addElement(node);
    Script response = new Script();
    response.addVdb(responseGrp);
    System.out.println("PublishAddContentCmdDelegate is end!");
    return (response);
    // ##end

  }

  /**
   * �ж�ContentHolder�����Ƿ��Ѵ���ָ����url��Ϣ
   *
   * @param holder
   *            ContentHolderIfc ContentHolder����
   * @param url
   *            String URLDataInfo��urlֵ
   * @return boolean
   */
  private boolean hasUrl(ContentHolderIfc holder, String url) {
    URLDataInfo data = urlData(holder, url);
    if (data == null) {
      return false;
    }
    else {
      return true;
    }
  }

  /**
   * ����ContentHolder�����Ƿ��Ѵ���ָ����url��������Ϣ
   *
   * @param holder
   *            ContentHolderIfc
   * @param url
   *            String URLDataInfo�����urlֵ
   * @return URLDataInfo
   */
  private URLDataInfo urlData(ContentHolderIfc holder, String url) {
    if (url == null || url.trim().equals("")) {
      return null;
    }
    try {
      ContentService cs = (ContentService) getEJBService("ContentService");
      Vector v = cs.getContents(holder);
      ContentItemIfc item;
      URLDataInfo urlInfo = null;

      for (Iterator iter = v.iterator(); iter.hasNext(); ) {
        item = (ContentItemIfc) iter.next();
        if (item instanceof URLDataInfo) {
          urlInfo = (URLDataInfo) item;
          if (url.equals(urlInfo.getUrlLocation())) {
            return urlInfo;
          }
        }
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    return null;

  }

  /**
   * �ж�ContentHolder�����Ƿ��Ѵ���ָ������������Ϣ <BR>
   * ����ļ�����fSizeΪ0L����ֻ�Ƚ��ļ���
   *
   * @param holder
   *            ContentHolderIfc ContentHolder����
   * @param fileN
   *            String �ļ���
   * @param fSize
   *            long �ļ�����
   * @return boolean
   */
  private boolean hasApplication(ContentHolderIfc holder, String fileN,
                                 long fSize) {
    ApplicationDataInfo data = applicationData(holder, fileN, fSize);
    if (data == null) {
      return false;
    }
    else {
      return true;
    }

  }

  /**
   * ����ContentHolder�����Ƿ��Ѵ���ָ������������Ϣ <BR>
   * ����ļ�����fSizeΪ0L����ֻ�Ƚ��ļ���
   *
   * @param holder
   *            ContentHolderIfc ContentHolder����
   * @param fileN
   *            String �ļ���
   * @param fSize
   *            long �ļ�����
   * @return ApplicationDataInfo �Ѵ��ڵ�������
   */
  private ApplicationDataInfo applicationData(ContentHolderIfc holder,
                                              String fileN, long fSize) {
    // debug("************SHF DEBUG fileN="+fileN);
    // debug("************SHF DEBUG fileS="+fSize);
    if (fileN == null || fileN.trim().equals("")) {
      return null;
    }
    try {
      ContentService cs = (ContentService) getEJBService("ContentService");
      Vector v = cs.getContents(holder);
      ContentItemIfc item;
      ApplicationDataInfo appDataInfo = null;
      for (Iterator iter = v.iterator(); iter.hasNext(); ) {
        item = (ContentItemIfc) iter.next();
        if (item instanceof ApplicationDataInfo) {
          appDataInfo = (ApplicationDataInfo) item;
          if (fileN.equals(appDataInfo.getFileName())
              && (fSize == 0L || fSize == appDataInfo
                  .getFileSize())) {
            return appDataInfo;
          }
        }
      }
    }
    catch (Exception ex) {
      ex.printStackTrace();
      return null;
    }
    return null;

  }

  /**
   * ɾ��ָ����������
   *
   * @param holder
   *            ContentHolderIfc
   * @param data
   *            ApplicationDataInfo
   */
  private void deleteApplicationData(ContentHolderIfc holder,
                                     ApplicationDataInfo data) {

    try {
      ContentService cs = (ContentService) getEJBService("ContentService");
      cs.deleteApplicationData(holder, data);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * ɾ��ָ����������
   *
   * @param holder
   *            ContentHolderIfc
   * @param data
   *            URLDataInfo
   */
  private void deleteURLData(ContentHolderIfc holder, URLDataInfo data) {

    try {
      ContentService cs = (ContentService) PublishHelper
          .getEJBService("ContentService");
      cs.deleteURLData(holder, data);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /**
   * ���ݲ����������Ҷ�Ӧ���ĵ�����
   *
   * @param where
   *            String ��������
   * @throws QMException
   * @return DocInfo ��Ӧ���ĵ�����
   */
  private DocInfo lookupDoc(String where) throws QMException {
    if (where == null) {
      return null;
    }
    Hashtable table = super.parseWhere(where);

    String num = (String) table.get("number");

    // �����version��iterationΪ����Դ�Ĵ�汾�ź�С�汾��
    String version = (String) table.get("version");

    // String iteration = (String)table.get("iteration");
    if (num == null || num.trim().length() == 0) {
      throw new QMException("�ĵ����������б�ҪnumberԼ��!");
    }
    if ( (version == null || version.trim().equals(""))) {
      return PublishHelper.getDocInfoByNumber(num);
    }
    else {
      // ���ݷ���Դ�Ĵ�汾��,С�汾�Ų���(��չ�����м�¼)

      return PublishHelper.getDocInfoByOrigInfo(num, version);
    }
  }

  private DerivedImageInfo lookupDerivedImage(String where) throws QMException {
    if (where == null) {
      return null;
    }
    DerivedImageInfo dimage = null;
    // d.setImageName();
    PersistService pservice = (PersistService) PublishHelper
        .getEJBService("PersistService");
    QMQuery query = new QMQuery("DerivedImage");
    query.addCondition(new QueryCondition("imageName", "=", where));
    Collection result = pservice.findValueInfo(query);
    if (result != null && result.size() != 0) {
      for (Iterator ite = result.iterator(); ite.hasNext(); ) {
        dimage = (DerivedImageInfo) ite.next();
      }
    }
    return dimage;

  }

  /**
   * �м��ļ�ת���б�����豸ʼ����
   *
   * @param path
   *            String �ļ�ת���б��ļ�Ŀ¼
   * @param fid
   *            String �ļ�ת���б��ļ�ID
   * @return PrintWriter ����豸
   */
  private PrintWriter initCML(String path, String fid) {
    if (fid == null) {
      return null;
    }
    String listF = null;
    if (path.endsWith("/") || path.endsWith("\\")) {
      listF = path + "CML";
    }
    else {
      listF = path + "/CML";
    }
    listF = listF + fid + ".list";
    PrintWriter out = null;
    try {
      // ��׷�ӷ�ʽ������ת���б��ļ�
      out = new PrintWriter(new FileWriter(listF, true), true);
    }
    catch (IOException ioEx) {
      // ioEx.printStackTrace();
      PublishPartsLog.logError(ioEx);
      return null;
    }
    return out;
  }

  /**
   * ���ݲ����������Ҷ�Ӧ���㲿������
   *
   * @param where
   *            String ��������
   * @throws QMException
   * @return QMPartInfo ��Ӧ�㲿������
   */
  private QMPartIfc lookupPart(String where) throws QMException {
    if (where == null) {
      return null;
    }
    // ���ȸ����㲿������Դ�ı�š���汾�š�С�汾�ţ�ȷ����Ӧ���㲿��
    QMPartIfc part = null;
    Hashtable table = super.parseWhere(where);
    String num = (String) table.get("number");
    // �����version��iterationΪ����Դ�Ĵ�汾�ź�С�汾��
    String version = (String) table.get("version");
    // String iteration = (String)table.get("iteration");
    if (num == null || num.trim().length() == 0) {
      throw new QMException("�㲿�����������б�ҪnumberԼ��!");
    }
    if ( (version == null || version.trim().equals(""))) {
      part = PublishHelper.getPartInfoByNumber(num);
    }
    else {
      // ���ݷ���Դ�Ĵ�汾��,С�汾�Ų���(IBA�����м�¼)
      part = PublishHelper.getPartInfoByOrigInfo(num, version);
    }

    if (part == null) {
      return null;
    }
    return part;
  }


  /**
   * ��ʾ�㲿��������Ϣ
   *
   * @param part
   *            QMPartInfo
   * @return String
   */
  private String getIdetifer(QMPartInfo part) {
    StringBuffer sb = new StringBuffer();
    sb.append(part.getPartNumber());
    sb.append(" ");
    sb.append(part.getPartName());
    sb.append(" ");
    sb.append(part.getVersionValue());
    return sb.toString();
  }

  private void handelInputstream(InteMultipartInputStream inteMulIs) {
    if (inteMulIs == null) {
      return;
    }
    try {
      inteMulIs.streamBlobs(new OutputStream() {

        public void close() throws IOException {
        }

        public void flush() throws IOException {
        }

        public void write(byte abyte0[], int j, int k) throws IOException {
        }

        public void write(int j) throws IOException {
        }

        public void write(byte abyte0[]) throws IOException {
        }

      });
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

  }

  public static void main(String[] args) {
    PublishAddContentCmdDelegate tt = new PublishAddContentCmdDelegate();
  }

}
