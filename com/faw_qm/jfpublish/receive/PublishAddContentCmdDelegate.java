package com.faw_qm.jfpublish.receive;

/**
 * 发布内容数据的代理类
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author 王海军
 * @version 1.0
 * SS1 添加文件服务器传输逻辑 jiahx 2013-12-10
 * SS2 去掉文件名称中空格，否则可视化会有问题。 liunan 2014-3-21
 * SS3 汽研windchill升级10.2，不发布epm把中性文件发不到part上。 liunan 2015-8-21
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

  // 中性文件转换列表文件目录
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
    log("************************* 本次处理开始**************************");
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

    // 为当前用户设置管理员权限

    // sservice.setAccessEnforced(false);
    // System.out.println("333333 :"+sservice.isAccessEnforced());
    sservice.setAdministrator();
    // GroupInfo groupinfo = (GroupInfo) userservice.getActorValueInfo(
    // "Administrators", false);
    // userservice.groupAddUser(groupinfo, (UserInfo) user);
    // sservice.freeAdministrator();
    // 创建helper类
    // 创建反馈信息
    Group responseGrp = createOutputGroup();
    responseGrp.setName(reulstGrpName);
    // 获取操作对象ContentHolder的BsoName名
    String bsoName = cmd.paramValue("CONTENTHOLDER_BSONAME", null);
    String fileSize = cmd.paramValue("FILESIZE", null);

    // 获取查找ContentHolder对象的定位条件
    String where = cmd.paramValue("WHERE", null);
		//CCBegin by liunan 2008-09-03
		//为输出语句添加开关。
		//liunan 2009-09-04 if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
    System.out.println("***** file" + where + "size is :" + fileSize);
    if (bsoName == null) {
      throw new QMException("缺少必要参数:CONTENTHOLDER_BSONAME");
    }
    // 获取角色信息
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

    // 获取是否将文档对象检出后修改Blob标识(以区别是修改或添加)
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
    // 中间文件列表文件输出器
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

    // 确定ContentHolder对象
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

        // 查找文档对象
        DocInfo doc = lookupDoc(where);
        if (doc == null) {
          log("根据指定的信息（where=" + where + "）找不到对应的ContenHolder对象");
          handelInputstream(mulIs);
          Element node = new Element();
          addAttributeToNode(node, "MESSAGE", "根据指定的信息（where="
                             + where + "）找不到对应的ContenHolder对象");
          addAttributeToNode(node, "RESULTFLAG", "Failure");
          responseGrp.addElement(node);
          Script response = new Script();
          response.addVdb(responseGrp);
          return (response);
        }
        // 如果要求检出该对象,再对Blob数据进行添加/修改,则先检出该对象
        if (isCheckOut) {
          // 如果已经检出，则直接获取检出的副本
          if (WorkInProgressHelper.isCheckedOut(doc)) {
            if (!WorkInProgressHelper.isWorkingCopy(doc)) {
              doc = (DocInfo) PublishHelper.workingCopyOf(doc);
            }
          }
          // 如果文档对象没有检出且不在个人文件夹中，则要检出该对象
          else if (!PublishHelper.inPersonalFolder(doc)) {
            PublishHelper.checkOut( (WorkableIfc) doc, "发布检出文档");
            checkOutObj = true;
            doc = (DocInfo) PublishHelper.workingCopyOf(doc);
          }
        } // end isCheckOut
        contentHolder = (ContentHolderIfc) doc;
      }

      else if (bsoName.equalsIgnoreCase("epmdoc")) {
      	//CCBegin SS3
      	System.out.println("====增加零部件中性文件");

        // 查找零部件对象
        QMPartIfc part = lookupPart(where);
        if (part == null) {
          log("根据指定的信息（where=" + where + "）找不到对应的ContenHolder对象");
          handelInputstream(mulIs);
          Element node = new Element();
          addAttributeToNode(node, "MESSAGE", "根据指定的信息（where="
                             + where + "）找不到对应的ContenHolder对象");
          addAttributeToNode(node, "RESULTFLAG", "Failure");
          responseGrp.addElement(node);
          Script response = new Script();
          response.addVdb(responseGrp);
          return (response);
        }
        // 如果要求检出该对象,再对Blob数据进行添加/修改,则先检出该对象
        if (isCheckOut) {
          // 如果已经检出，则直接获取检出的副本
          if (WorkInProgressHelper.isCheckedOut(part)) {
            if (!WorkInProgressHelper.isWorkingCopy(part)) {
              part = (QMPartIfc) PublishHelper.workingCopyOf(part);
            }
          }
          // 如果文档对象没有检出且不在个人文件夹中，则要检出该对象
          else if (!PublishHelper.inPersonalFolder(part)) {
            PublishHelper.checkOut( (WorkableIfc) part, "发布检出零部件");
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
      
      //CCBegin by liunan 2009-12-04 增加零部件附件的添加方法。
      if (bsoName.equalsIgnoreCase("part")) {
      	System.out.println("====增加零部件附件");

        // 查找零部件对象
        QMPartIfc part = lookupPart(where);
        if (part == null) {
          log("根据指定的信息（where=" + where + "）找不到对应的ContenHolder对象");
          handelInputstream(mulIs);
          Element node = new Element();
          addAttributeToNode(node, "MESSAGE", "根据指定的信息（where="
                             + where + "）找不到对应的ContenHolder对象");
          addAttributeToNode(node, "RESULTFLAG", "Failure");
          responseGrp.addElement(node);
          Script response = new Script();
          response.addVdb(responseGrp);
          return (response);
        }
        // 如果要求检出该对象,再对Blob数据进行添加/修改,则先检出该对象
        if (isCheckOut) {
          // 如果已经检出，则直接获取检出的副本
          if (WorkInProgressHelper.isCheckedOut(part)) {
            if (!WorkInProgressHelper.isWorkingCopy(part)) {
              part = (QMPartIfc) PublishHelper.workingCopyOf(part);
            }
          }
          // 如果文档对象没有检出且不在个人文件夹中，则要检出该对象
          else if (!PublishHelper.inPersonalFolder(part)) {
            PublishHelper.checkOut( (WorkableIfc) part, "发布检出零部件");
            checkOutObj = true;
            part = (QMPartIfc) PublishHelper.workingCopyOf(part);
          }
        } // end isCheckOut
        contentHolder = (ContentHolderIfc) part;
      }
      //CCEnd by liunan 2009-12-04

      // 处理urlData类型内容项数据
      while (urlEnum.hasMoreElements()) {
        String s4 = (String) urlEnum.nextElement();
        if (s4 == null || s4.trim().equals("")) {
          continue;
        }
        /*
         * 如果存在相同url值的URLData内容项数据
         */
        URLDataInfo urlDataT = null;
        urlDataT = urlData(contentHolder, s4);
        if (urlDataT != null) {
          if (isReplaceSame) {
            deleteURLData(contentHolder, urlDataT);
          }
          else {
            log("存在相同URLData数据 skip! (holder: " + where + "  url:"
                + s4 + ")");
            handelInputstream(mulIs);
            Element node = new Element();
            addAttributeToNode(node, "MESSAGE",
                               "存在相同URLData数据 skip!");
            addAttributeToNode(node, "RESULTFLAG", "Failure");
            responseGrp.addElement(node);
            Script response = new Script();
            response.addVdb(responseGrp);
            return (response);
          }
        }

        // 添加操作
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

      // 处理ApplicationData
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
		  	//为输出语句添加开关。
		  	//liunan 2009-09-04 if (PublishHelper.VERBOSE)
		  	//CCEnd by liunan 2008-09-03
        System.out.println("=====>fileName is :" + fileName);
        while (contHeader != null && contHeader.fileName != null) {

          // 确定文件大小，如果文件很大，要使用磁盘暂存Blob数据，
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
              log("接收的文件大小:" + len);
            }
            catch (Exception ex) {
              len = 0L;
            }
          }
          if (len == 0L || len > fileSizeLimit) {
            // 采用文件暂时存储方案
            isFileSave = true;
          }
          /*
           * 检查内容项，判断是否已经存在同名的内容项 如果存在相同ApplicationData内容项数据
           * (文件名相同\长度相同)
           */
          ApplicationDataInfo _data = applicationData(contentHolder,
              fileName, 0);//此处为什么直接用的0，而不是len  liunan 2010-01-04
          if (_data != null) {
            if (isReplaceSame) {
              deleteApplicationData(contentHolder, _data);

            }
            else {
              log("存在相同ApplicationData数据 ! (holder: " + where
                  + "  file:" + fileName + ")");
              handelInputstream(mulIs);
              if (createLink) {
                Element node = new Element();
                addAttributeToNode(node, "MESSAGE",
                                   "存在相同ApplicationData数据 skip! (holder: "
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
                                   "存在相同ApplicationData冲突数据错误，发布!(holder: "
                                   + contentHolder.getIdentity()
                                   + "  file:"
                                   + fileName + ")"
                                   + "不成功");
                addAttributeToNode(node, "RESULTFLAG",
                                   "Failure");
                responseGrp.addElement(node);
              }
              Script response = new Script();
              response.addVdb(responseGrp);
              return (response);
            }
          } // end _data!= null

          // 从网络输入流中缓冲数据到文件或内存
          if (isFileSave) {
            // 确定暂时文件名
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

            // 使用磁盘方式暂存Blob数据
            beginTime = System.currentTimeMillis();

            FileOutputStream oos = new FileOutputStream(new File(
                fileN));

            mulIs.readBlob(oos);

            oos.close();
            // 重新读数据
            File tempFile = new File(fileN);
            // System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            // tempFile.mkdir();
            fis = new FileInputStream(tempFile);

            len = tempFile.length();
            log("文件上传前大小:" + len);
            endTime = System.currentTimeMillis();

          }
          else {
            // 直接将Blob数据流读入内存，并产生字节数组，它要求Blob数据块要小，否则可能造成内存不足或溢出等问题
            beginTime = System.currentTimeMillis();
            fileContent = mulIs.readBlob();
            endTime = System.currentTimeMillis();

            len = fileContent.length;
          }
          //CCBegin SS1
		  ApplicationDataInfo appDataInfo = null;
          if(fileVaultUsed)
          {
        	  //begin modify by jiahx 2014-05-05 修改上传文件名称不一致所导致的错误
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
              //end modify by jiahx 2014-05-05 修改上传文件名称不一致所导致的错误
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
                    // 关联主文件内容
                    appDataInfo = (ApplicationDataInfo) cser
                        .uploadPrimaryContent(
                        (FormatContentHolderIfc) contentHolder,
                        appDataInfo);
                    primary = false;
                  }
                  else {
                    // 关联附加文件内容

        		      	//CCBegin by liunan 2008-09-03
        	       		//为输出语句添加开关。
        		      	//liunan 2009-09-04 if (PublishHelper.VERBOSE)
        	      		//CCEnd by liunan 2008-09-03
                    System.out.println("$$$$$$$$$$$$$$$$$$ dd="
                                       + appDataInfo.getFileName());
                    sservice.setAdministrator();
                    appDataInfo = (ApplicationDataInfo) cser.uploadContent(
                        contentHolder, appDataInfo);
                    //begin modify by jiahx 2014-05-05 添加控制台上传文件信息的输出
                    long i = appDataInfo.getFileSize();
                    Long lo = new Long(i);
                    log("文件" + appDataInfo.getFileName() + "上传后大小:" + lo);
                    //end modify by jiahx 2014-05-05 添加控制台上传文件信息的输出
                  }
        	  streamID = "";
          }
          else
          {
        	// 创建应用数据的值对象
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
                    // 关联主文件内容
                    appDataInfo = (ApplicationDataInfo) cser
                        .uploadPrimaryContent(
                        (FormatContentHolderIfc) contentHolder,
                        appDataInfo);
                    primary = false;
                  }
                  else {
                    // 关联附加文件内容

        		      	//CCBegin by liunan 2008-09-03
        	       		//为输出语句添加开关。
        		      	//liunan 2009-09-04 if (PublishHelper.VERBOSE)
        	      		//CCEnd by liunan 2008-09-03
                    System.out.println("$$$$$$$$$$$$$$$$$$ ddddddd="
                                       + appDataInfo.getFileName());
                    sservice.setAdministrator();
                    appDataInfo = (ApplicationDataInfo) cser.uploadContent(
                        contentHolder, appDataInfo);
                  }
              	  // 数据流ID
                  streamID = appDataInfo.getStreamDataID();
                  long i = appDataInfo.getFileSize();
                  Long lo = new Long(i);
                  log("文件" + appDataInfo.getFileName() + "上传后大小:" + lo);
                  if (Long.parseLong(fileSize) != lo.longValue()) {
                    Element node = new Element();
                    addAttributeToNode(node, "equalornot", "notequal");

                    responseGrp.addElement(node);
                  }
                    beginTime = System.currentTimeMillis();
                    // 传内容数据
          			    //CCBegin by liunan 2008-09-03
          		    	//为输出语句添加开关。
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
          	     		  //为输出语句添加开关。
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
          // 产生一条中间文件转换列表记录
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
      } // 处理流结束

      // 如果是ContentHolder检出了，现在检入
      if (checkOutObj) {
          PublishHelper.checkIn( (WorkableIfc) contentHolder, "检入发布文档");
      }
      // ////////////////////////////////////////////////////////////////////////////
      // 以下程序测试完成后需要恢复
      // ////////////////////////////////////////////////////////////////////////////////
      /*
       * if (isimage) { log("更新EPM文档的URLData属性－－－－－－－－－－"); String epmnum =
       * cmd.paramValue("EPMNUMBER"); String url = cmd.paramValue("URL");
       * EPMDocumentInfo epm = PublishHelper.getEPMDocInfoByNumber(epmnum);
       * //向EPM文档添加URLData属性 if(epm!=null) { URLDataInfo urldata = new
       * URLDataInfo(); urldata.setUrlLocation(url);
       * urldata.setDescription("EPM文档中性文件"); URLDataInfo urlData; if
       * (primary) urlData = (URLDataInfo) cser.uploadPrimaryContent(epm,
       * urldata); else urlData = (URLDataInfo) cser.uploadContent(epm,
       * urldata); //存储EPM文档和DerivedImage的关联 //ViewMarkUpServiceAdapter
       * delegate = TransformHelper.getDelegate(); SourceToImageIfc link =
       * new SourceToImageInfo(dimage, (GraphicallyRepresentableIfc) epm);
       * PersistService
       * pser1=(PersistService)getEJBService("PersistService");
       * pser1.saveValueInfo(link); } else { errorLog("中性文件所在的EPM文档为空!"); }
       *  }
       */
      // ///////////////////////////////////////////////////////////////
      // back
      // //////////////////////////////////////////////////////////////
      // ///////////////////////////////////////////////////////////////////////////////////
      // 以下程序仅为测试使用
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
          epm.setLocation("\\Root\\其它\\图样");
          DomainService doservice = (DomainService) getEJBService(
              "DomainService");
          // String domainID = doservice.getDomainID("System");
          // epm.setDomain(domainID);
          epm.setOwnerApplication(EPMApplicationType
                                  .toEPMApplicationType("PRODUCTVIEW"));
          epm.setAuthoringApplication(EPMAuthoringAppType
                                      .toAuthoringAppType("PROE"));
          //CCBegin by liunan 2011-03-17 根据解放要求，修改生命周期命名
          //String lifeCyID = PublishHelper.getLifeCyID("流程类生命周期");
          String lifeCyID = PublishHelper.getLifeCyID("数据接收生命周期二");
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
        urldata.setDescription("EPM文档中性文件");
        URLDataInfo urlData;
        if (primary) {
          urlData = (URLDataInfo) cser.uploadPrimaryContent(epm,
              urldata);
        }
        else {
          urlData = (URLDataInfo) cser.uploadContent(epm, urldata);
          // 存储EPM文档和DerivedImage的关联
        }
        // ViewMarkUpServiceAdapter delegate =
        // TransformHelper.getDelegate();
        
        //CCBegin by liunan 2008-08-07
        //SourceToImageInfo构造函数在最新版本终参数传递顺序改变了，改为采用最新方式传递。
        //原代码如下
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

      // 事务提交
      log(fileName + "内容数据保存成功");
    // 关闭中间文件转换列表
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
      log(fileName + ",失败" + "," + ex.getLocalizedMessage());
      Element node = new Element();
      addAttributeToNode(node, "MESSAGE", ex.getClass().toString());
      addAttributeToNode(node, "RESULTFLAG", "Failure");
      responseGrp.addElement(node);
      Script response = new Script();
      response.addVdb(responseGrp);
			//CCBegin by liunan 2008-09-03
			//为输出语句添加开关。
			//liunan 2009-09-04 if (PublishHelper.VERBOSE)
			//CCEnd by liunan 2008-09-03
      System.out.println("the return response is :" + response);
      return (response);
    }
    /*
     * finally { log("*************************
     * 本次处理结束**************************"); sservice.setAccessEnforced(true); }
     */

    // 解除当前用户的管理员权限
    // 解除当前用户的管理员权限
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
   * 判断ContentHolder对象是否已存在指定的url信息
   *
   * @param holder
   *            ContentHolderIfc ContentHolder对象
   * @param url
   *            String URLDataInfo的url值
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
   * 查找ContentHolder对象是否已存在指定的url内容项信息
   *
   * @param holder
   *            ContentHolderIfc
   * @param url
   *            String URLDataInfo对象的url值
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
   * 判断ContentHolder对象是否已存在指定的内容项信息 <BR>
   * 如果文件长度fSize为0L，则只比较文件名
   *
   * @param holder
   *            ContentHolderIfc ContentHolder对象
   * @param fileN
   *            String 文件名
   * @param fSize
   *            long 文件长度
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
   * 查找ContentHolder对象是否已存在指定的内容项信息 <BR>
   * 如果文件长度fSize为0L，则只比较文件名
   *
   * @param holder
   *            ContentHolderIfc ContentHolder对象
   * @param fileN
   *            String 文件名
   * @param fSize
   *            long 文件长度
   * @return ApplicationDataInfo 已存在的内容项
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
   * 删除指定的内容项
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
   * 删除指定的内容项
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
   * 根据查找条件查找对应的文档对象
   *
   * @param where
   *            String 查找条件
   * @throws QMException
   * @return DocInfo 对应的文档对象
   */
  private DocInfo lookupDoc(String where) throws QMException {
    if (where == null) {
      return null;
    }
    Hashtable table = super.parseWhere(where);

    String num = (String) table.get("number");

    // 这里的version及iteration为发布源的大版本号和小版本号
    String version = (String) table.get("version");

    // String iteration = (String)table.get("iteration");
    if (num == null || num.trim().length() == 0) {
      throw new QMException("文档查找条件中必要number约束!");
    }
    if ( (version == null || version.trim().equals(""))) {
      return PublishHelper.getDocInfoByNumber(num);
    }
    else {
      // 根据发布源的大版本号,小版本号查找(扩展属性中记录)

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
   * 中间文件转换列表输出设备始初化
   *
   * @param path
   *            String 文件转换列表文件目录
   * @param fid
   *            String 文件转换列表文件ID
   * @return PrintWriter 输出设备
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
      // 以追加方式打开中性转换列表文件
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
   * 根据查找条件查找对应的零部件对象
   *
   * @param where
   *            String 查找条件
   * @throws QMException
   * @return QMPartInfo 对应零部件对象
   */
  private QMPartIfc lookupPart(String where) throws QMException {
    if (where == null) {
      return null;
    }
    // 首先根据零部件发布源的编号、大版本号、小版本号，确定对应的零部件
    QMPartIfc part = null;
    Hashtable table = super.parseWhere(where);
    String num = (String) table.get("number");
    // 这里的version及iteration为发布源的大版本号和小版本号
    String version = (String) table.get("version");
    // String iteration = (String)table.get("iteration");
    if (num == null || num.trim().length() == 0) {
      throw new QMException("零部件查找条件中必要number约束!");
    }
    if ( (version == null || version.trim().equals(""))) {
      part = PublishHelper.getPartInfoByNumber(num);
    }
    else {
      // 根据发布源的大版本号,小版本号查找(IBA属性中记录)
      part = PublishHelper.getPartInfoByOrigInfo(num, version);
    }

    if (part == null) {
      return null;
    }
    return part;
  }


  /**
   * 显示零部件基本信息
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
