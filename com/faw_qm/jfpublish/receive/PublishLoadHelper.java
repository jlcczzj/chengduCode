/**
 * SS1 添加文件服务器传输逻辑 jiahx 2013-12-10
 */
package com.faw_qm.jfpublish.receive;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
//CCBegin by liunan 2008-09-02
import java.util.StringTokenizer;
//CCEnd by liunan 2008-09-02

import com.faw_qm.affixattr.ejb.service.AffixAttrService;
import com.faw_qm.affixattr.model.AttrDefineInfo;
import com.faw_qm.affixattr.model.AttrValueInfo;
import com.faw_qm.baseline.ejb.service.BaselineService;
import com.faw_qm.baseline.model.ManagedBaselineInfo;
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.exception.ContentException;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentItemInfo;
import com.faw_qm.content.model.FormatContentHolderIfc;
import com.faw_qm.content.util.ContentClientHelper;
import com.faw_qm.content.util.StreamUtil;
import com.faw_qm.doc.ejb.service.StandardDocService;
import com.faw_qm.doc.exception.DocException;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.util.DocFormData;
import com.faw_qm.domain.ejb.service.DomainService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.iba.value.ejb.service.IBAValueObjectsFactory;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.model.AbstractValueIfc;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.integration.model.Command;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.integration.model.Param;
import com.faw_qm.integration.model.Request;
import com.faw_qm.integration.model.Script;
import com.faw_qm.integration.util.QMProperties;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.ejb.entity.QMPart;
import com.faw_qm.part.ejb.extendedService.ExtendedPartService;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.users.ejb.service.UsersService;
import com.faw_qm.users.model.GroupInfo;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.QMThread;

public class PublishLoadHelper {

	public static Properties mapPro = null;
	
	//CCBegin SS1
	static boolean fileVaultUsed = (RemoteProperty.getProperty(
            "registryFileVaultStoreMode", "true")).equals("true");
	//CCEnd SS1

	protected static String ERROR_PATH;

	protected static String LOG_PATH;
	
	protected static String MAPP_PATH;
	static {
		try {
			ERROR_PATH = QMProperties.getLocalProperties().getProperty(
					"publish.error.path");
			LOG_PATH = QMProperties.getLocalProperties().getProperty(
					"publish.log.path");
			MAPP_PATH = QMProperties.getLocalProperties().getProperty(
	                "publish.mappingreceive.path");
			mapPro = new Properties();
			Properties old = new Properties();
			old.load(new FileInputStream(new
			//File("E:\\jfv4r3\\productfactory\\phosphor\\support\\publish\\config\\integration\\mappingreceive.properties")));
			//File("/opt/v4r3/jfupv4r3/support/publish/config/integration/mappingreceive.properties")));
			File(MAPP_PATH)));
			Enumeration e = old.keys();
			if (PublishHelper.VERBOSE)
				System.out.println("======>old pro amount: " + old.size());
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				if (PublishHelper.VERBOSE)
					System.out.println("key: "
							+ PublishHelper.strDecoding(key, "ISO8859-1")
							+ ",value: "
							+ PublishHelper.strDecoding(old.getProperty(key),
									"ISO8859-1"));
				mapPro.setProperty(PublishHelper.strDecoding(key, "ISO8859-1"),
						PublishHelper.strDecoding(old.getProperty(key),
								"ISO8859-1"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public PublishPartsLog logger = null;

	public PrintWriter errorWriter = null;

	private HashMap paramMap = new HashMap();

	// 用来缓存发布的或修订的part的编号
	public Vector partVector = new Vector();

	public Vector partsCannotStore = new Vector();// 用来保存不需要存储或是无法存储的零部件

	public Vector failedParts = new Vector();// 保存失败的零部件

	public Vector failedDocs = new Vector();// 保存失败的普通文档

        //CCBegin by liunan 2008-12-01
        public Vector faileAlternate = new Vector();//保存失败的替换件
        public Vector faileSubstitutes = new Vector();//保存失败的结构替换件
        //CCEnd by liunan 2008-12-01

	public Vector partNeedAcl=new Vector();//用于保存修要变更权限的修订零部件

	public static String DEFAULT_LIFECYCLE = null;

	/*
	 * 说明:对于零部件\文档的存储目录(文件柜/夹),当不指定存储目录时,则按源数据的存储位置进行存储，
	 * 要求目标服务器与发布源存在相同的目录结构，如果指定则忽略源数据的存储位置，按指定的目录进行存储。
	 */

	// 设置缺省的视图名
	public static String DEFAULT_VIEW = null;

	// 是否在零部件对象的IBA属性中记录发布源相关信息
	public boolean saveSourceInfoByIBA = true;

	public static boolean DEBUG = false;

	public static SimpleDateFormat logDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	public String userLog = null;

	// 记录数据来源（值为技术中心）
	public String dataSource = "技术中心";

	// 记录从技术中心发布此数据时的日期
	public String publishDate = null;

	// 记录以何种形式发布（采用通知书/更改通知书）
	public String publishForm = null;

	// 记录采用通知书或更改请求的编号
	public String noteNum = null;

	//
	public PersistService ps = null;

	public UserIfc user;

	public String primaryPartNum;

	public int zong = 0;

	public int sucnum = 0;

	public int docsucnum = 0;

	public int doczong = 0;

	public int resucnum = 0;

	public int rezong = 0;

	public int redocnum = 0;

	public int redoczong = 0;

	public int linkzong = 0;

	public int linksuc = 0;

  //CCBegin by liunan 2008-12-03
	public int altzong = 0; //替换件关联总数
	
	public int altnum = 0; //替换件关联成功总数
	
	public int subzong = 0; //结构替换件关联总数
	
	public int subnum = 0; //结构替换件关联成功总数
	//CCEnd by liunan 2008-12-03

	public int changeorderzong = 0;

	public String logFileName = "";

	// public static int connecttest = 1;

	private String structureBaseline = null;

	private String partsBaseline = null;

	public PublishLoadHelper() {
		try {
			QMProperties props = QMProperties.getLocalProperties();
			DEBUG = props.getProperty("com.faw_qm.PublishParts.debug", false);
			DEFAULT_LIFECYCLE = props.getProperty(
					"com.faw_qm.PublishParts.DEFAULT_LIFECYCLE", "Default");
			DEFAULT_VIEW = props.getProperty(
					"com.faw_qm.PublishParts.DEFAULT_VIEW", "Engineering");
			saveSourceInfoByIBA = props.getProperty(
					"com.faw_qm.PublishParts.SaveSourceInfoByIBA", true);
			userLog = props.getProperty("com.faw_qm.publishParts.userLogFile",
					null);
		} catch (Exception exception) {
			exception.printStackTrace(System.err);
			throw new ExceptionInInitializerError(exception);
		}
	}

	public class NewReturnThread extends QMThread {

		private Script script;

		private PublishLoadHelper loadHelper;

		public void run() {
			SessionService sservice = null;
			UsersService userservice = null;
			try {
				PublishHelper.setStateBusy();
				sservice = (SessionService) PublishHelper
						.getEJBService("SessionService");
				userservice = (UsersService) PublishHelper
						.getEJBService("UsersService");
				if (PublishHelper.VERBOSE)
					System.out.println("****** 进入业务处理线程" + "the script is :"
							+ script != null);

				Command cmd = script.getCommand();
				String cmdName = cmd.getName();
				// user = sservice.getCurUserInfo();
				user = userservice.getUserValueInfo("rdc");
				//CCBegin by liunan 2008-11-14 添加rdc临时管理员权限。
				PublishHelper.setStateBusy();
				sservice.setAccessEnforced(false);
				//sservice.setAdministrator();
				//CCEnd by liunan 2008-11-14
				log("进行数据发布的用户是：" + user.getUsersName());
				// 记录数据来源（值为技术中心）
				// 记录从技术中心发布此数据时的日期
				publishDate = cmd.paramValue("PUBLISHDATE");
				// 记录以何种形式发布（采用通知书/更改通知书）
				publishForm = cmd.paramValue("PUBLISHFORM");
				// 记录采用通知书或更改请求的编号
				noteNum = cmd.paramValue("NOTENUMBER");
				errorWriter = new PrintWriter(new FileWriter(new File(
						ERROR_PATH + noteNum + ".log"), false), true);
				primaryPartNum = cmd.paramValue("PRIMARYPART");
				logger = new PublishPartsLog(noteNum);
				ps = (PersistService) PublishHelper
						.getEJBService("PersistService");
				// log(script.getVdb().toXML());
				// 读传入输入组信息
				Group partsCreateGrp = null;
				Group partsReviseGrp = null;
				Group partUsageLinksGrp = null;
				Group partDescsGrp = null;
				Group docsCreateGrp = null;
				Group docsReviseGrp = null;
				Group partsIBAAttr = null;
				Group changeOrders = null;
				//CCBegin by liunan 2008-12-01
				Group partAlternatesGrp = null;
				Group partSubstitutesGrp = null;
				//CCEnd by liunan 2008-12-01
				partsCreateGrp = script.getGroupIn("CreateParts");
				partsReviseGrp = script.getGroupIn("ReviseParts");
				partUsageLinksGrp = script.getGroupIn("PartUsageLinks");
				// partRefsGrp = script.getGroupIn("PartRefrenceLinks");
				partDescsGrp = script.getGroupIn("PartDescribeLinks");
				docsCreateGrp = script.getGroupIn("CreateDocs");
				docsReviseGrp = script.getGroupIn("ReviseDocs");
				changeOrders = script.getGroupIn("CHANGEORDERS");
				//CCBegin by liunan 2008-12-01
				partAlternatesGrp = script.getGroupIn("AlternatesLinks");
			//System.out.println("=========load=========="+partAlternatesGrp);
				partSubstitutesGrp = script.getGroupIn("SubstitutesLinks");
			//System.out.println("=========load=========="+partSubstitutesGrp);
				//CCEnd by liunan 2008-12-01
				partsCreateGrp = loadHelper.filterateCreateParts(
						partsCreateGrp, partsReviseGrp);// 为了防止验证信息不准确，重新进行验证
				partsReviseGrp = loadHelper
						.filterateReviseParts(partsReviseGrp);// 为了防止验证信息不准确，重新进行验证
				docsCreateGrp = loadHelper.filterateCreateDocs(docsCreateGrp,
						docsReviseGrp);// 为了防止验证信息不准确，重新进行验证
				docsReviseGrp = loadHelper.filterateReviseDocs(docsReviseGrp);

				partsIBAAttr = script.getGroupIn("PartIBAAttrs");
				Hashtable partIBA = PublishHelper.initIBA(partsIBAAttr);
				Enumeration params = cmd.getParams("PARAM");
				String tempStr = null;
				while (params.hasMoreElements()) {
					Param tmp = (Param) params.nextElement();
					if (tmp != null) {
						tempStr = tmp.getData();
					} else {
						continue;
					}

					int i = tempStr.indexOf('=');
					if (i > 0) {
						String s1 = tempStr.substring(0, i);
						String s2 = tempStr.substring(i + 1);

						if (s2 != null && !s2.trim().equals("")) {
							paramMap.put(s1, s2);
						}
					}
				}
				SimpleDateFormat logDateFormat = new SimpleDateFormat(
						"yyyy-MM-dd-HH:mm:ss.SSS");
				userLog("#进行数据接收的用户是：" + user.getUsersName());
				userLog("#本次发布数据接收的时间是："
						+ logDateFormat.format(new java.util.Date()));
				// 创建反馈信息

				log("创建Part: "+noteNum);
				if (partsCreateGrp != null) {
					PartCreateDelegate del = new PartCreateDelegate(
							partsCreateGrp, partIBA, loadHelper);
					ResultReport res = del.process();
					log("创建创建part数据 RESURLT ***************");
					log(res);
				}

				// 创建Doc
				log("创建Doc: "+noteNum);
				if (docsCreateGrp != null) {
					DocCreateDelegate del = new DocCreateDelegate(
							docsCreateGrp, loadHelper);
					ResultReport res = del.process();
					log("创建创建Doc数据 RESURLT ***************");
					log(res);
				}

				// 修订Part
				log("修订Part: "+noteNum);
				SessionService sservice1 = (SessionService) PublishHelper
						.getEJBService("SessionService");
				System.out.println("********************loadhelper*********************=="+sservice1.isAccessEnforced());
				if (partsReviseGrp != null) {
					PartReviseDelegate del = new PartReviseDelegate(
							partsReviseGrp, partIBA, loadHelper);
					ResultReport res = del.process();
					log("修订Part数据***************");
					log(res);
				}

				// 修订Doc
				log("修订Doc: "+noteNum);
				if (docsReviseGrp != null) {
					DocReviseDelegate del = new DocReviseDelegate(
							docsReviseGrp, loadHelper);
					ResultReport res = del.process();
					log("修订Doc数据 RESURLT ***************");
					log(res);

				}

				// 创建Part结构
				RelationsDelegate relationsDel = new RelationsDelegate(
						this.loadHelper);
				log("创建Part结构: "+noteNum);
				if (partUsageLinksGrp != null) {
					ResultReport res = relationsDel
							.savePartUsageLinks(partUsageLinksGrp);
					log("创建Part结构数据 RESULT ***************");
					log(res);
				}

				// 创建Part与Doc描述关系
				log("创建Part与Doc描述关系: "+noteNum);
				if (partDescsGrp != null) {
					ResultReport res = relationsDel
							.savePartDescribeLinks(partDescsGrp);
					log("创建Part与Doc描述关系数据 RESULT ***************");
					log(res);

				}
				
				// 创建变更单文档
				log("创建技术中心变更单文档: "+noteNum);
				if (changeOrders != null && changeOrders.getElementCount() != 0) {
					ChangeOrderCreateDelegate dele = new ChangeOrderCreateDelegate(
							changeOrders, loadHelper);
					ResultReport res = dele.process();
					log("创建技术中心变更单文档 RESULT ***************");
					log(res);
				}

				//CCBegin by liunan 2008-12-01
				// 创建Part与替换件关联关系
				log("创建Part与alternate关联关系: "+noteNum);
				if (partAlternatesGrp != null) {
					ResultReport res = relationsDel
							.savePartAlternatesLinks(partAlternatesGrp);
					log("创建Part与替换件关联关系数据 RESULT ***************");
					log(res);
				}
				// 创建Part与结构替换件关联关系
				log("创建Part与subtitutes关联关系: "+noteNum);
				if (partSubstitutesGrp != null) {
					ResultReport res = relationsDel
							.savePartSubtitutesLinks(partSubstitutesGrp);
					log("创建Part与结构替换件关联关系数据 RESULT ***************");
					log(res);
				}
				//CCEnd by liunan 2008-12-01

				delWithAcl();//2007年10月，设置动态权限
				//processBaseLine();//anan want 不用再添加基线了。
				generateLog();
				if (this.loadHelper.errorWriter != null) {
					this.loadHelper.errorWriter.close();
				}
				//CCBegin by liunan 2008-11-14 取消rdc临时管理员权限。
			  sservice.setAccessEnforced(true);
        //sservice.freeAdministrator();
			} catch (Exception e) {
		   	/*try {
			  	sservice.setAccessEnforced(true);
			  	sservice.freeAdministrator();
			  } catch (QMException e1) {
				  e1.printStackTrace();
			  }*/
				e.printStackTrace();
			} finally {
			/*try {
				PublishHelper.setStateFree();
				sservice.setAccessEnforced(true);
				} catch (QMException e1) {
				e1.printStackTrace();
			}*/
			  //CCEnd by liunan 2008-11-14
			}

		}

		public NewReturnThread(Script scripts, PublishLoadHelper helper) {
			this.script = scripts;
			this.loadHelper = helper;
		}
	}

	public void loadDataByScript(Script script) {
		NewReturnThread newthread = new NewReturnThread(script, this);
		newthread.start();
	}

	public void loadDataByFileName(String xmlFileName) {
		try {
			Request req = new Request(RequestIn.readRequest2(xmlFileName));
			NewReturnThread newthread = new NewReturnThread(req.getScript(),
					this);
			newthread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processBaseLine() throws QMException {
		QMTransaction transaction = new QMTransaction();
		transaction.begin();
		try {
			log("进行创建基线的用户是：" + user.getUsersName());
			BaselineService bslsvr = (BaselineService) PublishHelper
					.getEJBService("BaselineService");
			PersistService pservice = (PersistService) PublishHelper
					.getEJBService("PersistService");
			DomainService domain = (DomainService) PublishHelper
					.getEJBService("DomainService");
			String location = PublishHelper.strDecoding(mapPro
					.getProperty("jfpublish.baseline.folder"), "ISO8859-1");
			String domainID = domain.getDomainID("System");
			String lifecycle = mapPro.getProperty(
					"jfpublish.baseline.lifecycletemplate", null);
			lifecycle = PublishHelper.strDecoding(lifecycle, "ISO8859-1");
			String lifeCyID = PublishHelper.getLifeCyID(lifecycle);

			String seqNum = pservice.getNextSequence("baselineIdentity_seq");
			ManagedBaselineInfo baseline = new ManagedBaselineInfo();// 将本次接收的零部件添加到该基线中
			baseline.setBaselineName("数据接收基线" + noteNum + seqNum);
			baseline.setBaselineNumber(noteNum + seqNum);
			if (location != null && location.length() > 0) {
				baseline.setLocation(location);

			}
			baseline.setDomain(domainID);
			baseline.setLifeCycleTemplate(lifeCyID);
			baseline = (ManagedBaselineInfo) pservice.storeValueInfo(baseline,
					false);
			String partNum = null;
			QMPartIfc part = null;
			for (Iterator ite = partVector.iterator(); ite.hasNext();) {
				partNum = (String) ite.next();
				part = PublishHelper.getPartInfoByNumber(partNum);
				bslsvr.addToBaseline(part, baseline);
			}
			// 创建基线，并将本次发布的根部件按结构添加到该基线中
			seqNum = pservice.getNextSequence("baselineIdentity_seq");
			ManagedBaselineInfo newBaseline = new ManagedBaselineInfo();
			newBaseline.setBaselineName("零部件基线" + primaryPartNum + seqNum);
			newBaseline.setBaselineNumber(primaryPartNum + seqNum);
			if (location != null && location.length() > 0) {
				newBaseline.setLocation(location);

			}
			newBaseline.setDomain(domainID);
			newBaseline = (ManagedBaselineInfo) pservice.storeValueInfo(
					newBaseline, false);
			QMPartIfc primarypart = PublishHelper
					.getPartInfoByNumber(primaryPartNum);
			StandardPartService standardservice = (StandardPartService) PublishHelper
					.getEJBService("StandardPartService");
			// 获取当前用户的配置规范
			PartConfigSpecIfc spec = standardservice.findPartConfigSpecIfc();
			ExtendedPartService extendservice = (ExtendedPartService) PublishHelper
					.getEJBService("ExtendedPartService");
			if (primarypart != null) {
				extendservice.populateBaseline(primarypart, newBaseline, spec);

			}
			structureBaseline = newBaseline.getBaselineNumber();
			partsBaseline = baseline.getBaselineNumber();
			transaction.commit();

		} catch (QMException e) {

			transaction.rollback();
			e.printStackTrace();
			errorLog("基线创建或添加失败，可通过浏览日志文件查看发布信息");
		}

	}

	protected Group filterateCreateParts(Group createParts, Group reviseParts) {
		Group result = new Group();
		if (createParts == null) {
			return result;
		}
		Enumeration enu = createParts.getElements();
		while (enu.hasMoreElements()) {
			Element ele = (Element) enu.nextElement();
			String num = ((String) ele.getValue("num")).trim();
			String sourceVersion = (String) ele.getValue("sourceVersion");
			boolean isgpart = ((String) ele.getValue("type")).toLowerCase()
					.trim().equals("genericpart");
			QMPartIfc part = null;
			if (isgpart) {
				try {
					part = PublishHelper.getGPartInfoByNumber(num);
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (part != null) {
					try {
						// 比较part中记录的技术中心发布的版本和当前技术中心发布的版本是否相同，如果不同
						// 并且
						// 发布件的版本高于记录的版本
						// 该件作为修订件处理
						String s = getSourceVersion(part);

                                                  //CCBegin by liunan 2008-09-02
                                                          //修改以前的版本比较方法，无法正确比较出AA与B、C、D等版本的大小。
                                                          //改用产品中版本服务采用的版本比较方法。
                                                          //if (sourceVersion.compareTo(s) > 0) {
                                                //System.out.println(sourceVersion+"==============filterateCreateParts()的Gpart============="+s);
                                                if(getPublishFlag(sourceVersion,s)){
                                                //CCEnd by liunan 2008-09-02
							reviseParts.addElement(ele);
						} else {
							if (PublishHelper.VERBOSE)
								System.out.println("已存在");
							this.partsCannotStore.add(part.getPartNumber());
							continue;
						}
					} catch (QMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					result.addElement(ele);
				}
			} else {
				try {
					part = PublishHelper.getPartInfoByNumber(num);
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (part != null) {
					try {
						String s = getSourceVersion(part);
                                                //CCBegin by liunan 2008-09-02
                                                        //修改以前的版本比较方法，无法正确比较出AA与B、C、D等版本的大小。
                                                        //改用产品中版本服务采用的版本比较方法。
                                                        //if (sourceVersion.compareTo(s) > 0) {
                                              //System.out.println(sourceVersion+"==============filterateCreateParts()的part============="+s);
                                              if(getPublishFlag(sourceVersion,s)){
                                              //CCEnd by liunan 2008-09-02

							// 修订零部件
							reviseParts.addElement(ele);
						} else {
							if (PublishHelper.VERBOSE)
								System.out.println("已存在");
							this.partsCannotStore.add(part.getPartNumber());
							continue;
						}

					} catch (QMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					result.addElement(ele);
				}
			}
		}
		return result;
	}

	protected Group filterateReviseParts(Group reviseParts) {
		Group result = new Group();
		if (reviseParts == null) {
			return result;
		}
		Enumeration enu = reviseParts.getElements();
		while (enu.hasMoreElements()) {
			Element ele = (Element) enu.nextElement();
			String num = ((String) ele.getValue("num")).trim();
			String sourceVersion = (String) ele.getValue("sourceVersion");
			String lifecyclestate =  (String)ele.getValue("lifecyclestate");
			boolean isgpart = ((String) ele.getValue("type")).toLowerCase()
					.trim().equals("genericpart");
			QMPartIfc part = null;
			if (isgpart) {
				try {
					part = PublishHelper.getGPartInfoByNumber(num);
					
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (part != null) {
					try {
						//CCBegin by liunan 2009-01-21
						String partState = part.getLifeCycleState().toString();
						String partnote = part.getIterationNote();
						//CCEnd by liunan 2009-01-21
						// 比较part中记录的技术中心发布的版本和当前技术中心发布的版本是否相同，如果不同
						// 并且
						// 发布件的版本高于记录的版本
						// 该件作为修订件处理
						String s = getSourceVersion(part);
                                                //CCBegin by liunan 2008-09-02
                                                        //修改以前的版本比较方法，无法正确比较出AA与B、C、D等版本的大小。
                                                        //改用产品中版本服务采用的版本比较方法。
                                                        //if (sourceVersion.compareTo(s) > 0) {
                                              //System.out.println(sourceVersion+"==============filterateReviseParts()的Gpart============="+s+getPublishFlag(sourceVersion,s));
                                              if(getPublishFlag(sourceVersion,s)){
                                              //CCEnd by liunan 2008-09-02

							result.addElement(ele);
						}
						//CCBegin by liunan 2009-01-21
						//如果版本一样，则比较生命周期状态，汽研为生准，解放为非生准，则发布，但实际保存时只作状态和采用、更改通知书属性修改。
						//CCBegin by liunan 2012-05-14 存在生产状态后，再把生准状态发布过来的问题。
						//else if(sourceVersion.equals(s)&&!partState.equals("PREPARING")&&lifecyclestate.equals("PREPARE"))
						else if(sourceVersion.equals(s)&&!partState.equals("PREPARING")&&!partState.equals("MANUFACTURING")&&lifecyclestate.equals("PREPARE"))
						//CCEnd by liunan 2012-05-14
						{
							result.addElement(ele);
						}
						//CCBegin by liunan 2011-08-01
						//如果版本一样，则比较生命周期状态，汽研为前准，解放为非生准且非前准，则发布，但实际保存时只作状态和采用、更改通知书属性修改。
						else if(sourceVersion.equals(s)&&!partState.equals("PREPARING")&&!partState.equals("ADVANCEPREPARE")&&lifecyclestate.equals("ADVANCEPREPARE"))
						{
							result.addElement(ele);
						}
						//CCEnd by liunan 2011-08-01
						//如果版本一样，则比较生命周期状态，汽研为生准，解放为生准，且解放的“版本注释”属性含有“前准”字样，则发布，但实际保存时只作状态和采用、更改通知书属性修改。
            else if(sourceVersion.equals(s)&&partState.equals("PREPARING")&&partnote!=null&&partnote.indexOf("前准")>=0&&lifecyclestate.equals("PREPARE"))
            {
            	result.addElement(ele);
            }
            //CCEnd by liunan 2009-01-21
						else {
							if (PublishHelper.VERBOSE)
								System.out.println("=====>广义部件：" + num
										+ "不需要修订");
							this.partsCannotStore.add(part.getPartNumber());
							continue;
						}
					} catch (QMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					if (PublishHelper.VERBOSE)
						System.out.println("=====>广义部件：" + num + "已被删除");
				}
			} else {
				try {
					part = PublishHelper.getPartInfoByNumber(num);
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (part != null) {
					try {
						//CCBegin by liunan 2009-01-21
						String partState = part.getLifeCycleState().toString();
						String partnote = part.getIterationNote();
						//CCEnd by liunan 2009-01-21
						String s = getSourceVersion(part);
                                          //CCBegin by liunan 2008-09-02
                                                  //修改以前的版本比较方法，无法正确比较出AA与B、C、D等版本的大小。
                                                  //改用产品中版本服务采用的版本比较方法。
                                                  //if (sourceVersion.compareTo(s) > 0) {
                                        //System.out.println(sourceVersion+"==============filterateReviseParts()的part============="+s);
                                        if(getPublishFlag(sourceVersion,s)){
                                        //CCEnd by liunan 2008-09-02

							// 修订零部件
							result.addElement(ele);
						}
						//CCBegin by liunan 2009-01-21
						//如果版本一样，则比较生命周期状态，汽研为生准，解放为非生准，则发布，但实际保存时只作状态和采用、更改通知书属性修改。
						//CCBegin by liunan 2012-05-14 存在生产状态后，再把生准状态发布过来的问题。
						//else if(sourceVersion.equals(s)&&!partState.equals("PREPARING")&&lifecyclestate.equals("PREPARE"))
						else if(sourceVersion.equals(s)&&!partState.equals("PREPARING")&&!partState.equals("MANUFACTURING")&&lifecyclestate.equals("PREPARE"))
						//CCEnd by liunan 2012-05-14
						{
							result.addElement(ele);
						}
						//CCBegin by liunan 2011-08-01
						//如果版本一样，则比较生命周期状态，汽研为前准，解放为非生准且非前准，则发布，但实际保存时只作状态和采用、更改通知书属性修改。
						else if(sourceVersion.equals(s)&&!partState.equals("PREPARING")&&!partState.equals("ADVANCEPREPARE")&&lifecyclestate.equals("ADVANCEPREPARE"))
						{
							result.addElement(ele);
						}
						//CCEnd by liunan 2011-08-01
						//如果版本一样，则比较生命周期状态，汽研为生准，解放为生准，且解放的“版本注释”属性含有“前准”字样，则发布，但实际保存时只作状态和采用、更改通知书属性修改。
						//CCBegin by liunan 2011-04-22 前准判断由>改成>=
            else if(sourceVersion.equals(s)&&partState.equals("PREPARING")&&partnote!=null&&partnote.indexOf("前准")>=0&&lifecyclestate.equals("PREPARE"))
            {
            	result.addElement(ele);
            }
            //CCEnd by liunan 2009-01-21
            else {
							if (PublishHelper.VERBOSE)
								System.out
										.println("=====>零部件：" + num + "不需要修订");
							this.partsCannotStore.add(part.getPartNumber());
							continue;
						}

					} catch (QMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					if (PublishHelper.VERBOSE)
						System.out.println("=====>零部件：" + num + "已被删除");
				}
			}
		}
		return result;
	}

	protected Group filterateCreateDocs(Group createDocs, Group reviseDocs) {
		Group result = new Group();
		if (createDocs == null) {
			return result;
		}
		AffixAttrService affService = null;
		try {
			affService = (AffixAttrService) PublishHelper
					.getEJBService("AffixAttrService");
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PersistService ps = null;
		try {
			ps = (PersistService) PublishHelper.getEJBService("PersistService");
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Enumeration enu = createDocs.getElements();
		while (enu.hasMoreElements()) {
			Element ele = (Element) enu.nextElement();
			String num = ((String) ele.getValue("num")).trim();
			String sourceVersion = (String) ele.getValue("sourceVersion");
			DocInfo doc = null;
			try {
				doc = PublishHelper.getDocInfoByNumber(num);
			} catch (QMException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (doc != null) {
				Vector attvalues = null;
				try {
					attvalues = affService.getAffixAttr(doc);
				} catch (QMException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String preSourceversion = null;
				int i = attvalues.size();
				for (int j = 0; j < i; j++) {
					AttrValueInfo attvalue = (AttrValueInfo) attvalues
							.elementAt(j);
					// 通过附加属性值的对象取得属性定义的ID并刷出值对象
					// 通过附加属性值的对象取得属性定义的ID并刷出值对象
					AttrDefineInfo attDefine = null;
					try {
						attDefine = (AttrDefineInfo) ps.refreshInfo(attvalue
								.getAttrDefID(), false);
					} catch (QMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// 如果属性定义的名称等于"version"，取出附加属性值与接收的数据比较
					if (attDefine.getAttrName().toLowerCase().trim().equals(
							"sourceversion")) {
						preSourceversion = attvalue.getValue();
						break;
					}
				}
				if (preSourceversion == null) {
					preSourceversion = "";
				}
                                //CCBegin by liunan 2008-09-02
                                        //修改以前的版本比较方法，无法正确比较出AA与B、C、D等版本的大小。
                                        //改用产品中版本服务采用的版本比较方法。
                                        //if (sourceVersion.compareTo(preSourceversion) > 0) {
                              //System.out.println(sourceVersion+"==============filterateCreateDocs()============="+preSourceversion);
                              if(getPublishFlag(sourceVersion,preSourceversion)){
                              //CCEnd by liunan 2008-09-02

					reviseDocs.addElement(ele);
				} else {
					if (PublishHelper.VERBOSE)
						System.out.println("已存在");
					continue;
				}

			} else {
				result.addElement(ele);
			}
		}
		return result;
	}

	protected Group filterateReviseDocs(Group reviseDocs) {
		Group result = new Group();
		if (reviseDocs == null) {
			return result;
		}
		AffixAttrService affService = null;
		try {
			affService = (AffixAttrService) PublishHelper
					.getEJBService("AffixAttrService");
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PersistService ps = null;
		try {
			ps = (PersistService) PublishHelper.getEJBService("PersistService");
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Enumeration enu = reviseDocs.getElements();
		while (enu.hasMoreElements()) {
			Element ele = (Element) enu.nextElement();
			String num = ((String) ele.getValue("num")).trim();
			String sourceVersion = (String) ele.getValue("sourceVersion");
			DocInfo doc = null;
			try {
				doc = PublishHelper.getDocInfoByNumber(num);
			} catch (QMException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (doc != null) {
				Vector attvalues = null;
				try {
					attvalues = affService.getAffixAttr(doc);
				} catch (QMException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String preSourceversion = null;
				int i = attvalues.size();
				for (int j = 0; j < i; j++) {
					AttrValueInfo attvalue = (AttrValueInfo) attvalues
							.elementAt(j);
					// 通过附加属性值的对象取得属性定义的ID并刷出值对象
					// 通过附加属性值的对象取得属性定义的ID并刷出值对象
					AttrDefineInfo attDefine = null;
					try {
						attDefine = (AttrDefineInfo) ps.refreshInfo(attvalue
								.getAttrDefID(), false);
					} catch (QMException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// 如果属性定义的名称等于"version"，取出附加属性值与接收的数据比较
					if (attDefine.getAttrName().toLowerCase().trim().equals(
							"sourceversion")) {
						preSourceversion = attvalue.getValue();
						break;
					}
				}
				if (preSourceversion == null) {
					preSourceversion = "";
				}
                                //CCBegin by liunan 2008-09-02
                                        //修改以前的版本比较方法，无法正确比较出AA与B、C、D等版本的大小。
                                        //改用产品中版本服务采用的版本比较方法。
                                        //if (sourceVersion.compareTo(preSourceversion) > 0) {
                              //System.out.println(sourceVersion+"==============filterateReviseDocs()============="+preSourceversion);
                              if(getPublishFlag(sourceVersion,preSourceversion)){
                              //CCEnd by liunan 2008-09-02

					result.addElement(ele);
				} else {
					if (PublishHelper.VERBOSE)
						System.out.println("=====>描述文档：" + num + "不需要修订");
					continue;
				}

			} else {
				if (PublishHelper.VERBOSE)
					System.out.println("=====>描述文档：" + num + "已被删除");
			}
		}
		return result;
	}


	private String getSourceVersion(IBAHolderIfc holder) throws QMException {
		IBAValueService ibaService = (IBAValueService) PublishHelper
				.getEJBService("IBAValueService");
		holder = (IBAHolderIfc) ibaService
				.refreshAttributeContainerWithoutConstraints(holder);
		DefaultAttributeContainer attc = (DefaultAttributeContainer) holder
				.getAttributeContainer();
		AbstractValueView[] valueview = attc.getAttributeValues();
		int m = valueview.length;
		String fbsourversion = "";
		for (int i = 0; i < m; i++) {
			if ((valueview[i].getDefinition().getName()).toLowerCase().trim()
					.equals("sourceversion")) {
				AbstractValueIfc value = IBAValueObjectsFactory
						.newAttributeValue(valueview[i], holder);
				fbsourversion = ((StringValueIfc) value).getValue();
				break;
			}
		}
		return fbsourversion;
	}

	protected void generateLog() {
		int m = 0;
		int n = 0;
		int a = 0;
		int b = 0;
		String docnum = noteNum;
		String path = userLog + "userpublish" + noteNum + "0.log";
		File file = new File(userLog);
		boolean ischange = true;
		if (file.exists()) {
			path = userLog + "userpublish" + noteNum + "0.log";
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				int j = files[i].getPath().indexOf(noteNum);

				if (j != -1) {
					String s = files[i].getPath().substring(
							j + noteNum.length(), j + noteNum.length() + 1);
					try {
						m = Integer.valueOf(s).intValue();
						if (m > n) {
							n = m;
							path = files[i].getPath();

						}
					} catch (Exception e) {
						continue;
					}
					ischange = false;
					docnum = noteNum;
				}
			}
		}
		if (file.exists() && ischange) {
			path = userLog + "changepublish0.log";
			File[] changefiles = file.listFiles();
			for (int i = 0; i < changefiles.length; i++) {
				int k = changefiles[i].getPath().indexOf("changepublish");
				int l = changefiles[i].getPath().lastIndexOf(".");
				if (k != -1) {
					String s = changefiles[i].getPath().substring(k + 13, l);
					a = Integer.valueOf(s).intValue();
					if (a > b) {
						b = a;
						path = changefiles[i].getPath();

					}
				}

			}
			docnum = "changepublish" + b;
		}

		// System.out.println("---------------------------->>>"+path);
		//CCBegin by liunan 2008-12-03 添加对替换件和结构替换件的支持。
		JieFangTemplateProcessor jfTemplateProcessor = new JieFangTemplateProcessor(
				path, user.getUsersName(), zong, sucnum, doczong, docsucnum,
				rezong, resucnum, redoczong, redocnum,
				linkzong - linksuc, altzong, altnum, subzong, subnum);
		//CCEnd by liunan 2008-12-03

		// 在指定目录生成日志的html文件
		logFileName = LOG_PATH + noteNum + "-"
				+ logDateFormat.format(new java.util.Date()) + ".html";
		// System.out.println("--------------------------->>>"+logFileName);

		try {
			JieFangTemplateProcessor.writeFile(jfTemplateProcessor
					.getLogContent(), logFileName);
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// 通过文档管理日志文件
		// 通过文档管理日志文件
		DocInfo docInfo = null;
		try {
			docInfo = PublishHelper.getDocInfoByNumber("CONFIRMATION-"
					+ noteNum);
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (docInfo == null) {
			return;
		}
		StandardDocService ser = null;
		try {
			ser = (StandardDocService) PublishHelper
					.getEJBService("StandardDocService");
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ContentService cService = null;
		try {
			cService = (ContentService) PublishHelper
					.getEJBService("ContentService");
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		File variableFile = new File(logFileName);
		DataInputStream dataInputStream = null;
		byte[] oldFileBytes = null;
		try {
			oldFileBytes = new byte[(int) variableFile.length()];
			dataInputStream = new DataInputStream(new FileInputStream(
					variableFile));
			dataInputStream.read(oldFileBytes);
			dataInputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//CCBegin SS1
		if(fileVaultUsed)
		{
			ContentClientHelper helper = new ContentClientHelper();
			ApplicationDataInfo appDataInfo = null;
			try {
				appDataInfo = helper.requestUpload((byte[]) oldFileBytes);
			} catch (QMException e) {
				e.printStackTrace();
			}
			try
			{
				appDataInfo.setFileName("基础数据发布结果统计.html");
				appDataInfo.setUploadPath(LOG_PATH);
				appDataInfo = (ApplicationDataInfo) cService.uploadContent(
						(FormatContentHolderIfc) docInfo, appDataInfo);
			} catch (ContentException e) {
				e.printStackTrace();
			} catch (QMException e) {
				e.printStackTrace();
			}
		}
		else
		{
			ApplicationDataInfo appDataInfo = new ApplicationDataInfo();
			appDataInfo.setFileName("基础数据发布结果统计.html");
			//CCBegin by liunan 2010-01-29
			appDataInfo.setUploadPath(LOG_PATH);
			//CCEnd by liunan 2010-01-29
			appDataInfo.setFileSize(variableFile.length());
			try {
				appDataInfo = (ApplicationDataInfo) cService.uploadContent(
						(FormatContentHolderIfc) docInfo, appDataInfo);
			} catch (ContentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String streamId = appDataInfo.getStreamDataID();
			try {
				StreamUtil.writeData(streamId, (byte[]) oldFileBytes);
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//CCEnd SS1
		if (variableFile.isFile()) {
			variableFile.delete();
		}
		if (PublishHelper.VERBOSE)
			System.out.println("************** 基线创建结束");
	}

	protected void delWithAcl() {
		if(this.partNeedAcl.size()>0)
		{
			Iterator iter=this.partNeedAcl.iterator();
			while(iter.hasNext())
			{
			    QMPartIfc part=(QMPartIfc) iter.next();
			    //System.out.println("--------------------------->"+part.getPartNumber());
			    PublishHelper.addXRight(part);
			}
		}
	}

	protected void log(Object msg) {

		PublishPartsLog.log(msg);

	}

	protected void errorLog(Object msg) {
		if (msg instanceof Throwable) {
			PublishPartsLog.log((Throwable) msg);
		} else {
			PublishPartsLog.log("*****ERROR: " + msg);
		}
	}

	protected void userLog(Object obj) {

		logger.userLog(obj);

	}

	public void logErrorPart(String partNum) {
		this.errorWriter.println("prt$$$$$" + partNum);
	}

	public void logErrorDoc(String docNum) {
		this.errorWriter.println("doc$$$$$" + docNum);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
        /**
         * 比较两个版本的大小，决定是否发布此对象。
         * @param s1 String 汽研最新版本。
         * @param s2 String 解放pdm中记录的该对象对应汽研的版本。
         * @return boolean 需要发布时返回true，否则返回false。
         * @author liunan 2008-10-04
         */
        private boolean getPublishFlag(String s1, String s2)
        {
          if(s1.indexOf(".")<0)
          {
            s1 = s1 + ".1";
          }
          if (s1.equals(s2)) {
            return false;
          }

          //利用StringTokenizer分割st1
          StringTokenizer st1 = new StringTokenizer(s1, ".");
          //利用StringTokenizer分割st2
          StringTokenizer st2 = new StringTokenizer(s2, ".");
          int level1 = st1.countTokens();
          int level2 = st2.countTokens();
          //两个版本值长度大为较新的
          if (level1 < level2) {
            return false;
          }
          if (level1 > level2) {
            return true;
          }
          String[] sarray1 = new String[level1];
          String[] sarray2 = new String[level2];
          //把分割后的字串，加入字符串数组
          for (int i = 0; i < level1; i++) {
            sarray1[i] = st1.nextToken();
            sarray2[i] = st2.nextToken();
          }
          //在循环中比较数组中的字串
          for (int k = 0; k < level1; k++) {
            if (sarray1[k].length() > sarray2[k].length()) {
              return true;
            }
            if (sarray1[k].length() < sarray2[k].length()) {
              return false;
            }
            if (sarray1[k].compareTo(sarray2[k]) < 0) {
              return false;
            }
            if (sarray1[k].compareTo(sarray2[k]) > 0) {
              return true;
            }
          }
          return false;
        }
}
