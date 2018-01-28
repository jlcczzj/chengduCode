//SS1 查看文档是增加对文档分类是否为空的判断。 liunan 2012-11-5
//SS2 零部件编号相同的情况除了part和gpart外，现在还多了变速箱的相同编号情况，qmpartmasterbsoid不同，有(BSXUP)后缀。 liunan 2013-7-24
//SS3 新能源数据发布 “数据接收确认通知书”文档页面添加关联“变更单”链接 liudongming 2013-10-21
//SS4 添加文件服务器传输逻辑 2013-12-10
//SS5 技术中心发零部件发布到中心设计视图 刘家坤 2014-7-8
//SS6 解放BOM整合 干涉检查 zhaoqiuying 2014-5-15 
//SS7 汽研windchill升级10.2，不发布epm把中性文件发布到part上。 liunan 2015-8-21
//SS8 发布试制任务单需要对描述中的零部件清单进行处理，生成试制清单和给解放试制的试制件设置试制任务单编号属性。 liunan 2017-6-27
//SS9 汽研任务单，解放流程卡的零部件提取。 liunan 2017-7-12
//SS10 如果变更前零部件是解放设计零部件，没有发布源版本，那么就找解放版本来定位零部件。 liunan 2017-7-18
//SS11 如果是试制任务单，则不通过源版本搜索。 liunan 2017-9-7

package com.faw_qm.jfpublish.receive;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

import com.faw_qm.acl.exception.InvalidAclEntryException;
import com.faw_qm.acl.model.AdHocControlledIfc;
import com.faw_qm.acl.util.AclEntrySet;
import com.faw_qm.acl.util.AdHocAcl;
import com.faw_qm.acl.util.AdHocAclHelper;
import com.faw_qm.affixattr.ejb.service.AffixAttrService;
import com.faw_qm.affixattr.model.AttrContainerInfo;
import com.faw_qm.affixattr.model.AttrDefineInfo;
import com.faw_qm.affixattr.model.AttrRestictInfo;
import com.faw_qm.baseline.ejb.service.BaselineService;
import com.faw_qm.baseline.model.ManagedBaselineIfc;
import com.faw_qm.baseline.model.ManagedBaselineInfo;
import com.faw_qm.config.ejb.service.ConfigService;
import com.faw_qm.content.ejb.entity.ContentItem;
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentItemIfc;
import com.faw_qm.content.model.StreamDataInfo;
import com.faw_qm.content.util.ContentClientHelper;
import com.faw_qm.csm.navigation.ClassificationNodeLoader;
import com.faw_qm.csm.navigation.ejb.service.NavigationObjectsFactory;
import com.faw_qm.csm.navigation.liteview.ClassificationNodeDefaultView;
import com.faw_qm.csm.navigation.liteview.ClassificationNodeNodeView;
import com.faw_qm.csm.navigation.liteview.ClassificationStructDefaultView;
import com.faw_qm.csm.navigation.model.ClassificationNodeIfc;
import com.faw_qm.csm.navigation.model.ClassificationNodeInfo;
import com.faw_qm.doc.ejb.service.StandardDocService;
import com.faw_qm.doc.model.DocClassificationInfo;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterIfc;
import com.faw_qm.doc.model.DocMasterInfo;
import com.faw_qm.doc.util.DocCfComponents;
import com.faw_qm.doc.util.DocServiceHelper;
import com.faw_qm.epm.build.model.EPMBuildHistoryIfc;
import com.faw_qm.epm.build.model.EPMBuildHistoryInfo;
import com.faw_qm.epm.build.model.EPMBuildLinksRuleInfo;
import com.faw_qm.epm.build.model.EPMBuildRuleIfc;
import com.faw_qm.epm.epmdocument.model.EPMDocumentIfc;
import com.faw_qm.epm.epmdocument.model.EPMDocumentInfo;
import com.faw_qm.epm.epmdocument.model.EPMDocumentMasterIfc;
import com.faw_qm.epm.epmdocument.model.EPMDocumentMasterInfo;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.folder.model.FolderEntryIfc;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.remote.RemoteProperty;
import com.faw_qm.framework.service.BaseService;
import com.faw_qm.framework.service.BaseValueIfc;
import com.faw_qm.framework.service.BaseValueInfo;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.framework.service.ServiceLocatorException;
import com.faw_qm.iba.definition.ejb.service.IBADefinitionObjectsFactory;
import com.faw_qm.iba.definition.ejb.service.IBADefinitionService;
import com.faw_qm.iba.definition.litedefinition.AttributeDefDefaultView;
import com.faw_qm.iba.definition.litedefinition.AttributeOrgNodeView;
import com.faw_qm.iba.definition.litedefinition.BooleanDefView;
import com.faw_qm.iba.definition.litedefinition.FloatDefView;
import com.faw_qm.iba.definition.litedefinition.IntegerDefView;
import com.faw_qm.iba.definition.litedefinition.RatioDefView;
import com.faw_qm.iba.definition.litedefinition.ReferenceDefView;
import com.faw_qm.iba.definition.litedefinition.StringDefView;
import com.faw_qm.iba.definition.litedefinition.TimestampDefView;
import com.faw_qm.iba.definition.litedefinition.URLDefView;
import com.faw_qm.iba.definition.litedefinition.UnitDefView;
import com.faw_qm.iba.definition.model.AttributeDefinitionIfc;
import com.faw_qm.iba.definition.model.UnitDefinitionInfo;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractContextualValueDefaultView;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.litevalue.BooleanValueDefaultView;
import com.faw_qm.iba.value.litevalue.FloatValueDefaultView;
import com.faw_qm.iba.value.litevalue.IntegerValueDefaultView;
import com.faw_qm.iba.value.litevalue.RatioValueDefaultView;
import com.faw_qm.iba.value.litevalue.ReferenceValueDefaultView;
import com.faw_qm.iba.value.litevalue.StringValueDefaultView;
import com.faw_qm.iba.value.litevalue.TimestampValueDefaultView;
import com.faw_qm.iba.value.litevalue.URLValueDefaultView;
import com.faw_qm.iba.value.litevalue.UnitValueDefaultView;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.model.ReferenceValueInfo;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.identity.DisplayIdentity;
import com.faw_qm.identity.IdentityFactory;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.integration.util.QMProperties;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartDescribeLinkIfc;
import com.faw_qm.part.model.PartDescribeLinkInfo;
import com.faw_qm.part.model.PartReferenceLinkIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartConfigSpecAssistant;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;
import com.faw_qm.pcfg.family.model.GenericPartInfo;
import com.faw_qm.pcfg.family.model.GenericPartMasterInfo;
import com.faw_qm.pcfg.family.model.GenericPartUsageLinkInfo;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.PersistHelper;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.units.exception.IncompatibleUnitsException;
import com.faw_qm.units.exception.UnitFormatException;
import com.faw_qm.units.model.QuantityOfMeasureIfc;
import com.faw_qm.units.util.DefaultUnitRenderer;
import com.faw_qm.units.util.MeasurementSystemCache;
import com.faw_qm.units.util.QuantityOfMeasureDefaultView;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.users.model.UserInfo;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.exception.VersionControlException;
import com.faw_qm.version.model.IteratedIfc;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
import com.faw_qm.viewmanage.model.ViewObjectInfo;
import com.faw_qm.wip.ejb.service.WorkInProgressService;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.wip.util.WorkInProgressHelper;
import com.faw_qm.part.util.PartServiceHelper;
//CCBegin SS8
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.net.URL;
import com.faw_qm.iba.definition.model.StringDefinitionIfc;
import com.faw_qm.iba.value.model.StringValueInfo;
import java.io.DataInputStream;
import com.faw_qm.content.util.StreamUtil;
//CCEnd SS8
/**
 * 发布数据的帮助类
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author 王海军
 * @version 1.0
 */
public class PublishHelper {
	
	//CCBegin by liunan 2008-09-03
	//将输出语句开关设置为false，不再进行调适语言输出。
	//源码如下。
	//public static final boolean VERBOSE = true;
	public static final boolean VERBOSE = false;
	//CCEnd by liunan 2008-09-03

	public static final String STATE_BUSY = "BUSY";

	public static final String STATE_FREE = "FREE";

	private static int publishState = 0;

	// 文档分类对象cache key--分类路径名 value--分类ID
	private static HashMap docCfMap = new HashMap();

	// 文档发布源扩展属性定义 key--定义名 value--定义ID
	private static HashMap docAffixDef = new HashMap();

	private static HashMap docAffixCon = new HashMap();

	// IBAcache key--属性定义或容器名 value--属性定义或容器
	private static HashMap ibaAttrDefMap = new HashMap();

	// 零部件cache, key -- 编号 value--零部件值对象 (用于基于树结构的部件删除)
	private static HashMap partMap = new HashMap();

	private static boolean LOG = true;

	private static boolean DEBUG = false;

	private static String Default_attrOrgName = "publishOrg";

	private static String Default_attrDefName_source = "dataSource";

	private static String Default_attrDefName_form = "publishForm";

	private static String Default_attrDefName_version = "sourceVersion";

	private static String Default_attrDefName_date = "publishDate";

	private static String Default_attrDefName_num = "noteNum";

	private static String Default_attrDefName_creater = "creater";

	private static String Default_attrDefName_modifier = "modifier";

	private static String Default_DocAffixAttrConName = "com.faw_qm.doc.ejb.entity.Doc";

	private static String Default_DocAffixAttrDefName_version = "sourceVersion";

	// 记录发布源相关信息:IBA属性组织器名
	private static String attrOrgName = "";

	// 记录发布源相关信息:IBA属性定义(发布源url)
	private static String attrDefName_source = "";

	private static String attrDefName_creater = "";

	private static String attrDefName_modifier = "";

	// 记录发布源相关信息:IBA属性定义(发布源id)
	private static String attrDefName_form = "";

	// 记录发布源相关信息:IBA属性定义(发布源大版本号)
	private static String attrDefName_version = "";

	// 记录发布源相关信息:IBA属性定义(发布源小版本号)
	private static String attrDefName_date = "";

	private static String attrDefName_num = "";

	// 记录发布源相关信息:文档扩展属性容器名
	// private String docAffixAttrConName;
	// 记录发布源相关信息:文档扩展属性约束名
	// private String docAffixAttrResName;
	// 记录发布源相关信息:文档扩展属性定义(发布url)
	// private String docAffixAttrDefName_url;
	// 记录发布源相关信息:文档扩展属性定义(发布源id)
	// private String docAffixAttrDefName_id;
	// 记录发布源相关信息:文档扩展属性定义(发布源大版本号)
	private static String docAffixAttrDefName_version;

	// 记录发布源相关信息:文档扩展属性定义(发布源小版本号)
	// private String docAffixAttrDefName_iteration;
	
	//CCBegin by liunan 2010-01-29 改成公有变量。
	public static String CHANGEORDER_DES_PART_SEPERATOR = "!$%*&";// 发布变更单时用于分隔变更变更单原有说明和变更单相关部件表示字符串

	public static String CHANGEORDER_PART_PART_SEPERATOR = "!*%#!";// 发布变更单时用于分隔变更单不同的部件的表示字符串

	public static String CHANGEORDER_PART_NUMBER_NAME_VERSION_SEPERATOR = "!*!";// 发布变更单时用于在变更单的某个相关部件表示字符串中分隔编号、名称和前后版本
	//CCEnd by liunan 2010-01-29
	
	//CCBegin SS4
	static boolean fileVaultUsed = (RemoteProperty.getProperty(
            "registryFileVaultStoreMode", "true")).equals("true");
	//CCEnd SS4
	
	static {
		try {
			QMProperties props = QMProperties.getLocalProperties();
			LOG = props.getProperty("com.faw_qm.PublishParts.log", true);
			DEBUG = props.getProperty("com.faw_qm.PublishParts.debug", false);
			attrOrgName = props.getProperty(
					"com.faw_qm.PublishParts.IBAAttrOrgName",
					Default_attrOrgName);
			attrDefName_source = props.getProperty(
					"com.faw_qm.PublishParts.IBAAttrDefName_source",
					Default_attrDefName_source);
			attrDefName_creater = "creater";
			attrDefName_modifier = "modifier";
			attrDefName_form = props.getProperty(
					"com.faw_qm.PublishParts.IBAAttrDefName_form",
					Default_attrDefName_form);
			attrDefName_version = props.getProperty(
					"com.faw_qm.PublishParts.IBAAttrDefName_version",
					Default_attrDefName_version);
			attrDefName_date = props.getProperty(
					"com.faw_qm.PublishParts.IBAAttrDefName_date",
					Default_attrDefName_date);
			attrDefName_num = props.getProperty(
					"com.faw_qm.PublishParts.IBAAttrDefName_num",
					Default_attrDefName_num);
			docAffixAttrDefName_version = props.getProperty(
					"com.faw_qm.PublishParts.DocAffixAttrDefName_version",
					Default_DocAffixAttrDefName_version);
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG = true;
		}
	}

	public static synchronized void setStateFree() {
		PublishHelper.publishState--;
		//CCBegin by liunan 2008-09-03
		//为输出语句加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("=====>PublishHelper.setStateFree,state="
				+ PublishHelper.publishState);
	}

	public static synchronized void setStateBusy() {
		PublishHelper.publishState++;
		//CCBegin by liunan 2008-09-03
		//为输出语句加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("=====>PublishHelper.setStateBusy,state="
				+ PublishHelper.publishState);
	}

	public static synchronized String getPublishState() {
		//CCBegin by liunan 2008-09-03
		//为输出语句加开关。
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("=====>PublishHelper.getPublishState,state="
				+ PublishHelper.publishState);
		if (PublishHelper.publishState == 0) {
			return PublishHelper.STATE_FREE;
		} else {
			return PublishHelper.STATE_BUSY;
		}
	}

	/**
	 * 根据业务对象的bsoID信息，获取对应的业务对象
	 * 
	 * @param bsoID
	 *            String 业务对象的id信息
	 * @throws QMException
	 * @return BaseValueInfo 对应的业务对象
	 */
	public static BaseValueInfo getQMPDMObject(String bsoID) throws QMException {
		BaseValueInfo obj = null;
		PersistService ser = (PersistService) EJBServiceHelper
				.getService("PersistService");
		obj = (BaseValueInfo) ser.refreshInfo(bsoID, false);
		return obj;
	}

	/**
	 * 根据属性容器名，获取属性容器ID信息
	 * 
	 * @param conName
	 *            String 属性容器名
	 * @throws QMException
	 * @return String 属性容器ID信息
	 */
	public static String getAttrContainID(String conName) throws QMException {
		AffixAttrService ser = (AffixAttrService) EJBServiceHelper
				.getService("AffixAttrService");
		AttrContainerInfo acInfo = ser.findContainerByName(conName);
		if (acInfo != null) {
			return acInfo.getBsoID();
		} else {
			return null;
		}
	}

	public static void createPartUsageLink(String parent, String parentVersion,
			String child, String id, String quan, String unit) {
		try {
			float quantity = Float.valueOf(quan).floatValue();
			VersionControlService versiobSer = (VersionControlService) PublishHelper
					.getEJBService("VersionControlService");
			StandardPartService ser = (StandardPartService) PublishHelper
					.getEJBService("StandardPartService");
			QMPartInfo parentPart = PublishHelper.getPartInfoByOrigInfo(parent,
					parentVersion);

			//CCBegin SS5
//			if (parentPart == null || parentPart.getViewName() == null
//					|| !(parentPart.getViewName().trim().equals("工程视图"))) {
				if (parentPart == null || parentPart.getViewName() == null
						|| !(parentPart.getViewName().trim().equals("中心设计视图"))) {
				//CCEnd SS5
	    	//CCBegin by liunan 2008-09-03
	    	//为输出语句加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartUsageLink, can't get parent！");
				return;
			}

			QMPartMasterInfo childMaster = null;
			QMPartIfc childPart = PublishHelper.getPartInfoByNumber(child);
			if (childPart == null) {
	    	//CCBegin by liunan 2008-09-03
	    	//为输出语句加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartUsageLink, can't get child！");
				return;
			}
			childMaster = (QMPartMasterInfo) childPart.getMaster();
			PartUsageLinkInfo usageLink = null;
			if (id != null) {
				usageLink = new GenericPartUsageLinkInfo();
				((GenericPartUsageLinkInfo) usageLink).setId(id);
			} else {
				usageLink = new PartUsageLinkInfo();
			}
			usageLink.setLeftBsoID(childMaster.getBsoID());
			usageLink.setRightBsoID(parentPart.getBsoID());
			QMQuantity qmquan = new QMQuantity();
			qmquan.setDefaultUnit(Unit.toUnit(unit) != null ? Unit.toUnit(unit)
					: Unit.getUnitDefault());
			qmquan.setQuantity(quantity);
			usageLink.setQMQuantity(qmquan);
			usageLink.setQuantity(quantity);
			// 设置默认单位(使用方式)
			if (unit == null || unit.trim().length() == 0) {
				usageLink.setDefaultUnit(Unit.getUnitDefault());
			} else {
				Unit unitT = Unit.toUnit(unit);
				if (unitT == null) {
					usageLink.setDefaultUnit(Unit.getUnitDefault());
				} else {

					usageLink.setDefaultUnit(unitT);
				}
			}
			PersistService ps = (PersistService) PublishHelper
					.getEJBService("PersistService");
			ps.saveValueInfo(usageLink);

		} catch (QMException ex) {
			ex.printStackTrace();
		}
	}

	public static void createPartEpmLink(String partNum, String epmNum) {
		try {
			VersionControlService versiobSer = (VersionControlService) PublishHelper
					.getEJBService("VersionControlService");
			QMPartMasterIfc partMaster = PublishHelper
					.getPartMasterByNumber(partNum);
			if (partMaster == null) {
	    	//CCBegin by liunan 2008-09-03
	    	//为输出语句加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartEpmLink, can't get part！");
				return;
			}
			QMPartInfo part = null;
			Collection coll = (Collection) versiobSer.allVersionsOf(partMaster);
			// 获得master下的所有小版本，按最新版序排列，
			// 则第一个元素即为最新版本版序的零部件对象
			Iterator iterator = coll.iterator();
			while (iterator.hasNext()) {
				part = (QMPartInfo) iterator.next();
				//CCBegin SS5
//				if (part.getViewName() != null
//						&& part.getViewName().trim().equals("工程视图")) {
				if (part.getViewName() != null
						&& part.getViewName().trim().equals("中心设计视图")) {
					//CCEnd SS5
					break;
				}
			}

			if (part == null) {
	    	//CCBegin by liunan 2008-09-03
	    	//为输出语句加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartEpmLink, can't get part！");
				return;
			}

			EPMDocumentInfo doc = PublishHelper.getEPMDocInfoByNumber(epmNum);

			if (doc == null) {
	    	//CCBegin by liunan 2008-09-03
	    	//为输出语句加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartEpmLink, can't get EPM！");
				return;
			}
			String docType = doc.getAuthoringApplication().getDisplay();
			// 检查是否存在关联关系
			//CCBegin by liunan 2009-11-09 改成通过EPMBuildHistory表来查找关联是否存在。
			//源代码
			//if (PublishHelper.hasBuildLinkRule(part, doc)) {
			if (PublishHelper.hasBuildHistory(part, doc)) 
			{
	    	//CCBegin by liunan 2008-09-03
	    	//为输出语句加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartEpmLink, relation exists！");
				return;
			}

			// 构造EPM对象与零部件对象之间的关联关系
			
			PersistService ps = (PersistService) PublishHelper.getEJBService("PersistService");
			if (!PublishHelper.hasBuildLinkRule(part, doc)) 
			{
				EPMBuildLinksRuleInfo tempRule = new EPMBuildLinksRuleInfo(doc, part);
				tempRule.setSourceInstance(doc.getFamilyInstance());
				tempRule.setDescription("Build rule for (generic) instance:" + doc.getDocName());
				ps.saveValueInfo(tempRule);
			}
			//CCEnd by liunan 2009-11-09

			EPMBuildHistoryInfo buildHistory = new EPMBuildHistoryInfo();
			buildHistory.setLeftBsoID(doc.getBsoID());
			buildHistory.setRightBsoID(part.getBsoID());
			ps.saveValueInfo(buildHistory);
		} catch (QMException ex) {
			ex.printStackTrace();
		}
	}

	public static void createPartDocLink(String partNum, String docNum) {
		try {
			StandardPartService ser = (StandardPartService) PublishHelper
					.getEJBService("StandardPartService");
			VersionControlService versiobSer = (VersionControlService) PublishHelper
					.getEJBService("VersionControlService");
			QMPartMasterIfc partMaster = PublishHelper
					.getPartMasterByNumber(partNum);
			if (partMaster == null) {
	    	//CCBegin by liunan 2008-09-03
	    	//为输出语句加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartDocLink, can't get part！");
				return;
			}
			QMPartInfo part = null;
			Collection coll = (Collection) versiobSer.allVersionsOf(partMaster);
			// 获得master下的所有小版本，按最新版序排列，
			// 则第一个元素即为最新版本版序的零部件对象
			Iterator iterator = coll.iterator();
			while (iterator.hasNext()) {
				part = (QMPartInfo) iterator.next();
				//CCBegin SS5
//				if (part.getViewName() != null
//						&& part.getViewName().trim().equals("工程视图")) {
				if (part.getViewName() != null
							&& part.getViewName().trim().equals("中心设计视图")) {
					//CCEnd SS5
					break;
				}
			}
			if (part == null) {
	    	//CCBegin by liunan 2008-09-03
	    	//为输出语句加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartDocLink, can't get part！");
				return;
			}
			DocInfo doc = PublishHelper.getDocInfoByNumber(docNum);
			if (doc == null) {
	    	//CCBegin by liunan 2008-09-03
	    	//为输出语句加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartDocLink, can't get doc！");
				return;
			}

			// 检查是否存在描述关系
			if (PublishHelper.hasPartDocDesc(part, doc)) {
	    	//CCBegin by liunan 2008-09-03
	    	//为输出语句加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartDocLink, relation exists！");
				return;
			}

			PartDescribeLinkInfo describeLink = new PartDescribeLinkInfo();
			describeLink.setRightBsoID(doc.getBsoID());
			describeLink.setLeftBsoID(part.getBsoID());
			PartDescribeLinkInfo link = (PartDescribeLinkInfo) ser
					.savePartDescribeLink(describeLink);
		} catch (QMException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 根据生命周期模板名，获取对应的生命周期模板
	 * 
	 * @param lifeName
	 *            String 生命周期模板名
	 * @throws QMException
	 * @return LifeCycleTemplateInfo 对应的生命周期模板
	 */
	public static LifeCycleTemplateInfo getLifeCycle(String lifeName)
			throws QMException {
		LifeCycleService ser = (LifeCycleService) EJBServiceHelper
				.getService("LifeCycleService");
		LifeCycleTemplateInfo lfInfo = (LifeCycleTemplateInfo) ser
				.getLifeCycleTemplate(lifeName);
		return lfInfo;
	}

	/**
	 * 根据生命周期模板名，获取对应的生命周期模板ID
	 * 
	 * @param lifeName
	 *            String 生命周期模板名
	 * @throws QMException
	 * @return String 对应的生命周期模板ID
	 */
	public static String getLifeCyID(String lifeName) throws QMException {
		LifeCycleTemplateInfo lfInfo = (LifeCycleTemplateInfo) getLifeCycle(lifeName);
		if (lfInfo != null) {
			return lfInfo.getBsoID();
		} else {
			return null;
		}
	}

	/**
	 * 分配资料夹
	 * 
	 * @param info
	 *            FolderEntryIfc 资料项对象
	 * @param location
	 *            String 资料路径
	 * @throws QMException
	 * @return FolderEntryIfc
	 */
	public static FolderEntryIfc assignFolder(FolderEntryIfc info,
			String location) throws QMException {
		FolderService ser = (FolderService) EJBServiceHelper
				.getService("FolderService");
		FolderEntryIfc view1 = (FolderEntryIfc) ser
				.assignFolder(info, location);
		return (FolderEntryIfc) view1;

	}

	/**
	 * 根据零部件对象的编号，获取对应零部件的ID信息
	 * 
	 * @param partNumber
	 *            String 零部件对象的编号
	 * @throws QMException
	 * @return String 对应零部件的ID信息
	 */
	public static String getPartBsoID(String partNumber) throws QMException {
		// String docBsoID = null;
		QMPartIfc part = getPartInfoByNumber(partNumber);
		if (part != null) {
			return part.getBsoID();
		} else {
			return null;
		}
	}

	/**
	 * 根据零部件对象的编号，获取对应零部件
	 * 
	 * @param number
	 *            String 零部件对象的编号
	 * @throws QMException
	 * @return QMPartInfo 对应零部件
	 */
	public static QMPartIfc getPartInfoByNumber(String number)
			throws QMException {
		QMPartIfc part = null;
		QMPartMasterIfc master = getPartMasterByNumber(number);
		if (master == null) {
			return null;
		}
		part = (QMPartIfc) getIteratedByMaster(master);
		return part;
	}

	/**
	 * 根据零部件对象的编号，获取对应零部件
	 * 
	 * @param number
	 *            String 零部件对象的编号
	 * @throws QMException
	 * @return QMPartInfo 对应零部件
	 */
	public static GenericPartInfo getGPartInfoByNumber(String number)
			throws QMException {
		GenericPartInfo gpart = null;
		GenericPartMasterInfo master = getGPartMasterByNumber(number);
		if (master == null) {
			return null;
		}

		gpart = (GenericPartInfo) getIteratedByMaster(master);
		return gpart;
	}

	/**
	 * 根据文档对象的编号，获取对应的文档对象ID信息
	 * 
	 * @param docNumber
	 *            String 文档对象的编号
	 * @throws QMException
	 * @return String 对应的文档对象ID信息
	 */
	public static String getDocIDByNumber(String docNumber) throws QMException {
		// String docBsoID = null;
		DocInfo docInfo = getDocInfoByNumber(docNumber);
		if (docInfo != null) {
			return docInfo.getBsoID();
		} else {
			return null;
		}
	}

	/**
	 * 根据文档对象的编号，获取对应的文档对象
	 * 
	 * @param docNumber
	 *            String 文档对象的编号
	 * @throws QMException
	 * @return DocInfo 对应的文档对象
	 */
	public static DocInfo getDocInfoByNumber(String docNumber)
			throws QMException {
		DocInfo doc = null;
		DocMasterInfo master = getDocMasterByNumber(docNumber);
		if (master == null) {
			return null;
		}
		doc = (DocInfo) getIteratedByMaster(master);
		return doc;
	}

	/**
	 * 给定Master对象，获取这个对象的最新版本对象
	 * 
	 * @param master
	 *            MasteredIfc
	 * @throws QMException
	 * @return IteratedIfc
	 */
	public static IteratedIfc getIteratedByMaster(MasteredIfc master)
			throws QMException {
		VersionControlService ser = (VersionControlService) EJBServiceHelper
				.getService("VersionControlService");
		Collection coll = (Collection) ser.allVersionsOf(master);
		IteratedIfc iteratedIfc = null;
		// 获得master下的所有小版本，按最新版序排列，
		// 则第一个元素即为最新版本版序的零部件对象
		Iterator iterator = coll.iterator();
		if (iterator.hasNext()) {
			iteratedIfc = (IteratedIfc) iterator.next();
		}
		return iteratedIfc;

	}

	/**
	 * 给定Master，得到这个对象的所有的小版本（包括不同分枝），也就是master对应的所有小版本对象 <BR>
	 * 按版本由新到旧排好序的结果 如c.2,c.1,B.3,B,2,B.1,A.9,A.8,A.7...A,1
	 * 
	 * @param master
	 *            MasteredIfc
	 * @throws QMException
	 * @return Collection
	 */
	public static Collection getAllIterationsOf(MasteredIfc master)
			throws QMException {
		VersionControlService ser = (VersionControlService) EJBServiceHelper
				.getService("VersionControlService");
		return ser.allIterationsOf(master);
	}

	/**
	 * 根据零部件的编号，获取对应的零部件主对象
	 * 
	 * @param partNumber
	 *            String 零部件的编号
	 * @throws QMException
	 * @return QMPartMasterInfo 对应的零部件主对象
	 */
	public static QMPartMasterIfc getPartMasterByNumber(String partNumber)
			throws QMException {
		QMQuery query = new QMQuery("QMPartMaster");
		QueryCondition condition = new QueryCondition("partNumber", "=",
				partNumber);
		query.addCondition(condition);
		// query.setChildQuery(true);
		QMPartMasterIfc master = null;
		PersistService ser = (PersistService) EJBServiceHelper
				.getService("PersistService");
		Collection coll = (Collection) ser.findValueInfo(query, false);
		//CCBegin by liunan 2010-05-04 如果存在2个同编号的主信息，说明这个编号的part和gpart都有。
		//此时只取gpart，目前数据发布认为有相同编号的part和gpart都存在时，再发布的一定是gpart。
		//源代码
		/*Iterator iterator = coll.iterator();
		if (iterator.hasNext()) {
			master = ((QMPartMasterIfc) iterator.next());
		}*/
		
		//CCBegin SS1
		//if(coll.size()==2)
		if(coll.size()>=2)
		{
			//System.out.println("in getPartMasterByNumber 存在相同编号的part和gpart！！！");
			//System.out.println("in getPartMasterByNumber 存在相同编号的part、gpart 或 变速箱part！！！");
			QMPartMasterIfc mastertemp = null;
			QMPartMasterIfc qmparttemp = null;
			//CCEnd SS1
			Iterator iterator = coll.iterator();
			while (iterator.hasNext())
			{
				QMPartMasterIfc temp = (QMPartMasterInfo) iterator.next();
				//System.out.println("temp==="+temp);
				if(temp.getBsoID().startsWith("GenericPart"))
				{
					//CCBegin SS1
					//master = temp;
					mastertemp = temp;
				}
				else if(temp.getBsoID().endsWith("(BSXUP)"))
				{
					continue;
				}
				else
				{
					qmparttemp = temp;
				}
				//CCEnd SS1
			}
			//CCBegin SS1
			if(mastertemp!=null)
			{
				master = mastertemp;
			}
			else
			{
				master = qmparttemp;
			}
			//System.out.println("此时得到的零部件为： "+master);
			//CCEnd SS1
			//CCBegin by liunan 2010-05-20 解放系统中存在相同编号的多个partmaster情况
			//所以master为null时表示是这种情况，此时按以前方法取第一个，如果能从系统中保证只有一个
			//partmaster，则此条件不会进入。
			if(master==null)
			{
				master = (QMPartMasterInfo) coll.iterator().next();
			}
			//CCEnd by liunan 2010-05-20
		}
		else
		{
			Iterator iterator = coll.iterator();
			if (iterator.hasNext())
			{
				master = ((QMPartMasterIfc) iterator.next());
			}
		}
		//CCEnd by liunan 2010-05-04

		return master;
	}

	/**
	 * 根据零部件的编号，获取对应的零部件主对象
	 * 
	 * @param partNumber
	 *            String 零部件的编号
	 * @throws QMException
	 * @return QMPartMasterInfo 对应的零部件主对象
	 */
	public static GenericPartMasterInfo getGPartMasterByNumber(String partNumber)
			throws QMException {
		QMQuery query = new QMQuery("GenericPartMaster");
		QueryCondition condition = new QueryCondition("partNumber", "=",
				partNumber);
		query.addCondition(condition);
		// query.setChildQuery(true);
		GenericPartMasterInfo master = null;
		PersistService ser = (PersistService) EJBServiceHelper
				.getService("PersistService");
		Collection coll = (Collection) ser.findValueInfo(query, false);
		Iterator iterator = coll.iterator();
		if (iterator.hasNext()) {
			master = ((GenericPartMasterInfo) iterator.next());
		}
		return master;
	}

	/**
	 * 根据文档编号，获取对应文档对象的主对象
	 * 
	 * @param docNumber
	 *            String 文档编号
	 * @throws QMException
	 * @return DocMasterInfo 对应文档对象的主对象
	 */
	public static DocMasterInfo getDocMasterByNumber(String docNumber)
			throws QMException {
		DocMasterInfo master = null;
		QMQuery query = new QMQuery("DocMaster");
		QueryCondition condition = new QueryCondition("docNum", "=", docNumber);
		query.addCondition(condition);

		PersistService ser = (PersistService) EJBServiceHelper
				.getService("PersistService");
		Collection coll = (Collection) ser.findValueInfo(query, false);

		Iterator iterator = coll.iterator();
		if (iterator.hasNext()) {
			master = ((DocMasterInfo) iterator.next());

		}
		return master;
	}

	/**
	 * 根据文档分类名，获取对应的文档分类ID
	 * 
	 * @param docCfName
	 *            String 文档分类名
	 * @throws QMException
	 * @return String 对应的文档分类ID
	 */
	public static String getDocCf(String docCfName) throws QMException {
		String docCfBsoID = (String) docCfMap.get(docCfName);
		if (docCfBsoID == null) {

			// 分解父分类的路径信息
			String olddocCfName = docCfName;
			DocCfComponents docCfCom = new DocCfComponents(docCfName);
			int pathLen = docCfName.length();
			int lastIndex = docCfName.lastIndexOf("\\");
			if (lastIndex == pathLen - 1) {
				docCfName = docCfName.substring(0, pathLen - 1);
			}
			String docCfNameTail = docCfCom.getTail();
			if (docCfNameTail == null || docCfNameTail.trim().length() == 0) {
				String log2 = " Doc:" + olddocCfName + "  文档分类 \"" + docCfName
						+ "\" 信息不正确。";

				throw new QMException("Error 文档分类:" + olddocCfName);
			}

			// 根据分类名查找数据(可能多个值)
			QMQuery query = new QMQuery("DocClassification");
			QueryCondition qc = new QueryCondition("docCfName", "=",
					docCfNameTail);
			query.addCondition(qc);
			PersistService ser = (PersistService) EJBServiceHelper
					.getService("PersistService");
			Collection coll = (Collection) ser.findValueInfo(query, false);

			// 如果没有找到匹配记录，返回false
			if (coll == null || coll.size() == 0) {
				String log2 = " Doc:" + olddocCfName + " Be:文档分类 \""
						+ docCfName + "\" 不存在。";

				throw new QMException("Error 文档分类:" + olddocCfName + " 不存在");
			}

			boolean findFlag = false;
			DocClassificationInfo tempDocCf = null;
			// 如果只有一条记录则返回true,并绶存父分类的值对象
			if (coll.size() == 1) {
				Iterator iter = coll.iterator();
				tempDocCf = (DocClassificationInfo) iter.next();
				docCfBsoID = tempDocCf.getBsoID();
				findFlag = true;
			} else {
				// 遍历所有同名分类，找出路径完全匹配的分类,返回true,并绶存分类的值对象
				for (Iterator iter = coll.iterator(); iter.hasNext();) {
					tempDocCf = (DocClassificationInfo) iter.next();
					String path = DocServiceHelper
							.findDocCfLeafNodePathByID(tempDocCf.getBsoID());
					if (olddocCfName.equals(path.substring(1))) {
						docCfBsoID = tempDocCf.getBsoID();
						findFlag = true;
						break;
					}
				}
			}
			if (!findFlag) {
				String log2 = " Doc:" + olddocCfName + " Be:文档分类 \""
						+ docCfName + "\" 不存在。";

				throw new QMException("Error 文档分类:" + olddocCfName + " 不存在");
			}
			docCfMap.put(olddocCfName, docCfBsoID);
			return docCfBsoID;
		} else {
			return docCfBsoID;
		}
	}

	/**
	 * 根据视图名，获取对应的视图对象
	 * 
	 * @param viewName
	 *            String 视图名
	 * @throws QMException
	 * @return ViewObjectInfo 对应的视图对象
	 */
	public static ViewObjectInfo getView(String viewName) throws QMException {
		ViewService vs = (ViewService) EJBServiceHelper.getService(viewName);
		ViewObjectInfo v = vs.getView(viewName);
		return v;
	}

	/**
	 * 判断是否存在指定的视图对象
	 * 
	 * @param viewName
	 *            String 视图名
	 * @throws QMException
	 * @return boolean 是否存在指定的视图对象
	 */
	public static boolean isHasView(String viewName) throws QMException {
		ViewObjectInfo v = getView("ViewService");
		return v != null;
	}

	/**
	 * 判断是否存在指定的零部件编号
	 * 
	 * @param partNum
	 *            String 零部件编号
	 * @return boolean
	 */
	public static boolean hasPartMaster(String partNum) {
		try {
			QMPartMasterIfc part = getPartMasterByNumber(partNum);
			return part != null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 判断是否存在指定的文档编号
	 * 
	 * @param docNum
	 *            String
	 * @return boolean
	 */
	public static boolean hasDocMaster(String docNum) {
		try {
			DocMasterInfo doc = getDocMasterByNumber(docNum);
			return doc != null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 判断是否存在指定的零部件结构关系
	 * 
	 * @param parent
	 *            QMPartInfo
	 * @param child
	 *            QMPartMasterInfo
	 * @param quantity
	 *            float
	 * @param unit
	 *            String
	 * @return boolean
	 */
	public static boolean hasStruct(QMPartInfo parent, QMPartMasterInfo child,
			float quantity, String unit) {
		if (parent == null || child == null) {
			return false;
		}
		return hasStruct(parent.getBsoID(), child.getBsoID(), quantity, unit);
	}

	/**
	 * 判断是否存在指定的零部件结构关系
	 * 
	 * @param parentId
	 *            String 父ID
	 * @param childId
	 *            String 子件ID
	 * @param quantity
	 *            float 使用数量
	 * @param unit
	 *            String 使用单位
	 * @return boolean
	 */
	public static boolean hasStruct(String parentId, String childId,
			float quantity, String unit) {
		try {
			QMQuery query = new QMQuery("PartUsageLink");
			QueryCondition qc = new QueryCondition("rightBsoID", "=", parentId);
			query.addCondition(qc);
			query.addAND();
			qc = new QueryCondition("leftBsoID", "=", childId);
			query.addCondition(qc);
			query.addAND();
			qc = new QueryCondition("quantity", "=", (float) quantity);
			query.addCondition(qc);
			query.addAND();
			qc = new QueryCondition("unit", "=", unit);
			query.addCondition(qc);
			PersistService ser = (PersistService) EJBServiceHelper
					.getService("PersistService");
			Collection coll = (Collection) ser.findValueInfo(query, false);
			if (coll.size() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取指定零部件的结构关联类
	 * 
	 * @param parent
	 *            QMPartInfo
	 * @param child
	 *            QMPartMasterInfo
	 * @throws QMException
	 * @return PartUsageLinkInfo
	 */
	public static Collection getPartUsageLink(QMPartInfo parent,
			QMPartMasterInfo child) throws QMException {
		if (parent == null || child == null) {
			return null;
		}
		QMQuery query = new QMQuery("PartUsageLink");
		QueryCondition qc = new QueryCondition("rightBsoID", "=", parent
				.getBsoID());
		query.addCondition(qc);
		query.addAND();
		query.setChildQuery(true);
		qc = new QueryCondition("leftBsoID", "=", child.getBsoID());
		query.addCondition(qc);
		PersistService ser = (PersistService) EJBServiceHelper
				.getService("PersistService");
		Collection coll = (Collection) ser.findValueInfo(query, false);
		return coll;
		/*
		 * Iterator iter = coll.iterator(); if (iter.hasNext()) { return
		 * (PartUsageLinkInfo) iter.next(); } else { return null; }
		 */
	}

	/**
	 * 获取一个零部件的父件集合(QMPartInfo)
	 * 
	 * @param master
	 *            QMPartMasterInfo
	 * @throws Exception
	 * @return Collection
	 */
	public static Collection getParentPart(QMPartMasterIfc child)
			throws QMException {
		QMQuery query = new QMQuery("QMPart");
		query.appendBso("PartUsageLink", false);
		QueryCondition con = new QueryCondition("bsoID", "rightBsoID");
		query.addCondition(0, 1, con);
		query.addAND();
		QueryCondition con1 = new QueryCondition("leftBsoID",
				QueryCondition.EQUAL, child.getBsoID());
		query.addCondition(1, con1);
		// query.addAND();
		// QueryCondition con2 = new QueryCondition("iterationIfLatest", true);
		// query.addCondition(0, con2);
		PersistService ps = (PersistService) getEJBService("PersistService");
		return ps.findValueInfo(query);
	}

	/**
	 * 获取指定零部件的子件结构关联类集合（PartUsageLinkInfo）
	 * 
	 * @param parent
	 *            QMPartInfo
	 * @throws QMException
	 * @return Collection
	 */
	public static Collection getPartUsageLinks(QMPartIfc parent)
			throws QMException {
		if (parent == null) {
			return new Vector(0);
		}
		QMQuery query = new QMQuery("PartUsageLink");
		QueryCondition qc = new QueryCondition("rightBsoID", "=", parent
				.getBsoID());
		query.addCondition(qc);
		PersistService ser = (PersistService) getEJBService("PersistService");
		return ser.findValueInfo(query, false);
	}

	/**
	 * 获取一个业务对象的对象显示标识
	 * 
	 * @param qmObj
	 *            BaseValueInfo
	 * @return String
	 */
	private static String getIdentity(BaseValueIfc qmObj) {
		if (qmObj == null) {
			return "";
		}
		DisplayIdentity displayidentity = IdentityFactory
				.getDisplayIdentity(qmObj);
		Locale locale = RemoteProperty.getVersionLocale();
		return displayidentity.getDisplayIdentifier().getLocalizedMessage(
				locale);

	}

	/**
	 * 删除指定的零部件,如果指定bom，则按bom结构删除零部件
	 * 
	 * @param parentPart
	 *            QMPartInfo
	 * @param bom
	 *            boolean
	 * @throws QMException
	 */
	public static void deletePublishDataByRootPartNumber(String rootPartNumber)
			throws QMException {
		QMPartIfc parentPart = getPartInfoByNumber(rootPartNumber);
		deletePublishDataByRootPart(parentPart, new ArrayList());
	}

	/**
	 * 删除指定的零部件,如果指定bom，则按bom结构删除零部件
	 * 
	 * @param parentPart
	 *            QMPartInfo
	 * @param bom
	 *            boolean
	 * @throws QMException
	 */
	public static void deletePublishDataByRootPart(QMPartIfc parentPart,
			Collection partsAlreadyDelete) {
		try {
			if (PublishHelper.VERBOSE)
				System.out
						.println("enter PublishHelper.deletePublishDataByRootPart,part number: "
								+ parentPart == null ? null : parentPart
								.getPartNumber());
			if (parentPart == null) {
				if (PublishHelper.VERBOSE)
					System.out
							.println("leave PublishHelper.deletePublishDataByRootPart with error,parentPart is null!");
				return;
			}
			if (partsAlreadyDelete == null) {
				partsAlreadyDelete = new ArrayList();
			}
			QMPartMasterIfc partMaster = (QMPartMasterInfo) parentPart
					.getMaster();
			Collection parents = getParentPart(partMaster);
			if (parents.size() > 0) {
				if (PublishHelper.VERBOSE)
					System.out.println("Don't delete part["
							+ getIdentity(parentPart)
							+ "] it is usaged by other part......");
				QMPartIfc temp;
				for (Iterator iter = parents.iterator(); iter.hasNext();) {
					if (PublishHelper.VERBOSE)
						System.out.println("parent...."
								+ getIdentity((QMPartIfc) iter.next()));
				} // end for
				return;
			}
			// 查找子件
			Vector childrenColl = new Vector();
			Collection children = getPartUsageLinks(parentPart);
			QMPartMasterIfc master = null;
			QMPartIfc child = null;
			PartUsageLinkIfc link;
			Iterator iter = children.iterator();
			while (iter.hasNext()) {
				link = (PartUsageLinkIfc) iter.next();
				master = (QMPartMasterIfc) link.getUses();
				child = (QMPartIfc) getIteratedByMaster(master);
				if (child != null) {
					if (PublishHelper.VERBOSE)
						System.out.println("child:         "
								+ child.getPartNumber());
					childrenColl.add(child);
				} else {
					if (PublishHelper.VERBOSE)
						System.out.println("child " + master.getPartNumber()
								+ ", can't get iteration!");
				}
			}
			if (PublishHelper.VERBOSE)
				System.out.println("===========>" + childrenColl.size());
			// 删除父件
			PersistService pservice = (PersistService) getEJBService("PersistService");
			StandardPartService partService = (StandardPartService) EJBServiceHelper
					.getService("StandardPartService");
			StandardDocService docService = (StandardDocService) EJBServiceHelper
					.getService("StandardDocService");
			Collection describeDocs = getDescribeDocs(parentPart);
			Collection epmDocs = getRelatedEpmDocs(parentPart);
			if (!partsAlreadyDelete.contains(parentPart.getPartNumber())) {
				removeFromBaselines(parentPart);
				partService.deletePart(parentPart);
				partsAlreadyDelete.add(parentPart.getPartNumber());
			}
			// pservice.removeValueInfo(parentPart);
			if (PublishHelper.VERBOSE)
				System.out.println("已删除零部件:" + parentPart.getPartNumber()
						+ "doc amount: " + describeDocs.size()
						+ ",epm amount: " + epmDocs.size());
			if (describeDocs != null) {
				Iterator iterator = describeDocs.iterator();
				while (iterator.hasNext()) {
					DocIfc doc = (DocIfc) iterator.next();
					DocMasterIfc docMaster = (DocMasterIfc) doc.getMaster();
					pservice.removeValueInfo(doc);
					if (getIteratedByMaster(docMaster) == null) {
						pservice.removeValueInfo(docMaster);
					}
					// docService.deleteDoc(doc.getBsoID());
					if (PublishHelper.VERBOSE)
						System.out.println("已删除描述文档:" + doc.getDocNum());
				}
			}
			if (epmDocs != null) {
				Iterator iterator = epmDocs.iterator();
				while (iterator.hasNext()) {
					EPMDocumentInfo epmDoc = (EPMDocumentInfo) iterator.next();
					EPMDocumentMasterIfc epmMaster = (EPMDocumentMasterIfc) epmDoc
							.getMaster();
					pservice.removeValueInfo(epmDoc);
					if (getIteratedByMaster(epmMaster) == null) {
						pservice.removeValueInfo(epmMaster);
					}
					if (PublishHelper.VERBOSE)
						System.out.println("已删除EPM文档:" + epmDoc.getDocNumber());
				}
			}
			if (PublishHelper.VERBOSE)
				System.out.println("===========>" + childrenColl.size());
			// 删除子件
			for (Enumeration enumer = childrenColl.elements(); enumer
					.hasMoreElements();) {
				QMPartIfc childPart = (QMPartIfc) enumer.nextElement();
				if (!partsAlreadyDelete.contains(childPart.getPartNumber())) {
					deletePublishDataByRootPart(childPart, partsAlreadyDelete);
				} else {
					if (PublishHelper.VERBOSE)
						System.out.println(childPart.getPartNumber()
								+ " has already been deleted!");
				}
			}
			if (PublishHelper.VERBOSE)
				System.out
						.println("leave PublishHelper.deletePublishDataByRootPart!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除指定的零部件,如果指定bom，则按bom结构删除零部件
	 * 
	 * @param parentPart
	 *            QMPartInfo
	 * @param bom
	 *            boolean
	 * @throws QMException
	 */
	public static void deleteParts(QMPartIfc parentPart, boolean bom)
			throws QMException {
		QMPartMasterInfo partMaster = (QMPartMasterInfo) parentPart.getMaster();
		Collection parents = getParentPart(partMaster);
		if (parents.size() > 0) {
			if (PublishHelper.VERBOSE)
				System.out.println("Don't delete part["
						+ getIdentity(parentPart)
						+ "] it is usaged by other part......");
			QMPartInfo temp;
			for (Iterator iter = parents.iterator(); iter.hasNext();) {
				if (PublishHelper.VERBOSE)
					System.out.println("parent...."
							+ getIdentity((QMPartInfo) iter.next()));
			} // end for
			return;
		}
		// 查找子件
		Vector childrenColl = new Vector();
		if (bom) {
			Collection children = getPartUsageLinks(parentPart);
			QMPartMasterInfo master = null;
			QMPartInfo child = null;
			PartUsageLinkInfo link;
			Iterator iter = children.iterator();
			while (iter.hasNext()) {
				link = (PartUsageLinkInfo) iter.next();
				master = (QMPartMasterInfo) link.getUses();
				child = (QMPartInfo) partMap.get(master.getPartNumber());
				if (child == null) {
					child = (QMPartInfo) getIteratedByMaster(master);
					if (child != null) {
						partMap.put(master.getPartNumber(), child);
					}
				}
				childrenColl.add(child);
			}
		}
		// 删除父件
		PersistService ser = (PersistService) getEJBService("PersistService");
		ser.deleteValueInfo(parentPart);
		if (PublishHelper.VERBOSE)
			System.out.println("Delete Part [" + getIdentity(parentPart)
					+ "]  OK!");
		// 删除子件
		if (bom) {
			for (Enumeration enumer = childrenColl.elements(); enumer
					.hasMoreElements();) {
				deleteParts((QMPartInfo) enumer.nextElement(), bom);
			}
		}
	}

	/**
	 * 根据参数信息删除指定的零部件，如查bom参数为true，则表按bom结构删除零部件
	 * 
	 * @param num
	 *            String
	 * @param version
	 *            String
	 * @param iteration
	 *            String
	 * @param bom
	 *            boolean
	 * @throws Exception
	 */
	public static void deleteParts(String num, String version, boolean bom)
			throws QMException {
		if (num == null) {
			return;
		}
		QMPartIfc part = findPartUseCache(num, version);
		if (part == null) {
	    //CCBegin by liunan 2008-09-03
	    //为输出语句加开关。
	    if (PublishHelper.VERBOSE)
	    //CCEnd by liunan 2008-09-03
			System.out.println("Not find part(num=" + num + " version="
					+ version);
			return;
		}
		try {
			deleteParts(part, bom);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// 清空,也防止该cache被乱用，造成数据不准确(这个cache仅用于清除产品树方便)
			partMap.clear();
		}
	}

	/**
	 * 根据零部件的编号、版本号查找对应的零部件
	 * 
	 * @param num
	 *            String
	 * @param version
	 *            String
	 * @param iteration
	 *            String
	 * @throws QMException
	 * @return QMPartInfo
	 */
	private static QMPartIfc findPartUseCache(String num, String version)
			throws QMException {
		if (num == null) {
			return null;
		}
		QMPartIfc part = null;
		try {
			// 使用cache
			part = getCachedPart(num);
			if (part == null) {
				// 如果只有编号
				if (version == null) {
					part = getPartInfoByNumber(num);
					if (part != null) {
						partMap.put(part.getPartNumber().toUpperCase(), part);
					}
					return part;
				}

				// 构造两表联合查询条件
				QMQuery qs = new QMQuery("QMPart");
				qs.appendBso("QMPartMaster", false);
				QueryCondition con = new QueryCondition("masterBsoID", "bsoID");
				qs.addCondition(0, 1, con);
				qs.addAND();
				qs.setChildQuery(true);
				con = new QueryCondition("partNumber", "=", num);
				qs.addCondition(1, con);

				if (version != null) {
					qs.addAND();
					QueryCondition con1 = new QueryCondition("versionValue",
							"=", version);
					qs.addCondition(0, con1);
					/*
					 * //如果只有大版本号,则查找大版本号对应的最新版本 if(iteration == null) {
					 * qs.addAND(); QueryCondition con2 = new
					 * QueryCondition("iterationIfLatest", true);
					 * qs.addCondition(0, con2); }
					 */
				}
				/*
				 * if (iteration != null) { qs.addAND(); QueryCondition con3 =
				 * new QueryCondition("iterationID", "=", iteration);
				 * qs.addCondition(0, con3); }
				 */
				PersistService ser = (PersistService) getEJBService("PersistService");
				Collection coll = ser.findValueInfo(qs);
				Iterator iter = coll.iterator();
				if (iter.hasNext()) { // if already exist
					part = (QMPartInfo) iter.next();
				}
				if (part != null) {
					partMap.put(part.getPartNumber().toUpperCase(), part);
				}
			} // cache中没有
		} catch (QMException wte) {
			wte.printStackTrace();
		} catch (Exception wte) {
			wte.printStackTrace();
		}
		return part;

	}

	/**
	 * 在cache中查找零部件
	 * 
	 * @param number
	 *            String
	 * @return QMPartInfo
	 */
	private static QMPartInfo getCachedPart(String number) {
		number = number.toUpperCase();
		QMPartInfo part = (QMPartInfo) partMap.get(number);
		return part;
	}

	/**
	 * 根据零部件的编号、版本号查找对应的零部件
	 * 
	 * @param num
	 *            String
	 * @param version
	 *            String
	 * @param iteration
	 *            String
	 * @throws QMException
	 * @return QMPartInfo
	 */
	public static QMPartIfc findPartNoCache(String num, String version)
			throws QMException {
		if (num == null) {
			return null;
		}
		QMPartIfc part = null;
		try {
			// 如果只有编号
			if (version == null) {
				part = getPartInfoByNumber(num);
				if (part != null) {
					partMap.put(part.getPartNumber().toUpperCase(), part);
				}
				return part;
			}

			// 构造两表联合查询条件
			QMQuery qs = new QMQuery("QMPart");
			qs.setChildQuery(true);
			qs.appendBso("QMPartMaster", false);
			QueryCondition con = new QueryCondition("masterBsoID", "bsoID");
			qs.addCondition(0, 1, con);
			qs.addAND();
			con = new QueryCondition("partNumber", "=", num);
			qs.addCondition(1, con);

			if (version != null) {
				qs.addAND();
				QueryCondition con1 = new QueryCondition("versionValue", "=",
						version);
				qs.addCondition(0, con1);
				/*
				 * //如果只有大版本号,则查找大版本号对应的最新版本 if(iteration == null) {
				 * qs.addAND(); QueryCondition con2 = new
				 * QueryCondition("iterationIfLatest", true); qs.addCondition(0,
				 * con2); }
				 */
			}
			/*
			 * if (iteration != null) { qs.addAND(); QueryCondition con3 = new
			 * QueryCondition("iterationID", "=", iteration); qs.addCondition(0,
			 * con3); }
			 */
			PersistService ser = (PersistService) getEJBService("PersistService");
			Collection coll = ser.findValueInfo(qs);
			Iterator iter = coll.iterator();
			if (iter.hasNext()) { // if already exist
				part = (QMPartInfo) iter.next();
			}
		} catch (QMException wte) {
			wte.printStackTrace();
		} catch (Exception wte) {
			wte.printStackTrace();
		}
		return part;

	}

	/**
	 * 判断是否存在指定的零部件与文档的参考关系
	 * 
	 * @param part
	 *            QMPartInfo
	 * @param docMaster
	 *            DocMasterInfo
	 * @return boolean
	 * 
	 * public boolean hasPartDocRef(QMPartInfo part, DocMasterInfo docMaster) {
	 * if(part == null || docMaster == null) return false; return
	 * hasPartDocRef(part.getBsoID(), docMaster.getBsoID()); }
	 */
	/**
	 * 判断是否存在指定的零部件与文档的参考关系
	 * 
	 * @param partID
	 *            String
	 * @param docMasterID
	 *            String
	 * @return boolean
	 * 
	 * public boolean hasPartDocRef(String partID, String docMasterID) { try {
	 * QMQuery query = new QMQuery("PartReferenceLink"); QueryCondition qc = new
	 * QueryCondition("rightBsoID", "=", partID); query.addCondition(qc);
	 * query.addAND(); qc = new QueryCondition("leftBsoID", "=", docMasterID);
	 * query.addCondition(qc);
	 * 
	 * PersistService ser = (PersistService) getEJBService("PersistService");
	 * Collection coll = (Collection) ser.findValueInfo(query, false); if
	 * (coll.size() > 0) return true; else return false; }catch(Exception ex) {
	 * ex.printStackTrace(); return false; } }
	 */
	/**
	 * 判断指定的零部件、文档是否存在描述关联关系
	 * 
	 * @param part
	 *            QMPartInfo
	 * @param doc
	 *            DocInfo
	 * @return boolean
	 */
	public static boolean hasPartDocDesc(QMPartInfo part, DocInfo doc) {
		if (part == null || doc == null) {
			return false;
		}
		return hasPartDocDesc(part.getBsoID(), doc.getBsoID());
	}

	/**
	 * 判断指定的零部件、文档是否存在描述关联关系
	 * 
	 * @param partID
	 *            String 零部件ID
	 * @param docID
	 *            String 文档ID
	 * @return boolean 是否存在描述关联关系
	 */
	public static boolean hasPartDocDesc(String partID, String docID) {
		try {
			QMQuery query = new QMQuery("PartDescribeLink");
			QueryCondition qc = new QueryCondition("rightBsoID", "=", docID);
			query.addCondition(qc);
			query.addAND();

			qc = new QueryCondition("leftBsoID", "=", partID);
			query.addCondition(qc);
			PersistService ser = (PersistService) getEJBService("PersistService");
			Collection coll = (Collection) ser.findValueInfo(query, false);
			if (coll.size() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	/**
	 * 判断是否存在指定的EPM文档编号
	 * 
	 * @param docNum
	 *            String
	 * @return boolean
	 */
	public static boolean hasEPMDocMaster(String docNum) {
		try {
			EPMDocumentMasterInfo doc = getEPMDocMasterByNumber(docNum);
			return doc != null;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 判断指定的零部件及EPM文档是否存在关联关系
	 * 
	 * @param part
	 *            QMPartInfo
	 * @param doc
	 *            EPMDocumentInfo
	 * @return boolean
	 */
	public static boolean hasBuildLinkRule(QMPartInfo part, EPMDocumentInfo doc) {
		if (part == null || doc == null) {
			return false;
		}
		try {
			QMQuery query = new QMQuery("EPMBuildLinksRule");
			QueryCondition con = new QueryCondition("leftBsoID",
					QueryCondition.EQUAL, doc.getBranchID());

			query.addCondition(con);
			query.addAND();
			QueryCondition con1 = new QueryCondition("rightBsoID",
					QueryCondition.EQUAL, part.getBranchID());
			query.addCondition(con1);
			PersistService ps = (PersistService) getEJBService("PersistService");
			Collection links = ps.findValueInfo(query, false);
			if (links == null || links.isEmpty()) {
				return false;
			} else {
				return true;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 判断指定的零部件及EPM文档是否存在关联关系。
	 * 因为不再是生准才发布，而且存在生准的零部件进行小版本升级，
	 * 和已存在系统且有EPM文档又不是生准的零部件。
	 * 所以需要判断小版本关联，来决定是否建立零部件和EPM关联关系。
	 * @param part
	 *            QMPartInfo
	 * @param doc
	 *            EPMDocumentInfo
	 * @return boolean
	 * @author liunan 2009-11-09
	 */
	public static boolean hasBuildHistory(QMPartInfo part, EPMDocumentInfo doc) {
		if (part == null || doc == null) {
			return false;
		}
		try {
			QMQuery query = new QMQuery("EPMBuildHistory");
			QueryCondition con = new QueryCondition("leftBsoID",
					QueryCondition.EQUAL, doc.getBsoID());

			query.addCondition(con);
			query.addAND();
			QueryCondition con1 = new QueryCondition("rightBsoID",
					QueryCondition.EQUAL, part.getBsoID());
			query.addCondition(con1);
			PersistService ps = (PersistService) getEJBService("PersistService");
			Collection links = ps.findValueInfo(query, false);
			if (links == null || links.isEmpty()) {
				return false;
			} else {
				return true;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 判断指定的EPM文档是否存在结构关联关系
	 * 
	 * @param parent
	 *            EPMDocumentInfo
	 * @param child
	 *            EPMDocumentMasterInfo
	 * @return boolean
	 * 
	 * public boolean hasEPMMemberLink(EPMDocumentInfo parent,
	 * EPMDocumentMasterInfo child) { if(parent == null || child == null) return
	 * false; try { EPMMemberLinkInfo link = getEPMMemberLink(parent, child);
	 * if(link == null) return false; else return true; }catch(Exception ex) {
	 * ex.printStackTrace(); return false; } }
	 */
	/**
	 * 判断指定的EPM文档是否存在参考关联关系
	 * 
	 * @param parent
	 *            EPMDocumentInfo
	 * @param child
	 *            EPMDocumentMasterInfo
	 * @return boolean
	 * 
	 * public boolean hasEPMReferenceLink(EPMDocumentInfo parent,
	 * EPMDocumentMasterInfo child) { if(parent == null || child == null) return
	 * false; try { EPMReferenceLinkInfo link = getEPMReferenceLink(parent,
	 * child); if(link == null) return false; else return true; }catch(Exception
	 * ex) { ex.printStackTrace(); return false; } }
	 */

	/**
	 * 获取指定的EPM文档的结构关联类对象，如果不存在关联关系，则返回null
	 * 
	 * @param parent
	 *            EPMDocumentInfo
	 * @param child
	 *            EPMDocumentMasterInfo
	 * @throws QMException
	 * @return EPMMemberLinkInfo
	 * 
	 * public EPMMemberLinkInfo getEPMMemberLink(EPMDocumentInfo parent,
	 * EPMDocumentMasterInfo child) throws QMException { if(parent == null ||
	 * child == null) return null; QMQuery query = new QMQuery("EPMMemberLink");
	 * QueryCondition con = new QueryCondition("leftBsoID",
	 * QueryCondition.EQUAL, parent.getBsoID());
	 * 
	 * query.addCondition(con); query.addAND(); QueryCondition con1 = new
	 * QueryCondition("rightBsoID", QueryCondition.EQUAL, child.getBsoID());
	 * query.addCondition(con1); PersistService ps = (PersistService)
	 * getEJBService("PersistService"); Collection links =
	 * ps.findValueInfo(query, false); Iterator iter = links.iterator();
	 * if(iter.hasNext()) return (EPMMemberLinkInfo)iter.next(); else return
	 * null; }
	 */
	/**
	 * 获取指定的EPM文档的参考关联类对象，如果不存在关联关系，则返回null
	 * 
	 * @param parent
	 *            EPMDocumentInfo
	 * @param child
	 *            EPMDocumentMasterInfo
	 * @throws QMException
	 * @return EPMReferenceLinkInfo
	 * 
	 * public EPMReferenceLinkInfo getEPMReferenceLink(EPMDocumentInfo parent,
	 * EPMDocumentMasterInfo child) throws QMException { if(parent == null ||
	 * child == null) return null; QMQuery query = new
	 * QMQuery("EPMReferenceLink"); QueryCondition con = new
	 * QueryCondition("leftBsoID", QueryCondition.EQUAL, parent.getBsoID());
	 * 
	 * query.addCondition(con); query.addAND(); QueryCondition con1 = new
	 * QueryCondition("rightBsoID", QueryCondition.EQUAL, child.getBsoID());
	 * query.addCondition(con1); PersistService ps = (PersistService)
	 * getEJBService("PersistService"); Collection links =
	 * ps.findValueInfo(query, false); Iterator iter = links.iterator();
	 * if(iter.hasNext()) return (EPMReferenceLinkInfo)iter.next(); else return
	 * null; }
	 */

	/**
	 * 获得与零部件关联的epm文档
	 * 
	 * @param a_part
	 *            QMPartIfc
	 * @return EPMDocumentIfc
	 * @throws QMException
	 */
	public static EPMDocumentIfc getLinkedEPMDocument(QMPartIfc a_part)
			throws QMException {

		EPMDocumentIfc doc = null;
		String partbranchID = a_part.getBranchID();
		QMQuery query = new QMQuery("EPMDocument");
		query.appendBso("EPMBuildLinksRule", false);
		QueryCondition con = new QueryCondition("branchID", "leftBsoID");
		query.addCondition(0, 1, con);
		query.addAND();
		QueryCondition con1 = new QueryCondition("rightBsoID",
				QueryCondition.EQUAL, partbranchID);
		query.addCondition(1, con1);
		query.addAND();
		QueryCondition con2 = new QueryCondition("iterationIfLatest", true);
		query.addCondition(0, con2);
		PersistService ps = (PersistService) getEJBService("PersistService");
		Collection docs = ps.findValueInfo(query);
		Iterator ite = docs.iterator();
		if (ite.hasNext()) {
			doc = (EPMDocumentIfc) ite.next();
		}
		return doc;
	}

	/**
	 * 获得与epm文档关联的零部件
	 * 
	 * @param doc
	 *            EPMDocumentIfc
	 * @return QMPartInfo
	 * @throws QMException
	 */
	public static QMPartInfo getLinkedQMPart(EPMDocumentIfc doc)
			throws QMException {

		QMPartInfo part = null;
		String docbranchID = doc.getBranchID();
		QMQuery query = new QMQuery("QMPart");
		query.appendBso("EPMBuildLinksRule", false);
		QueryCondition con = new QueryCondition("branchID", "rightBsoID");
		query.addCondition(0, 1, con);
		query.addAND();
		QueryCondition con1 = new QueryCondition("leftBsoID",
				QueryCondition.EQUAL, docbranchID);
		query.addCondition(1, con1);
		query.addAND();
		QueryCondition con2 = new QueryCondition("iterationIfLatest", true);
		query.addCondition(0, con2);
		PersistService ps = (PersistService) getEJBService("PersistService");
		Collection docs = ps.findValueInfo(query);
		Iterator ite = docs.iterator();
		if (ite.hasNext()) {
			part = (QMPartInfo) ite.next();
		}
		return part;
	}

	/**
	 * 根据EPM文档编号,获取EPM文档(最新版本)
	 * 
	 * @param num
	 *            String EPM文档编号
	 * @throws QMException
	 * @return EPMDocumentInfo EPM文档
	 */
	public static EPMDocumentInfo getEPMDocInfoByNumber(String num)
			throws QMException {
		EPMDocumentInfo doc = null;
		EPMDocumentMasterInfo master = getEPMDocMasterByNumber(num);
		if (master == null) {
			return null;
		}
		doc = (EPMDocumentInfo) getIteratedByMaster(master);
		return doc;

	}

	/**
	 * 根据EPM文档编号,获取EPM文档主对象
	 * 
	 * @param num
	 *            String EPM文档编号
	 * @throws QMException
	 * @return EPMDocumentMasterIfc EPM文档主对象
	 */
	public static EPMDocumentMasterInfo getEPMDocMasterByNumber(String num)
			throws QMException {
		EPMDocumentMasterInfo master = null;
		QMQuery spec = new QMQuery("EPMDocumentMaster");
		QueryCondition cond = new QueryCondition("docNumber", "=", num);
		spec.addCondition(cond);
		PersistService ser = (PersistService) getEJBService("PersistService");
		Collection coll = (Collection) ser.findValueInfo(spec, false);
		Iterator iterator = coll.iterator();
		if (iterator.hasNext()) {
			master = ((EPMDocumentMasterInfo) iterator.next());
		}
		return master;
	}

	/**
	 * 删除指定的EPM文档对象
	 * 
	 * @param num
	 *            String EPM文档编号
	 * @return boolean
	 */
	public static boolean deleteEPMDoc(String num) {
		if (PublishHelper.VERBOSE)
			System.out
					.println("=====>enter PublishHelper.deleteEpmDoc,docNumber: "
							+ num);
		try {
			EPMDocumentInfo doc = getEPMDocInfoByNumber(num);
			if (doc == null) {
				return false;
			}
			PersistService ser = (PersistService) getEJBService("PersistService");
			ser.deleteValueInfo(doc);
			if (PublishHelper.VERBOSE)
				System.out
						.println("=====>leave PublishHelper.deleteEpmDoc,delete successful");
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			if (PublishHelper.VERBOSE)
				System.out
						.println("=====>leave PublishHelper.deleteEpmDoc,delete failed");
			return false;
		}

	}

	/**
	 * 根据EPM文档的编号,发布源大版本号,发布源的小版本号定位EPM文档对象
	 * 
	 * @param num
	 *            String EPM文档的编号
	 * @param version
	 *            String 发布源大版本号
	 * @param iteration
	 *            String 发布源的小版本号
	 * @throws QMException
	 * @return EPMDocumentInfo 对应的EPM文档对象
	 */
	public static EPMDocumentInfo getEPMDocByOrigInfo(String num,
			String sourceVersion) throws QMException {
		EPMDocumentInfo doc = null;
		EPMDocumentMasterInfo master = getEPMDocMasterByNumber(num);
		if (master == null) {
			return null;
		}
		Collection coll = getAllIterationsOf(master);
		IBAHolderIfc holder;
		// 集合中对象以版本排序,所以找到第一个符合条件的即可
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			doc = (EPMDocumentInfo) iter.next();
			holder = (IBAHolderIfc) doc;
			if (hasIBASourceValue(holder, sourceVersion)) {
				return doc;
			}
		}
		return null;
	}

	public static DocIfc getDocInfoById(String docId) throws QMException {
		PersistService ps = (PersistService) getEJBService("PersistService");
		return (DocIfc) ps.refreshInfo(docId);
	}

	/**
	 * 根据文档ID判断文档是否是“技术中心变更单”类文档
	 * 
	 * @param docId
	 *            文档BsoID
	 * @return
	 */
	public static String isChangeOrder(String docId) {
		DocIfc doc = null;
		try {
			doc = getDocInfoById(docId);
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isChangeOrder(doc);
	}

	/**
	 * 根据文档值对象判断是否是“技术中心变更单”类文档
	 * 
	 * @param doc
	 *            文档值对象
	 * @return
	 */
	public static String isChangeOrder(DocIfc doc) {
		if (doc == null) {
			return "false";
		}
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("====" + doc.getDocCfName());
		//CCBegin SS1
		if (doc.getDocCfName()!=null&&doc.getDocCfName().equals("技术中心变更单")) {
		//CCEnd SS1
			return "true";
		} else {
			return "false";
		}
	}

	/**
	 * 获取"技术中心变更单"类文档在技术中心系统中的描述信息.
	 * 
	 * @param changeOrder
	 * @return
	 */
	public static String getChangeOrderOriDesc(String docId) {
		DocIfc changeOrder = null;
		try {
			changeOrder = getDocInfoById(docId);
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getChangeOrderOriDesc(changeOrder);
	}

	/**
	 * 获取"技术中心变更单"类文档在技术中心系统中的描述信息.
	 * 
	 * @param changeOrder
	 * @return
	 */
	public static String getChangeOrderOriDesc(DocIfc changeOrder) {
		if (changeOrder == null) {
			return null;
		} else {
			String desc = changeOrder.getContDesc();
			if (desc == null || desc.length() == 0) {
				return null;
			}
			if (desc.trim().equals("TOOLONG")) {
				try {
					desc = getChangeOrderDescFromContent(changeOrder);
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (desc == null || desc.length() == 0) {
				return null;
			}
			YQTokenizer tokens = new YQTokenizer(desc,
					CHANGEORDER_DES_PART_SEPERATOR);
			//CCBegin SS8
			if (tokens.countTokens() != 3&&tokens.countTokens() != 2) {
			//CCEnd SS8
				return desc;
			}
			return (String) tokens.nextToken();
		}
	}

	private static String getChangeOrderDescFromContent(DocIfc changeOrder)
			throws QMException {
		ContentService cs = (ContentService) PublishHelper
				.getEJBService("ContentService");
		PersistService ps = (PersistService) PublishHelper
				.getEJBService("PersistService");
		ContentItemIfc primaryContent = cs.getPrimaryContent(changeOrder);
		if (primaryContent == null
				|| !(primaryContent instanceof ApplicationDataInfo)) {
			return null;
		}

		//CCBegin SS4
		if(fileVaultUsed)
		{
			ContentClientHelper helper = new ContentClientHelper();
			byte[] content = helper.requestDownload(((ApplicationDataInfo) primaryContent).getBsoID());
			if(content == null)
				return null;
			else
				return new String(content);
		}
		else
		{
			StreamDataInfo stream = (StreamDataInfo) ps
			.refreshInfo(((ApplicationDataInfo) primaryContent)
					.getStreamDataID());
			if (stream == null) {
				return null;
			}
			return new String(stream.getDataContent());
		}
		//CCEnd SS4
	}

	/**
	 * 根据"技术中心变更单"类文档id获取变更前的零部件
	 * 
	 * @param docId
	 * @return
	 */
	public static Collection getPartBeforechange(String docId) {
		DocIfc changeOrder = null;
		try {
			changeOrder = getDocInfoById(docId);
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getPartBeforechange(changeOrder);
	}

	/**
	 * 根据"技术中心变更单"类文档获取变更前的零部件
	 * 
	 * @param changeOrder
	 *            "技术中心变更单"类文档
	 * @return 字符串数组集合,每个字符串数组记录了一个零部件的id、编号、名称、技术中心版本
	 */
	public static Collection getPartBeforechange(DocIfc changeOrder) {
		Collection result = new ArrayList();
		if (changeOrder == null) {
			return result;
		} else {
			String desc = changeOrder.getContDesc();
			if (desc == null || desc.length() == 0) {
				return result;
			}
			if (desc.trim().equals("TOOLONG")) {
				try {
					desc = getChangeOrderDescFromContent(changeOrder);
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return getPartsBeforeByString(desc);
		}
	}
	/*
	 * 通过文档描述字符串获取变更前的零部件
	 */
	public static Collection getPartsBeforeByString(String desc)
	{
		Collection result = new ArrayList();
		if (desc == null || desc.length() == 0) {
			return result;
		}
		YQTokenizer tokens = new YQTokenizer(desc,
				CHANGEORDER_DES_PART_SEPERATOR);
		if (tokens.countTokens() != 3) {
			return result;
		}
		tokens.nextToken();// 跳过第一个
		String beforePartsMessage = tokens.nextToken();
		tokens = new YQTokenizer(beforePartsMessage,
				CHANGEORDER_PART_PART_SEPERATOR);
		while (tokens.hasMoreTokens()) {
			YQTokenizer partTokens = new YQTokenizer(tokens.nextToken(),
					CHANGEORDER_PART_NUMBER_NAME_VERSION_SEPERATOR);
			//ccbegin by liunan 2008-06-12
			//避免以前无状态情况下，显示不出内容的问题
			if (partTokens.countTokens() == 4||partTokens.countTokens() == 3) {
				//continue;
			//20080410增加了生命周期状态
			String partNumber = partTokens.nextToken();
			String partName = partTokens.nextToken();
			String partVersion = partTokens.nextToken();
			String partState="";
			if(partTokens.countTokens() == 4)//ccbegin by liunan 2008-06-12
			  partState=partTokens.nextToken();
			String partId = "null";
			QMPartInfo part = null;
			try {
				part = getPartInfoByOrigInfo(partNumber, partVersion);
				//CCBegin SS10
				if(part==null&&(partNumber.endsWith("JF")||partNumber.endsWith("JF]")))
				{
					part = getJFPartInfoByOrigInfo(partNumber, partVersion);
				}
				//CCEnd SS10
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (part != null) {
				partId = part.getBsoID();
			}
			result.add(new String[] { partId, partNumber, partName,
					partVersion ,partState});
		  }//ccend by liunan 2008-06-12
		}
		return result;
	}

	/**
	 * 根据"技术中心变更单"类文档id获取变更后的零部件
	 * 
	 * @param docId
	 * @return
	 */
	public static Collection getPartAfterchange(String docId) {
		DocIfc changeOrder = null;
		try {
			changeOrder = getDocInfoById(docId);
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getPartAfterchange(changeOrder);
	}

	/**
	 * 根据"技术中心变更单"类文档获取变更后的零部件
	 * 
	 * @param changeOrder
	 *            "技术中心变更单"类文档
	 * @return 字符串数组集合,每个字符串数组记录了一个零部件的id、编号、名称、技术中心版本
	 */
	public static Collection getPartAfterchange(DocIfc changeOrder) {
		Collection result = new ArrayList();
		//CCBegin SS6
		String value = "";
		//CCEnd SS6
		if (changeOrder == null) {
			return result;
		} else {
			String desc = changeOrder.getContDesc();
			if (desc == null || desc.length() == 0) {
				return result;
			}
			if (desc.trim().equals("TOOLONG")) {
				try {
					desc = getChangeOrderDescFromContent(changeOrder);
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (desc == null || desc.length() == 0) {
				return result;
			}
			YQTokenizer tokens = new YQTokenizer(desc,
					CHANGEORDER_DES_PART_SEPERATOR);
			if (tokens.countTokens() != 3) {
				return result;
			}
			tokens.nextToken();// 跳过第一个
			tokens.nextToken();// 跳过第二个
			String beforePartsMessage = tokens.nextToken();
			tokens = new YQTokenizer(beforePartsMessage,
					CHANGEORDER_PART_PART_SEPERATOR);
			while (tokens.hasMoreTokens()) {
				YQTokenizer partTokens = new YQTokenizer(tokens.nextToken(),
						CHANGEORDER_PART_NUMBER_NAME_VERSION_SEPERATOR);
			//ccbegin by liunan 2008-06-12
			//避免以前无状态情况下，显示不出内容的问题
			if (partTokens.countTokens() == 4||partTokens.countTokens() == 3) {
				//continue;
				//20080410增加了生命周期状态
				String partNumber = partTokens.nextToken();
				String partName = partTokens.nextToken();
				String partVersion = partTokens.nextToken();
			  String partState="";
		  	if(partTokens.countTokens() == 4)//ccbegin by liunan 2008-06-12
			    partState=partTokens.nextToken();
				String partId = "null";
				QMPartInfo part = null;
				try {
					part = getPartInfoByOrigInfo(partNumber, partVersion);
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (part != null) {
					partId = part.getBsoID();
					 //CCBegin SS6
					Vector IBAVec = getIBANameAndValue(partId);
					String[] nameAndValue; 
					String define = "";
					
                    for(int k=0;k<IBAVec.size();k++)
                    {
                        nameAndValue = (String[])IBAVec.get(k);
                        define = nameAndValue[0];
                        if(define.equals("影响互换"))
                        {
                            value = nameAndValue[1];
                            break;
                        }
                        else
                        {
                            value = "";
                        }
                    }
				}
				result.add(new String[] { partId, partNumber, partName,
						partVersion,partState,value });
				//CCEnd SS6
		    }//ccend by liunan 2008-06-12
			}
			return result;
		}
	}
	 //CCBegin SS6
	  /**
	   * 给定事物特性持有者的id,获得其iba属性值等。
	   * @param ibaHolderBsoID String
	   * @return Vector 其中的元素为二维字符串数组,第一维存放该iba属性值的属性定义的名称,
	   * 第二维存放iba属性的值。
	   */
	  public static Vector getIBANameAndValue(String ibaHolderBsoID)
	  {
	    IBAHolderIfc ibaHolderIfc = null;
	    try {
	      PersistService pService = (PersistService) EJBServiceHelper.getService(
	          "PersistService");
	       ibaHolderIfc = (IBAHolderIfc) pService.refreshInfo(ibaHolderBsoID);
	       IBAValueService ibaService = (IBAValueService)EJBServiceHelper.getService("IBAValueService");
	       //CCBegin by liunan 2009-01-14 汽研epm的事物特性单独保存，不从关联part中获取。
	       //掩码如下:
	       /*if(ibaHolderIfc instanceof EPMDocumentIfc)
	       {
	           EPMDocumentIfc epm = (EPMDocumentIfc) ibaHolderIfc;
	           ibaHolderIfc = getLinkedPart(epm);
	           if (ibaHolderIfc==null)
	           {
	             return new Vector();
	           }
	       }*/
	       //CCEnd by liunan 2009-01-14
	       ibaHolderIfc = (IBAHolderIfc)ibaService.refreshAttributeContainer(ibaHolderIfc,null,null,null);
	    }
	    catch (ServiceLocatorException ex) {
	      ex.printStackTrace();
	    }
	    catch (QMException ex)
	    {
	        ex.printStackTrace();
	    }
	    DefaultAttributeContainer container = (DefaultAttributeContainer)ibaHolderIfc.getAttributeContainer();
	    if(container == null)
	      container = new DefaultAttributeContainer();
	    AbstractValueView[] values = container.getAttributeValues();
	    Vector retVal = new Vector();
	    for(int i = 0; i < values.length; i++)
	    {
	      String[] nameAndValue = new String[2];
	      AbstractValueView value = values[i];
	      AttributeDefDefaultView definition = value.getDefinition();
	      nameAndValue[0] = definition.getDisplayName();
	      //modify by liun 2005.04.21
	      if(value instanceof AbstractContextualValueDefaultView)
	      {
	          MeasurementSystemCache.setCurrentMeasurementSystem("SI");
	          String measurementSystem = MeasurementSystemCache.getCurrentMeasurementSystem();
	          //修改4 20081028 zhangq begin 修改原因：零部件瘦客户查看界面带单位的浮点数类型属性显示单位为属性定义的量纲而非默认单位
	          String unitStr = "";
	          if(value instanceof UnitValueDefaultView)
	          {
	              UnitDefView definition1 = ((UnitValueDefaultView)value).getUnitDefinition();
	              QuantityOfMeasureDefaultView quantityofmeasuredefaultview = definition1.getQuantityOfMeasureDefaultView();
	              if (measurementSystem != null)
	              {
	                  //得到属性定义的单位
	                  unitStr = definition1.getDisplayUnitString(
	                          measurementSystem);
	                  //得到计量单位中的显示单位
	                  if (unitStr == null)
	                  {
	                      unitStr = quantityofmeasuredefaultview.
	                           getDisplayUnitString(measurementSystem);
	                  }
	                  //得到计量单位中的量纲
	                  if (unitStr == null)
	                  {
	                      unitStr = quantityofmeasuredefaultview.
	                           getDefaultDisplayUnitString(measurementSystem);
	                  }
	              }

	              DefaultUnitRenderer defaultunitrenderer = new
	                      DefaultUnitRenderer();

	              String ss = "";
	              try
	              {
	                  ss = defaultunitrenderer.renderValue(((UnitValueDefaultView)value).toUnit(), ((UnitValueDefaultView)value).getUnitDisplayInfo(measurementSystem));
	              }
	              catch (UnitFormatException _ex)
	              {
	                  _ex.printStackTrace();
	              }
	              catch (IncompatibleUnitsException _ex)
	              {
	                  _ex.printStackTrace();
	              }

	              //String ddd = ((UnitValueDefaultView)value).getUnitDefinition().getDefaultDisplayUnitString(measurementSystem);

	              //nameAndValue[1] = ss+ddd;
	              //属性值和单位中间用空格分开
	              nameAndValue[1] = ss+"  "+unitStr;
	          }
	          //修改4 20081028 zhangq end
	          else
	          {
	              nameAndValue[1] = ((AbstractContextualValueDefaultView)
	                                 value).
	                                getValueAsString();
	          }
	      }
	      else
	      {
	          nameAndValue[1] = ((ReferenceValueDefaultView) value).
	                            getLocalizedDisplayString();
	      }
	      retVal.add(nameAndValue);
	      nameAndValue = null;
	    }
	    return retVal;
	  }
	//CCEnd SS6
	/**
	 * 根据"技术中心变更单"类文档id获取变更中废弃取消的零部件
	 * @param docId
	 * @author liunan 2008-11-05
	 */
	public static Collection getPartCancelChange(String docId) {
		DocIfc changeOrder = null;
		try {
			changeOrder = getDocInfoById(docId);
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getPartCancelChange(changeOrder);
	}

	/**
	 * 根据"技术中心变更单"类文档获取变更中废弃取消的零部件
	 * 
	 * @param changeOrder "技术中心变更单"类文档
	 *            
	 * @return 字符串数组集合,每个字符串数组记录了一个零部件的id、编号、名称、技术中心版本
	 * @author liunan 2008-11-05
	 */
	public static Collection getPartCancelChange(DocIfc changeOrder) {
		Collection result = new ArrayList();
		if (changeOrder == null) {
			return result;
		} else {
			String desc = changeOrder.getContDesc();
			if (desc == null || desc.length() == 0) {
				return result;
			}
			if (desc.trim().equals("TOOLONG")) {
				try {
					desc = getChangeOrderDescFromContent(changeOrder);
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return getPartsCancelByString(desc);
		}
	}
	/*
	 * 通过文档描述字符串获取变更中废弃取消的零部件
	 * @author liunan 2008-11-05
	 */
	public static Collection getPartsCancelByString(String desc)
	{
		Collection result = new ArrayList();
		if (desc == null || desc.length() == 0) {
			return result;
		}
		YQTokenizer tokens = new YQTokenizer(desc,
				CHANGEORDER_DES_PART_SEPERATOR);
		if (tokens.countTokens() != 3) {
			return result;
		}
		tokens.nextToken();// 跳过第一个
		String beforePartsMessage = tokens.nextToken();
		tokens = new YQTokenizer(beforePartsMessage,
				CHANGEORDER_PART_PART_SEPERATOR);
		while (tokens.hasMoreTokens()) {
			YQTokenizer partTokens = new YQTokenizer(tokens.nextToken(),
					CHANGEORDER_PART_NUMBER_NAME_VERSION_SEPERATOR);
			//ccbegin by liunan 2008-06-12
			//避免以前无状态情况下，显示不出内容的问题
			if (partTokens.countTokens() == 4||partTokens.countTokens() == 3) {
				//continue;
			//20080410增加了生命周期状态
			String partNumber = partTokens.nextToken();
			String partName = partTokens.nextToken();
			String partVersion = partTokens.nextToken();
			String partState="";
			if(partTokens.countTokens() == 4)//ccbegin by liunan 2008-06-12
			  partState=partTokens.nextToken();
			String partId = "null";
			QMPartInfo part = null;
			try {
				part = getPartInfoByOrigInfo(partNumber, partVersion);
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (part != null) {
				partId = part.getBsoID();
			}
			if(partState.equals("CANCELLED"))
			result.add(new String[] { partId, partNumber, partName,
					partVersion ,partState});
		  }//ccend by liunan 2008-06-12
		}
		return result;
	}

	/**
	 * 根据零部件的编号,发布源大版本号,发布源的小版本号定位零部件对象
	 * 
	 * @param num
	 *            String 零部件的编号
	 * @param version
	 *            String 发布源的大版本号
	 * @param iteration
	 *            String 发布源的小版本号
	 * @throws QMException
	 * @return QMPartInfo 零部件对象
	 */
	public static QMPartInfo getPartInfoByOrigInfo(String num,
			String sourceVersion) throws QMException {
		QMPartInfo part = null;
		//CCBegin by liunan 2010-05-05 由于存在同编号的part和gpart因此都要循环过滤。
		//源代码
		/*QMPartMasterIfc master = getPartMasterByNumber(num);
		if (master == null) {
			return null;
		}
		Collection coll = getAllIterationsOf(master);
		IBAHolderIfc holder;
		// 集合中对象以版本排序,所以找到第一个符合条件的即可
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			part = (QMPartInfo) iter.next();
			holder = (IBAHolderIfc) part;
			if (hasIBASourceValue(holder, sourceVersion)) {
				//CCBegin by liunan 2008-12-19 获得父件的时候即判断视图
				if(part.getViewName().trim().equals("工程视图"))
				//CCEnd by liunan 2008-12-19
				return part;
			}
		}*/
		QMQuery query = new QMQuery("QMPartMaster");
		QueryCondition condition = new QueryCondition("partNumber", "=", num);
		query.addCondition(condition);
		QMPartMasterIfc master = null;
		PersistService ser = (PersistService) EJBServiceHelper
				.getService("PersistService");
		Collection collmaster = (Collection) ser.findValueInfo(query, false);
		if (collmaster == null)
		{
			return null;
		}
		Iterator iterator = collmaster.iterator();
		while (iterator.hasNext())
		{
			master = (QMPartMasterInfo) iterator.next();
			Collection coll = getAllIterationsOf(master);
			IBAHolderIfc holder;
			// 集合中对象以版本排序,所以找到第一个符合条件的即可
			for (Iterator iter = coll.iterator(); iter.hasNext();)
			{
				part = (QMPartInfo) iter.next();
				holder = (IBAHolderIfc) part;
				if (hasIBASourceValue(holder, sourceVersion))
				{
					//CCBegin by liunan 2008-12-19 获得父件的时候即判断视图
					//CCBegin SS5
					//if(part.getViewName().trim().equals("工程视图"))
					if(part.getViewName().trim().equals("中心设计视图"))
						//CCEnd SS5
					//CCEnd by liunan 2008-12-19
					return part;
				}
			}
		}
		//CCEnd by liunan 2010-05-05
		return null;
	}

	/**
	 * 根据零部件的编号,发布源大版本号,发布源的小版本号定位零部件对象
	 * 
	 * @param num
	 *            String 零部件的编号
	 * @param version
	 *            String 发布源的大版本号
	 * @param iteration
	 *            String 发布源的小版本号
	 * @throws QMException
	 * @return QMPartInfo 零部件对象
	 */
	public static GenericPartInfo getGPartInfoByOrigInfo(String num,
			String sourceVersion) throws QMException {
		GenericPartInfo part = null;
		GenericPartMasterInfo master = getGPartMasterByNumber(num);
		if (master == null) {
			return null;
		}
		Collection coll = getAllIterationsOf(master);
		IBAHolderIfc holder;
		// 集合中对象以版本排序,所以找到第一个符合条件的即可
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			part = (GenericPartInfo) iter.next();
			holder = (IBAHolderIfc) part;
			if (hasIBASourceValue(holder, sourceVersion)) {
				return part;
			}
		}
		return null;

	}

	/**
	 * 根据文档对象的编号，获取对应的文档对象
	 * 
	 * @param docNumber
	 *            String 文档对象的编号
	 * @throws QMException
	 * @return DocInfo 对应的文档对象
	 */
	public static DocInfo getDocInfoByOrigInfo(String num, String sourceVersion)
			throws QMException {
		DocInfo doc = null;
		DocMasterInfo master = getDocMasterByNumber(num);
		if (master == null) {
			return null;
		}
		// 获取指定全部选代小版本
		Collection coll = getAllIterationsOf(master);
		// 集合中对象以版本排序,所以找到第一个符合条件的即可
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			doc = (DocInfo) iter.next();
			if (hasAffixattr(doc, sourceVersion)) {
				return doc;
			}
			//CCBegin SS11
			if(doc.getLocation().indexOf("试制任务单")!=-1)
			{
				return doc;
			}
			//CCEnd SS11
		}

		return null;
	}

	/**
	 * 判断指定的文档对象的扩展属性中是否有指定发布源信息(大版本号、小版本号)
	 * 
	 * @param doc
	 *            DocInfo 文档对象
	 * @param sourceVersion
	 *            String 发布源的大版本号
	 * @param sourceIteration
	 *            String 发布源的小版本号
	 * @return boolean
	 */
	public static boolean hasAffixattr(DocInfo doc, String sourceVersion) {
		String vdefID = (String) docAffixDef.get(docAffixAttrDefName_version);
		if (vdefID == null) {
			vdefID = getAffixAttrDefIDByName(docAffixAttrDefName_version);
			if (vdefID == null) {
				return false;
			} else {
				docAffixDef.put(docAffixAttrDefName_version, vdefID);
			}
		}
		/*
		 * String idefID =
		 * (String)docAffixDef.get(docAffixAttrDefName_iteration); if(idefID ==
		 * null) { idefID =
		 * getAffixAttrDefIDByName(docAffixAttrDefName_iteration); if(idefID ==
		 * null) return false; else
		 * docAffixDef.put(docAffixAttrDefName_iteration, idefID); } }
		 */
		boolean verOk = false;
		// boolean iterOk = false;
		QMQuery query = null;
		try {
			query = new QMQuery("AttrValue");
			QueryCondition cond1 = new QueryCondition("attrDefID", "=", vdefID);
			query.addCondition(cond1);
			query.addAND();
			cond1 = new QueryCondition("value", "=", sourceVersion);
			query.addCondition(cond1);
			query.addAND();
			cond1 = new QueryCondition("ownerBsoID", "=", doc.getBsoID());
			query.addCondition(cond1);
			PersistService ps = (PersistService) EJBServiceHelper
					.getService("PersistService");
			Collection coll = ps.findValueInfo(query, false);
			Iterator iter = coll.iterator();
			if (iter.hasNext()) {
				verOk = true;
			} //
			/*
			 * query = new QMQuery("AttrValue"); cond1 = new
			 * QueryCondition("attrDefID", "=", idefID);
			 * query.addCondition(cond1); query.addAND(); cond1 = new
			 * QueryCondition("value", "=", sourceIteration);
			 * query.addCondition(cond1); query.addAND(); cond1 = new
			 * QueryCondition("ownerBsoID", "=", doc.getBsoID());
			 * query.addCondition(cond1); coll = ps.findValueInfo(query, false);
			 * iter = coll.iterator(); if (iter.hasNext()) { iterOk = true; }//
			 */

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		if (verOk) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 根据扩展属性名获取属性定义ID
	 * 
	 * @param defName
	 *            String 扩展属性名
	 * @return String 属性定义ID
	 */
	public static String getAffixAttrDefIDByName(String defName) {
		try {
			// 先检查一下文档的扩展属性容器是否定义了
			/*
			 * contID = getContIDByContName(contName); if(contID == null) return
			 * null;
			 */
			AttrDefineInfo def = getAffixAttrDefByName(defName);
			if (def != null) {
				return def.getBsoID();
			} else {
				return null;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据扩展属性名获取属性定义
	 * 
	 * @param defName
	 *            String 扩展属性名
	 * @return AttrDefineInfo 扩展属性定义
	 */
	public static AttrDefineInfo getAffixAttrDefByName(String defName)
			throws QMException {
		// 在cPDM系统中，扩展属性名是唯一的
		if (defName == null) {
			return null;
		}
		QMQuery query = new QMQuery("AttrDefine");
		QueryCondition cond1 = new QueryCondition("attrName", "=", defName);
		query.addCondition(cond1);
		PersistService ps = (PersistService) EJBServiceHelper
				.getService("PersistService");
		Collection defs = ps.findValueInfo(query, false);
		Iterator ite = defs.iterator();
		if (ite.hasNext()) {
			return (AttrDefineInfo) ite.next();
		} else {
			return null;
		}
	}

	/**
	 * 根据扩展属性容器名，获取对应的容器对象
	 * 
	 * @param contName
	 *            String 扩展属性容器名
	 * @throws QMException
	 * @return AttrContainerInfo 对应的容器对象
	 */
	public static AttrContainerInfo getAffixAttrContByName(String contName)
			throws QMException {
		if (contName == null) {
			return null;
		}
		// 获得服务
		AffixAttrService as = (AffixAttrService) EJBServiceHelper
				.getService("AffixAttrService");
		// 根据属性容器名获得容器对象
		return as.findContainerByName(contName);

	} // end

	/**
	 * 根据扩展属性约束名获取扩展属性约束对象
	 * 
	 * @param resName
	 *            String 扩展属性约束名
	 * @throws QMException
	 * @return AttrRestictInfo 扩展属性约束对象
	 */
	public static AttrRestictInfo getAffixAttrResByName(String resName)
			throws QMException {
		if (resName == null) {
			return null;
		}
		// 在cPDM系统中，扩展属性约束名是唯一的
		QMQuery query = new QMQuery("AttrRestict");
		QueryCondition cond1 = new QueryCondition("restName", "=", resName);
		query.addCondition(cond1);
		PersistService ps = (PersistService) EJBServiceHelper
				.getService("PersistService");
		Collection ress = ps.findValueInfo(query, false);
		Iterator ite = ress.iterator();
		if (ite.hasNext()) {
			return (AttrRestictInfo) ite.next();
		} else {
			return null;
		}
	}

	/**
	 * 根据容器名称获取容器ID。
	 * 
	 * @param String
	 *            contName 容器名称;
	 * @return String 容器ID。
	 * @exception com.faw_qm.framework.exceptions.QMException
	 */
	public static String getAffixAttrContIDByName(String contName)
			throws QMException {
		String contID = (String) docAffixCon.get(contName);
		if (contID == null) {
			AttrContainerInfo contInfo = getAffixAttrContByName(contName);
			// 获取容器ID
			if (contInfo != null) {
				contID = contInfo.getBsoID();
				docAffixCon.put(contName, contID);
			}
		}
		return contID;
	} // end

	/**
	 * 在IBA属性中查找发布源信息,判断是否有指定的版本信息
	 * 
	 * @param holder
	 *            IBAHolderIfc
	 * @param sourceVersion
	 *            String 发布源的大版本号
	 * @return boolean =
	 */
	public static boolean hasIBASourceValue(IBAHolderIfc holder,
			String sourceVersion) {
		try {
			IBAValueService vs = (IBAValueService) EJBServiceHelper
					.getService("IBAValueService");
			holder = vs.refreshAttributeContainerWithoutConstraints(holder);
			DefaultAttributeContainer attrCont = (DefaultAttributeContainer) holder
					.getAttributeContainer();
			AbstractValueView values[] = attrCont.getAttributeValues();
			boolean verOk = false;
			// boolean iterOk = false;
			for (int i = 0; i < values.length; i++) {

				if (values[i] instanceof StringValueDefaultView) {
					StringValueDefaultView strValue = (StringValueDefaultView) values[i];
					StringDefView defView = (StringDefView) strValue
							.getDefinition();
					String value = strValue.getValue();
					if (attrDefName_version.equals(defView.getName())
							&& (value != null && value.equals(sourceVersion))) {
						verOk = true;
						continue;
					}
					/*
					 * if (attrDefName_iteration.equals(defView.getName()) &&
					 * (value != null && value.equals(sourceIteration))) {
					 * iterOk = true; continue; }
					 */

				} // end if StringValueDefaultView
			} // end for

			return verOk;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 获取指定对象的检出副本
	 * 
	 * @param workable
	 *            WorkableIfc 已检出的对象
	 * @throws QMException
	 * @return WorkableIfc 已检出的对象的工作副本
	 */
	public static WorkableIfc workingCopyOf(WorkableIfc workable)
			throws QMException {
		WorkInProgressService ws = (WorkInProgressService) EJBServiceHelper
				.getService("WorkInProgressService");
		return ws.workingCopyOf(workable);
	}

	/**
	 * 检出对象，若该对象没有继承Workable接口，则空操作
	 * 
	 * @param work
	 *            WorkableIfc 要检出的对象
	 * @throws QMException
	 */
	public static void checkOut(WorkableIfc work, String desc)
			throws QMException {
		WorkInProgressService ws = (WorkInProgressService) EJBServiceHelper
				.getService("WorkInProgressService");
		ws.checkout(work, ws.getCheckoutFolder(), desc);
	}

	/**
	 * 检入对象
	 * 
	 * @param work
	 *            WorkableIfc 要检入的对象
	 * @param desc
	 *            String 检入描述信息
	 * @throws QMException
	 */
	public static void checkIn(WorkableIfc work, String desc)
			throws QMException {
		WorkInProgressService ws = (WorkInProgressService) EJBServiceHelper
				.getService("WorkInProgressService");
		ws.checkin(work, desc);
	}

	/**
	 * 撤消检出
	 * 
	 * @param work
	 *            WorkableIfc
	 * @throws QMException
	 */
	public static void undoCheckOut(WorkableIfc work) throws QMException {
		WorkInProgressService ws = (WorkInProgressService) EJBServiceHelper
				.getService("WorkInProgressService");
		ws.undoCheckout(work);
	}

	/**
	 * 判断资料项是否在个人资料夹中
	 * 
	 * @param folderEntry
	 *            FolderEntryIfc 资料项
	 * @throws QMException
	 * @return boolean 是否在个人资料夹中
	 */
	public static boolean inPersonalFolder(FolderEntryIfc folderEntry)
			throws QMException {
		if (PersistHelper.isPersistent(folderEntry)) {
			FolderService fs = (FolderService) EJBServiceHelper
					.getService("FolderService");
			return fs.inPersonalFolder(folderEntry);

		} else {
			FolderIfc personalFodler = getPersonalFolder(getCurUserInfo());
			// 当前用户的个人资料夹 路径
			String path = personalFodler.getPath();
			;
			return path.equals(folderEntry.getLocation());
		}
	}

	/**
	 * 获取指定用户的个人资料夹
	 * 
	 * @param user
	 *            UserIfc 指定的用户对象
	 * @throws QMException
	 * @return FolderIfc 个人资料夹
	 */
	public static FolderIfc getPersonalFolder(UserIfc user) throws QMException {
		FolderService fs = (FolderService) EJBServiceHelper
				.getService("FolderService");
		return fs.getPersonalFolder((UserInfo) user);
	}

	/**
	 * 根据组信息,初始IBA设置
	 * 
	 * @param partIBA
	 *            Group
	 * @return Hashtable
	 */
	public static Hashtable initIBA(Group ibaGrp) throws QMException {
		SessionService sservice = (SessionService) getEJBService("SessionService");

		Hashtable hashtable = new Hashtable();
		if (ibaGrp == null || ibaGrp.getElementCount() == 0) {
			return hashtable;
		}
		Vector vector = null;
		Enumeration enumer = ibaGrp.getElements();
		Element ele = null;
		String partNum = null;
		String ibaDef = null;
		String ibaType = null;
		String ibaValue = null;
		AttributeDefDefaultView defaultView = null;
		// 获取IBA服务
		IBADefinitionService ds = null;
		try {
			ds = (IBADefinitionService) getEJBService("IBADefinitionService");
		} catch (Exception ex) {
			ex.printStackTrace();
			return hashtable;
		}
		// 遍历组数据
		while (enumer.hasMoreElements()) {
			// sservice.setAdministrator();
			ele = (Element) enumer.nextElement();
			partNum = ((String) ele.getValue("num")).trim();
			if (hashtable.containsKey(partNum)) {
				vector = (Vector) hashtable.get(partNum);
			} else {
				vector = new Vector();
				hashtable.put(partNum, vector);
			}
			ibaDef = null;
			ibaType = null;
			ibaValue = null;
			ibaType = (String) ele.getValue("IBAType");
			ibaValue = (String) ele.getValue("value");
			// ///////////////////// quxg add,from yanqi
			if (ibaValue == null || ibaValue.equals("")) {
				continue;
			}
			// ///////////////////// quxg add,from yanqi

			ibaDef = (String) ele.getValue("attrDefName");
			//debugInfo("接收的IBA组参数 num＝" + partNum + " ibaDef=" + ibaDef
					//+ " ibaType=" + ibaType + " ibaValue=" + ibaValue);
			try {
				// 该服务方法有cache机制
				// defaultView = ds.getAttributeDefDefaultViewByPath(ibaDef);
				defaultView = null;
				PersistService ps = (PersistService) getEJBService("PersistService");
				QMQuery query = new QMQuery("AbstractAttributeDefinition");
				QueryCondition cond = new QueryCondition("name", "=", ibaDef);
				query.addCondition(cond);
				Collection queryresult = ps.findValueInfo(query);
				AttributeDefinitionIfc definition = null;
				if (queryresult.iterator().hasNext()) {
					definition = (AttributeDefinitionIfc) queryresult
							.iterator().next();
					defaultView = IBADefinitionObjectsFactory
							.newAttributeDefDefaultView(definition);

				}
				// 如果找到了对应的IBA定义
				if (defaultView != null) {
					if (ibaType.equals("BooleanValue")) {
						// log("11111111 IBA属性值是－－"+ibaValue);
						BooleanValueDefaultView booleanValueView = new BooleanValueDefaultView(
								(BooleanDefView) defaultView, Boolean.valueOf(
										ibaValue).booleanValue());
						vector.add(booleanValueView);
						defaultView = null;
						continue;
						// BooleanDefinitionInfo
					}
					if (ibaType.equals("FloatValue")) {
						if (ibaDef.trim().equalsIgnoreCase("Weight")) {
							StringValueDefaultView stringValueView = new StringValueDefaultView(
									(StringDefView) defaultView, ibaValue);
							vector.add(stringValueView);
							defaultView = null;
						} else {
							String precision = (String) ele
									.getValue("precision");

							FloatValueDefaultView floatValueView = new FloatValueDefaultView(
									(FloatDefView) defaultView, Double.valueOf(
											ibaValue).doubleValue(), Integer
											.valueOf(precision).intValue());
							vector.add(floatValueView);
							defaultView = null;
						}
						continue;
					}
					if (ibaType.equals("UnitValue")) {
						String unit = (String) ele.getValue("unit");
						// QuantityOfMeasureDefaultView quantity=
						// ((UnitDefView)defaultView).getQuantityOfMeasureDefaultView();
						UnitDefinitionInfo unitDefinition = (UnitDefinitionInfo) IBADefinitionObjectsFactory
								.newAttributeDefinition(defaultView);
						QuantityOfMeasureIfc quantity = unitDefinition
								.getQuantityOfMeasure();
						Hashtable table = quantity.getDisplayUnitsTable();

						String precision = (String) ele.getValue("precision");
						double doubl = Double.valueOf(ibaValue).doubleValue();
						int in = Integer.valueOf(precision).intValue();
						UnitValueDefaultView unitVauleView = new UnitValueDefaultView(
								(UnitDefView) defaultView, doubl, in);
						vector.add(unitVauleView);
						defaultView = null;

						continue;
					}
					if (ibaType.equals("IntegerValue")) {
						IntegerValueDefaultView integerValueView = new IntegerValueDefaultView(
								(IntegerDefView) defaultView, Integer.valueOf(
										ibaValue).intValue());
						vector.add(integerValueView);
						defaultView = null;
						continue;
					}
					if (ibaType.equals("RatioValue")) {
						String denominator = (String) ele
								.getValue("denominator");
						RatioValueDefaultView ratioValueView = new RatioValueDefaultView(
								(RatioDefView) defaultView, Double.valueOf(
										ibaValue).doubleValue(), Double
										.valueOf(denominator).doubleValue());
						vector.add(ratioValueView);
						defaultView = null;
						continue;
					}
					if (ibaType.equals("StringValue")) {						
						if(defaultView instanceof StringDefView)
						{
						StringValueDefaultView stringValueView = new StringValueDefaultView(
								(StringDefView) defaultView, ibaValue);
						vector.add(stringValueView);
					  }
					  else
					  {
					  	System.out.println("初始IBA数据时出现异常！\r\n" + "根据IBA属性名(" + ibaDef
						+ ")找到对应的定义对象类型不一致.");
					  }
						defaultView = null;
						continue;
					}
					if (ibaType.equals("TimestampValue")) {
						TimestampValueDefaultView timeValueView = new TimestampValueDefaultView(
								(TimestampDefView) defaultView, Timestamp
										.valueOf(ibaValue));
						vector.add(timeValueView);
						defaultView = null;
						continue;
					}
					if (ibaType.equals("UnitValue")) {
						String precision = (String) ele.getValue("precision");
						UnitValueDefaultView unitValueView = new UnitValueDefaultView(
								(UnitDefView) defaultView, Double.valueOf(
										ibaValue).doubleValue(), Integer
										.valueOf(precision).intValue());
						vector.add(unitValueView);
						defaultView = null;
						continue;
					}
					if (ibaType.equals("URLValue")) {
						String description = (String) ele
								.getValue("description");
						URLValueDefaultView urlValueView = new URLValueDefaultView(
								(URLDefView) defaultView, ibaValue, description);
						vector.add(urlValueView);
						defaultView = null;
						continue;
					}
					if (ibaType.equals("ReferenceValue")) {
						String structurename = "com.faw_qm.part.model.QMPartInfo";
						String nodePath = (String) ele.getValue("nodePath");

						int m = nodePath.indexOf("/");
						String windstructurename = nodePath.substring(0, m);
						if (windstructurename.equals("wt.part.WTPart")) {
							structurename = "com.faw_qm.part.model.QMPartInfo";
							nodePath = "Part Type/" + nodePath.substring(m + 1);
						}
						if (windstructurename
								.equals("wt.csm.businessentity.BusinessEntity")) {
							structurename = "com.faw_qm.csm.businessentity.model.BusinessEntityInfo";
							nodePath = nodePath.substring(m + 1);
						}

						ClassificationStructDefaultView classificationstructdefaultview = ClassificationNodeLoader
								.getClassificationStructDefaultView(
										structurename, defaultView);

						if (classificationstructdefaultview == null) {
							errorLog("找不到分类结构" + structurename);
						}
						// ClassificationNodeDefaultView
						// classificationnodedefaultview
						// =ClassificationNodeLoader.findParentClassificationNode(nodePath,
						// classificationstructdefaultview);

						defaultView = classificationstructdefaultview
								.getReferenceDefView();

						ClassificationNodeInfo nodeInfo = findParentClassificationNode(
								nodePath, classificationstructdefaultview);
						ClassificationNodeDefaultView classnodeview = NavigationObjectsFactory
								.newClassificationNodeDefaultView(nodeInfo);

						// String refValueID = getReferenceValueID((
						// ReferenceDefView) defaultView, nodePath,
						// classificationstructdefaultview);
						ReferenceValueDefaultView refValueView = new ReferenceValueDefaultView(
								(ReferenceDefView) defaultView);
						refValueView.setLiteIBAReferenceable(classnodeview);
						vector.add(refValueView);
						defaultView = null;
						continue;
					}
				}

				else {
	       	//CCBegin by liunan 2008-09-03
	      	//为输出语句加开关。
	      	//if (PublishHelper.VERBOSE)
	      	//CCEnd by liunan 2008-09-03
					System.out.println("根据IBA属性名(" + ibaDef
							+ ")找不到对应的定义对象,该数据跳过.");
				}
			} catch (ClassCastException e) {
	    	//CCBegin by liunan 2008-09-03
	    	//为输出语句加开关。
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out.println("初始IBA数据时出现异常！\r\n" + "根据IBA属性名(" + ibaDef
						+ ")找到对应的定义对象类型不一致.");
				errorLog("初始IBA数据时出现异常！" + "根据IBA属性名(" + ibaDef
						+ ")找到对应的定义对象类型不一致.");
				errorLog(e);
				e.printStackTrace();
			} catch (Exception ex) {
				errorLog("初始IBA数据时出现异常！");
				errorLog(ex);
				continue;
			}
		} //
		return hashtable;
	}

	private static String getReferenceValueID(ReferenceDefView defView,
			String s,
			ClassificationStructDefaultView classificationstructdefaultview)
			throws QMException {
		String refValueID = null;
		ReferenceValueInfo refValueInfo = null;
		ClassificationNodeInfo nodeInfo = findParentClassificationNode(s,
				classificationstructdefaultview);

		PersistService pService = (PersistService) getEJBService("PersistService");
		QMQuery query = new QMQuery("ReferenceValue");
		query.addCondition(new QueryCondition("definitionBsoID", "=", defView
				.getBsoID()));

		if (nodeInfo != null) {
			query.addAND();
			query.addCondition(new QueryCondition("iBAReferenceableID", "=",
					nodeInfo.getBsoID()));
		}
		Collection result = pService.findValueInfo(query);

		if (!result.isEmpty() && result.size() > 0) {
			refValueInfo = (ReferenceValueInfo) result.iterator().next();
		}
		if (refValueInfo != null) {
			refValueID = refValueInfo.getBsoID();
		}
		return refValueID;
	}

	private static ClassificationNodeIfc[] getClassificationStructureRootNodes(
			ClassificationStructDefaultView classificationstructdefaultview)
			throws QMException {
		QMQuery query = new QMQuery("ClassificationNode");
		boolean flag = true;
		query.addCondition(new QueryCondition("parentID",
				QueryCondition.IS_NULL));
		query.addAND();
		query.addCondition(new QueryCondition("classificationStructID", "=",
				classificationstructdefaultview.getBsoID()));
		PersistService pService = (PersistService) getEJBService("PersistService");
		Collection queryresult = pService.findValueInfo(query);
		if (queryresult.size() == 0) {
			return null;
		}
		ClassificationNodeInfo aclassificationnodeInfo[] = new ClassificationNodeInfo[queryresult
				.size()];
		Iterator iterator = queryresult.iterator();
		for (int i = 0; i < queryresult.size(); i++) {
			aclassificationnodeInfo[i] = (ClassificationNodeInfo) iterator
					.next();
		}
		return aclassificationnodeInfo;

	}

	public static ClassificationNodeInfo findParentClassificationNode(String s,
			ClassificationStructDefaultView classificationstructdefaultview) {

		ClassificationNodeInfo classificationNodeinfo = null;
		Object obj = null;
		StringTokenizer stringtokenizer = new StringTokenizer(s, "/");
		ClassificationNodeIfc classificationNode[] = null;
		try {
			classificationNode = getClassificationStructureRootNodes(classificationstructdefaultview);

		}

		catch (QMException qmexception) {
			qmexception.printStackTrace();

			return null;
		}

		for (; stringtokenizer.hasMoreTokens();) {
			String s1 = (String) stringtokenizer.nextElement();
			ClassificationNodeNodeView aclassificationnodenodeview[];

			for (int i = 0; i < classificationNode.length; i++) {

				if (classificationNode[i].getName().equalsIgnoreCase(s1)) {
					classificationNodeinfo = (ClassificationNodeInfo) classificationNode[i];

					break;
				}

			}

		}
		return classificationNodeinfo;
	}

	/**
	 * 在IBA属性容器中,设置发布源信息
	 * 
	 * @param holder
	 *            IBAHolderIfc 事物特性持有者
	 * @param props
	 *            Properties 发布源信息
	 * @return IBAHolderIfc 设置后的事物特性持有者
	 */
	public static IBAHolderIfc setPublishSourceInfoByIBA(IBAHolderIfc holder,
			Properties props) {
		DefaultAttributeContainer container = (DefaultAttributeContainer) holder
				.getAttributeContainer();

		if (props == null) {
			return null;
		}
		String dataSource = props.getProperty("dataSource", null);
		String publishDate = props.getProperty("publishDate", null);
		String publishForm = props.getProperty("publishForm", null);
		// 如果实体对象是part，取它的大版本值
		String sourceVersion = props.getProperty("sourceVersion", null);
		String noteNum = props.getProperty("noteNum", null);
		String creater = props.getProperty("creater", null);
		String modifier = props.getProperty("modifier", null);
		// String urlDesc = "查看发布源数据";
		String attrOrgDesc = "数据发布属性组织";
		String attrDef_dataSource_Desc = "数据来源";
		String attrDef_form_Desc = "发布形式";
		String attrDef_version_Desc = "发布源版本";
		String attrDef_date_Desc = "发布时间";
		String attrDef_num_Desc = "发布令号";
		try {
			IBADefinitionService ds = (IBADefinitionService) getEJBService("IBADefinitionService");
			IBAValueService vs = (IBAValueService) getEJBService("IBAValueService");
			StringDefView sourceDefView = null;
			StringDefView formDefView = null;
			StringDefView versionDefView = null;
			StringDefView dateDefView = null;
			StringDefView numDefView = null;
			StringDefView createrView = null;
			StringDefView modifierView = null;
			// 检查是否存在指定的发布源属性组织及属性定义,如果没有则建立
			AttributeOrgNodeView attrOrg = (AttributeOrgNodeView) ibaAttrDefMap
					.get(attrOrgName);
			if (attrOrg == null) {
				AttributeOrgNodeView[] orgs = ds.getAttributeOrganizerRoots();
				for (int i = 0; i < orgs.length; i++) {
					if (attrOrgName.equals(orgs[i].getName())) {
						attrOrg = orgs[i];
						ibaAttrDefMap.put(attrOrgName, attrOrg);
						break;
					}
				} // end for
			}
			// 如果指定的属性组织不存在则创建属性组织及属性定义
			if (attrOrg == null) {
				String msg = "指定的属性组织不存在";
				errorLog(msg);
			}

			// 数据来源
			if (sourceDefView == null) {
				sourceDefView = (StringDefView) ibaAttrDefMap
						.get(attrDefName_source);
			}
			if (sourceDefView == null) {
				sourceDefView = (StringDefView) ds
						.getAttributeDefDefaultViewByPath(attrDefName_source);
			}
			if (sourceDefView == null) {
				errorLog("数据来源的属性定义不存在");
			} else {
				ibaAttrDefMap.put(attrDefName_source, sourceDefView);

				// 发布形式
			}
			if (createrView == null) {
				createrView = (StringDefView) ibaAttrDefMap
						.get(attrDefName_creater);
			}
			if (createrView == null) {
				createrView = (StringDefView) ds
						.getAttributeDefDefaultViewByPath(attrDefName_creater);
			}
			if (createrView == null) {
				errorLog("创建者的属性定义不存在");
			} else {
				ibaAttrDefMap.put(attrDefName_creater, createrView);
				// 发布形式
			}

			if (modifierView == null) {
				modifierView = (StringDefView) ibaAttrDefMap
						.get(attrDefName_modifier);
			}
			if (modifierView == null) {
				modifierView = (StringDefView) ds
						.getAttributeDefDefaultViewByPath(attrDefName_modifier);
			}
			if (modifierView == null) {
				errorLog("更改者的属性定义不存在");
			} else {
				ibaAttrDefMap.put(attrDefName_modifier, modifierView);
				// 发布形式
			}

			if (formDefView == null) {
				formDefView = (StringDefView) ibaAttrDefMap
						.get(attrDefName_form);
			}
			if (formDefView == null) {
				formDefView = (StringDefView) ds
						.getAttributeDefDefaultViewByPath(attrDefName_form);
			}
			if (formDefView == null) {
				errorLog("发布形式的属性定义不存在");
			} else {
				ibaAttrDefMap.put(attrDefName_form, formDefView);

				// 发布源版本号
			}
			if (versionDefView == null) {
				versionDefView = (StringDefView) ibaAttrDefMap
						.get(attrDefName_version);
			}
			if (versionDefView == null) {
				versionDefView = (StringDefView) ds
						.getAttributeDefDefaultViewByPath(attrDefName_version);
			}
			if (versionDefView == null) {
				errorLog("发布版本的属性定义不存在");
			} else {
				ibaAttrDefMap.put(attrDefName_version, versionDefView);
				// 发布时间
			}
			if (dateDefView == null) {
				dateDefView = (StringDefView) ibaAttrDefMap
						.get(attrDefName_date);
			}
			if (dateDefView == null) {
				dateDefView = (StringDefView) ds
						.getAttributeDefDefaultViewByPath(attrDefName_date);
			}
			if (dateDefView == null) {
				errorLog("发布时间的属性定义不存在");
			} else {
				ibaAttrDefMap.put(attrDefName_date, dateDefView);
				// 发布令号
			}
			if (numDefView == null) {
				numDefView = (StringDefView) ibaAttrDefMap.get(attrDefName_num);
			}
			if (numDefView == null) {
				numDefView = (StringDefView) ds
						.getAttributeDefDefaultViewByPath(attrDefName_num);
			}
			if (numDefView == null) {
				errorLog("发布令号的属性定义不存在");
			} else {
				ibaAttrDefMap.put(attrDefName_num, numDefView);

				// 设置发布源相关信息
			}
			// 定义属性值
			if (container == null) {
				container = new DefaultAttributeContainer();
			}
			StringValueDefaultView strValue = null;
			if (publishForm != null && !publishForm.equals("")) {
				strValue = new StringValueDefaultView(formDefView, publishForm);
				container.addAttributeValue(strValue);
			}
			if (holder instanceof QMPartIfc)
			System.out.println("anantt revise   零部件 "+((QMPartIfc)holder).getPartNumber()+"  sourceVersion==="+sourceVersion);//anan
			if (sourceVersion != null && !sourceVersion.equals("")) {
				strValue = new StringValueDefaultView(versionDefView,
						sourceVersion);
				container.addAttributeValue(strValue);
				if (holder instanceof QMPartIfc)
				System.out.println("anantt revise   零部件 "+((QMPartIfc)holder).getPartNumber()+"  strValue==="+strValue);//anan
			}
			if (publishDate != null && !publishDate.equals("")) {
				strValue = new StringValueDefaultView(dateDefView, publishDate);
				container.addAttributeValue(strValue);
			}
			if (dataSource != null && !dataSource.equals("")) {
				strValue = new StringValueDefaultView(sourceDefView, dataSource);
				container.addAttributeValue(strValue);
			}

			if (creater != null && !creater.equals("")) {
				strValue = new StringValueDefaultView(createrView, creater);
				container.addAttributeValue(strValue);
			}
			if (modifier != null && !modifier.equals("")) {
				strValue = new StringValueDefaultView(modifierView, modifier);
				container.addAttributeValue(strValue);
			}
			if (noteNum != null && !noteNum.equals("")) {
				strValue = new StringValueDefaultView(numDefView, noteNum);
				container.addAttributeValue(strValue);
			}
		} catch (Exception ex) {
			errorLog("设置发布源信息失败!");
			ex.printStackTrace();
			errorLog(ex);
		}

		if (holder instanceof QMPartIfc) {
			try {
				AttributeOrgNodeView attrorg1 = null;
				IBADefinitionService ds1 = (IBADefinitionService) getEJBService("IBADefinitionService");
				AttributeOrgNodeView[] orgss = ds1.getAttributeOrganizerRoots();
				for (int i = 0; i < orgss.length; i++) {
					if ((orgss[i].getName()).equals("常用属性")) {
						attrorg1 = orgss[i];
						// ibaAttrDefMap.put(attrOrgName, attrOrg);
						break;
					}
				} // end for
				/*
				 * StringDefView datasourceview = null; datasourceview =
				 * (StringDefView) ds1
				 * .getAttributeDefDefaultViewByPath("dataSource");
				 * StringValueDefaultView strValue1 = new
				 * StringValueDefaultView( datasourceview, "接收图");
				 * container.addAttributeValue(strValue1);
				 */

			} catch (QMException e) {
				e.printStackTrace();
			}

		}
		holder.setAttributeContainer(container);
		return holder;
	}

	/**
	 * 为IBAHolder对象设置IBA属性值(增加方式)
	 * 
	 * @param holder
	 *            IBAHolderIfc IBAHoler对象
	 * @param ibaValues
	 *            Vector 属性值（AbstractContextualValueDefaultView集合）
	 * @return IBAHolderIfc
	 */
	public static IBAHolderIfc setIBAValues(IBAHolderIfc holder,
			Vector ibaValues) {
		if (holder == null || ibaValues == null) {
			return holder;
		}

		try {
			IBAValueService vs = (IBAValueService) getEJBService("IBAValueService");
			if (holder.getAttributeContainer() == null) {
				try {
					holder = vs.refreshAttributeContainer(holder, null, null,
							null);
				} catch (Exception ex) {
					ex.printStackTrace();
					holder
							.setAttributeContainer(new DefaultAttributeContainer());
				}
			}
			DefaultAttributeContainer attrCont = (DefaultAttributeContainer) holder
					.getAttributeContainer();
			if (ibaValues.isEmpty()) {
				log("   IBA值为空...,跳过.");
				return holder;
			}
			for (Iterator ite = ibaValues.iterator(); ite.hasNext();) {
				AbstractValueView valueView = (AbstractValueView) ite.next();
				// debugInfo("轻量级组件是 －－－" +
				// valueView.getLocalizedDisplayString());
				attrCont.setConstraintParameter("CSM");
				attrCont.addAttributeValue(valueView);

			}
		} catch (Exception ex) {
			errorLog("设置IBA值失败!");
			errorLog(ex);
		}
		return holder;
	}

	/**
	 * 获取当前用户对象
	 * 
	 * @throws QMException
	 * @return UserIfc 当前用户对象
	 */
	public static UserIfc getCurUserInfo() throws QMException {
		SessionService ss = (SessionService) getEJBService("SessionService");
		return ss.getCurUserInfo();
	}

	/**
	 * 获取EJB服务
	 * 
	 * @param serName
	 *            String EJB服务名
	 * @throws QMException
	 * @return BaseService EJB服务
	 */
	public static BaseService getEJBService(String serName) throws QMException {
		BaseService ser = EJBServiceHelper.getService(serName);
		if (ser == null) {
			throw new QMException("找不到EJB服务(" + serName + ")");
		}
		return ser;
	}

	public static BaseService getEJBService(String serName, String userName,
			String password) throws QMException {
		BaseService ser = EJBServiceHelper.getService(serName, userName,
				password);
		if (ser == null) {
			throw new QMException("找不到EJB服务(" + serName + ")");
		}
		return ser;
	}

	/**
	 * 根据文件路径,扩展名获取指定路径下的指定扩展名的文件集合
	 * 
	 * @param fqfn
	 *            String 指定的文件路径信息
	 * @param ext
	 *            String 文件的扩展名(默认为list)
	 * @return Collection Blob形表文件集合(FileItem)
	 */
	// public Collection getFileItems(String fqfn, String ext)
	// {
	// Vector v = new Vector();
	// if (fqfn == null)
	// {
	// return v;
	// }
	// File dir = new File(fqfn);
	// if (dir.isDirectory())
	// {
	// //如果是目录,则找以.list为扩展名的文件。
	// if (ext == null)
	// {
	// ext = "list";
	// }
	// FileFilter ff = (FileFilter)new BlobListFileFilter(ext);
	// File[] list = dir.listFiles(ff);
	// for (int i = 0; i < list.length; i++)
	// {
	// //System.out.println(list[i].getName());
	// //System.out.println(list[i].getAbsolutePath());
	// FileItem fi = new FileItem();
	// fi.setFilePathAndName(list[i].getAbsolutePath());
	// fi.setFileName(list[i].getName());
	// v.add(fi);
	// }
	// }
	// return v;
	// }
	public static void testUserTransaction(String affixAttrDefName,
			boolean exception) throws QMException {
		QMTransaction qmt = null;
		try {
			qmt = new QMTransaction();
			qmt.begin();
			AffixAttrService as = (AffixAttrService) getEJBService("AffixAttrService");
			AttrDefineInfo defInfo = new AttrDefineInfo();
			defInfo.setAttrName(affixAttrDefName);
			defInfo.setDisplayName("TestTransaction");
			defInfo = as.saveAttrDefine(defInfo);
			defInfo = new AttrDefineInfo();
			defInfo.setAttrName(affixAttrDefName + "_01");
			defInfo.setDisplayName("TestTransaction");
			defInfo = as.saveAttrDefine(defInfo);

			if (exception) {
				throw new Exception("抛出异常，事务不应提交!");
			}
			qmt.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			qmt.rollback();
		}
	}

	public static String strDecoding(String source, String charset) {
		if (source == null) {
			return null;
		}
		if (source.equals("")) {
			return source;
		}
		if (charset == null || charset.equals("")) {
			charset = "ISO8859-1";

		}
		try {
			String temp_p = source;
			byte[] temp_t = temp_p.getBytes(charset);
			String temp = new String(temp_t);
			return temp;
		} catch (Exception e) {

			return source;
		}
	}

	public static String normalLocation(String location) {
		location = location.replace('/', '\\');
		if (!location.startsWith("\\")) {
			location = "\\" + location;
		}
		if (!location.startsWith("\\Root")) {
			location = "\\Root" + location;

		}
		return location;
	}

	private static void log(Object msg) {

		System.out.println(msg);

	}

	private static void errorLog(Object msg) {
		if (msg instanceof Throwable) {
			System.out.println((Throwable) msg);
		} else {
			System.out.println("*****ERROR: " + msg);
		}
	}

	private static void debugInfo(Object msg) {
		if (DEBUG) {
			System.out.println(msg);
		}
	}

	// whj add for delete parts
	/**
	 * 
	 */
	public static void deleteParts(DocIfc doc) throws Exception {
		/*
		 * SessionService sservice = (SessionService)
		 * EJBServiceHelper.getService( "SessionService");
		 * 
		 * UsersService userservice = (UsersService)
		 * EJBServiceHelper.getService( "UsersService"); UserIfc user =
		 * sservice.getCurUserInfo(); sservice.setAdministrator(); GroupInfo
		 * groupinfo
		 * =(GroupInfo)userservice.getActorValueInfo("Administrators",false);
		 * userservice.groupAddUser(groupinfo,(UserInfo)user);
		 */
		// sservice.setAdministrator();
		String baselinenu = doc.getContDesc();
		StringTokenizer ss = new StringTokenizer(baselinenu, ",");
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("******** basedocdesc :" + baselinenu);
		String baselinenum = ss.nextToken();
		String baselinenum1 = ss.nextToken();
		PersistService pservice = (PersistService) EJBServiceHelper
				.getService("PersistService");
		BaselineService baseService = (BaselineService) EJBServiceHelper
				.getService("BaselineService");
		QMQuery query = new QMQuery("ManagedBaseline");
		QueryCondition condition = new QueryCondition("baselineNumber", "=",
				baselinenum);
		query.addCondition(condition);
		Collection coll = (Collection) pservice.findValueInfo(query, false);

		ManagedBaselineInfo baseline = null;
		Collection parts = null;
		QMPartInfo part = null;
		Vector doclinks = new Vector();
		Vector epmlinks = new Vector();
		for (Iterator ite = coll.iterator(); ite.hasNext();) {
			baseline = (ManagedBaselineInfo) ite.next();
		}

		QMQuery query1 = new QMQuery("ManagedBaseline");
		QueryCondition conditiona = new QueryCondition("baselineNumber", "=",
				baselinenum1);
		query1.addCondition(conditiona);
		Collection coll1 = (Collection) pservice.findValueInfo(query1, false);

		ManagedBaselineInfo baseline1 = null;

		for (Iterator ite1 = coll1.iterator(); ite1.hasNext();) {
			baseline1 = (ManagedBaselineInfo) ite1.next();
		}
		if (baseline1 != null) {
			Collection partsa = baseService.getBaselineItems(baseline1);
			baseService.removeFromBaseline(partsa, baseline1);
		}
		pservice.removeValueInfo(baseline1);
		if (baseline != null) {
			parts = baseService.getBaselineItems(baseline);
			baseService.removeFromBaseline(parts, baseline);

			if (parts != null && !parts.isEmpty()) {

				for (Iterator ites = parts.iterator(); ites.hasNext();) {
					try {
						part = (QMPartInfo) ites.next();

						// 查询零部件所有关联的文档并删除
						query = new QMQuery("PartDescribeLink");
						QueryCondition condition1 = new QueryCondition(
								"leftBsoID", "=", part.getBsoID());
						query.addCondition(condition1);
						Collection docs = (Collection) pservice.findValueInfo(
								query, false);
						for (Iterator docIte = docs.iterator(); docIte
								.hasNext();) {

							PartDescribeLinkInfo doclink = (PartDescribeLinkInfo) docIte
									.next();
							String docid = doclink.getRightBsoID();
							DocInfo partdoc = (DocInfo) pservice
									.refreshInfo(docid);
							pservice.removeValueInfo(doclink);
							pservice.removeValueInfo(partdoc);
	          	//CCBegin by liunan 2008-09-03
	          	//为输出语句加开关。
	          	if (PublishHelper.VERBOSE)
	          	//CCEnd by liunan 2008-09-03
							System.out.println("删除的文档有:" + partdoc.getDocNum());

						}
						// 查询零部件关联的EPM文档并删除
						query = new QMQuery("EPMBuildLinksRule");
						QueryCondition condition2 = new QueryCondition(
								"rightBsoID", "=", part.getBsoID());
						query.addCondition(condition2);
						Collection epmdocs = (Collection) pservice
								.findValueInfo(query, false);
						for (Iterator epmIte = epmdocs.iterator(); epmIte
								.hasNext();) {

							EPMBuildLinksRuleInfo epmdoclink = (EPMBuildLinksRuleInfo) epmIte
									.next();
							String epmid = epmdoclink.getLeftBsoID();
							EPMDocumentInfo epmdoc = (EPMDocumentInfo) pservice
									.refreshInfo(epmid);
							pservice.removeValueInfo(epmdoclink);
							pservice.removeValueInfo(epmdoc);

						}

						pservice.removeValueInfo(part);
	        	//CCBegin by liunan 2008-09-03
	        	//为输出语句加开关。
	        	if (PublishHelper.VERBOSE)
	        	//CCEnd by liunan 2008-09-03
						System.out.println("删除的零部件有:" + part.getPartNumber());
					} catch (QMException e) {
						e.printStackTrace();
					}

				}
			}
		}
		pservice.removeValueInfo(baseline);
		// sservice.freeAdministrator();
		// userservice.groupDeleteUser(groupinfo,(UserInfo)user);
	}

	/**
	 * 根据基线号删除该基线中的零部件及零部件的描述文档和EPM文档
	 * 
	 * @param baselineNumber
	 *            基线编号
	 * @throws Exception
	 */
	public static void deletePublishDataByBaseline(String baselineNumber)
			throws Exception {
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out
				.println("=====>enter PublishHelper.deletePublishDataByBaseline,baselineNumber: "
						+ baselineNumber);
		PersistService pservice = (PersistService) EJBServiceHelper
				.getService("PersistService");
		BaselineService baseService = (BaselineService) EJBServiceHelper
				.getService("BaselineService");
		StandardPartService partService = (StandardPartService) EJBServiceHelper
				.getService("StandardPartService");
		StandardDocService docService = (StandardDocService) EJBServiceHelper
				.getService("StandardDocService");
		QMQuery query = new QMQuery("ManagedBaseline");
		QueryCondition condition = new QueryCondition("baselineNumber", "=",
				baselineNumber);
		query.addCondition(condition);
		Collection coll = (Collection) pservice.findValueInfo(query, false);
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("=====>Baseline amount: " + coll.size());
		ManagedBaselineInfo baseline = null;
		Collection parts = null;
		QMPartIfc part = null;
		for (Iterator ite = coll.iterator(); ite.hasNext();) {
			baseline = (ManagedBaselineInfo) ite.next();
		}
		if (baseline != null) {
			parts = baseService.getBaselineItems(baseline);
	    //CCBegin by liunan 2008-09-03
	    //为输出语句加开关。
	    if (PublishHelper.VERBOSE)
	    //CCEnd by liunan 2008-09-03
			System.out.println("=====>Parts amount: " + parts.size());
			baseService.removeFromBaseline(parts, baseline);
			if (parts != null && !parts.isEmpty()) {
				Collection partsAlreadyDelete = new ArrayList();
				for (Iterator ites = parts.iterator(); ites.hasNext();) {
					// try {
					part = (QMPartIfc) ites.next();
					if (pservice.refreshBso(part.getBsoID()) != null) {
						deletePublishDataByRootPart(part, partsAlreadyDelete);
					}
					// Collection describeDocs = getDescribeDocs(part);
					// Collection epmDocs = getRelatedEpmDocs(part);
					// partService.deletePart(part);
					// pservice.removeValueInfo(part);
					// System.out.println("已删除零部件:" + part.getPartNumber());
					// if (describeDocs != null) {
					// Iterator iter = describeDocs.iterator();
					// while (iter.hasNext()) {
					// DocIfc doc = (DocIfc) iter.next();
					// docService.deleteDoc(doc.getBsoID());
					// pservice.removeValueInfo(doc);
					// System.out
					// .println("已删除描述文档:" + doc.getDocNum());
					// }
					// }
					// if (epmDocs != null) {
					// Iterator iter = epmDocs.iterator();
					// while (iter.hasNext()) {
					// EPMDocumentInfo epmDoc = (EPMDocumentInfo) iter
					// .next();
					// pservice.removeValueInfo(epmDoc);
					// System.out.println("已删除EPM文档:"
					// + epmDoc.getDocNumber());
					// }
					// }

					// } catch (QMException e) {
					// e.printStackTrace();
					// }
				}
			}
		}
		pservice.removeValueInfo(baseline);
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out
				.println("=====>leave PublishHelper.deletePublishDataByBaseline,baselineNumber.");
	}

	private static void removeFromBaselines(QMPartIfc part) {
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out
				.println("enter PublishHelper.removeFromBaselines, part number: "
						+ part == null ? null : part.getPartNumber());
		if (part == null) {
	    //CCBegin by liunan 2008-09-03
	    //为输出语句加开关。
	    if (PublishHelper.VERBOSE)
	    //CCEnd by liunan 2008-09-03
			System.out
					.println("leave PublishHelper.removeFromBaselines, part is null");
		}
		BaselineService baseService = null;
		try {
			baseService = (BaselineService) EJBServiceHelper
					.getService("BaselineService");
		} catch (ServiceLocatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Collection baselines = null;
		try {
			baselines = baseService.getBaselines(part);
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Iterator iter = baselines.iterator();
		while (iter.hasNext()) {
			ManagedBaselineIfc baseline = (ManagedBaselineIfc) iter.next();
			try {
				baseService.removeFromBaseline(part, baseline);
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("leave PublishHelper.removeFromBaselines!");
	}

	protected static Collection getDescribeDocs(QMPartIfc part)
			throws QMException {
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out
				.println("=====>enter PublishHelper.getDescribeDocs,part number: "
						+ part.getPartNumber());
		Collection result = new ArrayList();
		PersistService pservice = (PersistService) EJBServiceHelper
				.getService("PersistService");
		// 查询零部件所有关联的文档并删除
		QMQuery query = new QMQuery("PartDescribeLink");
		QueryCondition condition1 = new QueryCondition("leftBsoID", "=", part
				.getBsoID());
		query.addCondition(condition1);
		Collection docs = (Collection) pservice.findValueInfo(query, false);
		for (Iterator docIte = docs.iterator(); docIte.hasNext();) {
			PartDescribeLinkInfo doclink = (PartDescribeLinkInfo) docIte.next();
			String docid = doclink.getRightBsoID();
			DocInfo partdoc = (DocInfo) pservice.refreshInfo(docid);
			result.add(partdoc);
	    //CCBegin by liunan 2008-09-03
	    //为输出语句加开关。
	    if (PublishHelper.VERBOSE)
	    //CCEnd by liunan 2008-09-03
			System.out.println("获得零部件的描述文档: " + partdoc.getDocNum());
		}
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out
				.println("=====>leave PublishHelper.getDescribeDocs,docs amount: "
						+ result.size());
		return result;
	}

	private static Collection getRelatedEpmDocs(QMPartIfc part)
			throws QMException {
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out
				.println("=====>enter PublishHelper.getRelatedEpmDocs,part number: "
						+ part.getPartNumber());
		Collection result = new ArrayList();
		PersistService pservice = (PersistService) EJBServiceHelper
				.getService("PersistService");
		// 查询零部件所有关联的文档并删除
		QMQuery query = new QMQuery("EPMBuildHistory");
		QueryCondition condition2 = new QueryCondition("rightBsoID", "=", part
				.getBsoID());
		query.addCondition(condition2);
		Collection epmdocs = (Collection) pservice.findValueInfo(query, false);
		for (Iterator epmIte = epmdocs.iterator(); epmIte.hasNext();) {
			EPMBuildHistoryInfo epmdoclink = (EPMBuildHistoryInfo) epmIte
					.next();
			String epmid = epmdoclink.getLeftBsoID();
			EPMDocumentInfo epmdoc = (EPMDocumentInfo) pservice
					.refreshInfo(epmid);
			result.add(epmdoc);
	    //CCBegin by liunan 2008-09-03
	    //为输出语句加开关。
	    if (PublishHelper.VERBOSE)
	    //CCEnd by liunan 2008-09-03
			System.out.println("获得EPM文档 :" + epmdoc.getDocNumber());
		}
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out
				.println("=====>leave PublishHelper.getRelatedEpmDocs, epm amount: "
						+ result.size());
		return result;
	}

	public static void reviseEPM(String epmNum, String partNum)
			throws QMException {

		PersistService pservice = (PersistService) EJBServiceHelper
				.getService("PersistService");
		EPMDocumentInfo epmdoc;

		EPMDocumentMasterInfo master = null;
		QMQuery spec = new QMQuery("EPMDocumentMaster");
		QueryCondition cond = new QueryCondition("docNumber", "=", epmNum);
		spec.addCondition(cond);

		Collection coll = (Collection) pservice.findValueInfo(spec, false);
		Iterator iterator = coll.iterator();
		if (iterator.hasNext()) {
			master = ((EPMDocumentMasterInfo) iterator.next());
		}
		VersionControlService ser = (VersionControlService) EJBServiceHelper
				.getService("VersionControlService");
		Collection collVersion = (Collection) ser.allVersionsOf(master);
		IteratedIfc iteratedIfc = null;
		// 获得master下的所有小版本，按最新版序排列，
		// 则第一个元素即为最新版本版序的零部件对象
		Iterator iterators = collVersion.iterator();
		if (iterators.hasNext()) {
			iteratedIfc = (IteratedIfc) iterators.next();
		}

		epmdoc = (EPMDocumentInfo) iteratedIfc;
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("********** the epmdoc version is :"
				+ epmdoc.getVersionValue());
		epmdoc = (EPMDocumentInfo) ((VersionControlService) EJBServiceHelper
				.getService("VersionControlService"))
				.newVersion((VersionedIfc) epmdoc);
		pservice.saveValueInfo(epmdoc);
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("******** the revise epm version is :"
				+ epmdoc.getVersionValue());

		QMQuery query = new QMQuery("QMPartMaster");
		QueryCondition condition = new QueryCondition("partNumber", "=",
				partNum);
		query.addCondition(condition);
		// query.setChildQuery(true);
		QMPartMasterInfo partmaster = null;

		Collection coll2 = (Collection) pservice.findValueInfo(query, false);
		Iterator iterator2 = coll2.iterator();
		if (iterator2.hasNext()) {
			partmaster = ((QMPartMasterInfo) iterator2.next());
		}
		Collection collVersion1 = (Collection) ser.allVersionsOf(partmaster);
		IteratedIfc iteratedIfc1 = null;
		// 获得master下的所有小版本，按最新版序排列，
		// 则第一个元素即为最新版本版序的零部件对象
		Iterator iterators1 = collVersion1.iterator();
		if (iterators1.hasNext()) {
			iteratedIfc1 = (IteratedIfc) iterators1.next();
		}

		QMPartInfo part = (QMPartInfo) iteratedIfc1;

	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("****** the part is :" + part.getName());
		EPMBuildLinksRuleInfo tempRule = new EPMBuildLinksRuleInfo(epmdoc, part);
		tempRule.setSourceInstance(epmdoc.getFamilyInstance());
		tempRule.setDescription("Build rule for (generic) instance:"
				+ epmdoc.getDocName());

		pservice.saveValueInfo(tempRule);
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("***** save linkrule successful "
				+ tempRule.getRightBsoID() + "," + tempRule.getLeftBsoID());
		EPMBuildHistoryInfo buildHistory = new EPMBuildHistoryInfo();
		buildHistory.setLeftBsoID(epmdoc.getBsoID());
		buildHistory.setRightBsoID(part.getBsoID());
		pservice.saveValueInfo(buildHistory);
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("***** save link history successful ");

	}

	// added by whj
	static boolean saveSourceInfoByIBA = true;

	public static void setSourceInfoByIBA(boolean flag) {
		saveSourceInfoByIBA = flag;
	}

	public static boolean getSourceInfoByIBA() {
		return saveSourceInfoByIBA;
	}

	/**
	 * 更新IBA值,但不做保存
	 * 
	 * @param holder
	 * @param url
	 * @param group
	 * @return
	 * @throws java.lang.Exception
	 */
	public static IBAHolderIfc updateIBA(IBAHolderIfc holder, Properties props,
			Vector vector) throws QMException {
		if (holder == null) {
			return holder;
		}
		// 事物特性持有者。
		holder = ((IBAValueService) PublishHelper
				.getEJBService("IBAValueService")).refreshAttributeContainer(
				holder, null, null, null);
		// 属性容器。
		DefaultAttributeContainer container = (DefaultAttributeContainer) holder
				.getAttributeContainer();
		// container.setConstraintGroups(new ArrayList());
		AbstractValueView aValueView[] = container.getAttributeValues();
		// 删除容器中原有的属性值。
		for (int i = 0; aValueView != null && i < aValueView.length; i++) {
			if (aValueView[i] instanceof ReferenceValueDefaultView) {
				ReferenceValueDefaultView referenceValue = (ReferenceValueDefaultView) aValueView[i];
				if (referenceValue.getLiteIBAReferenceable() != null
						&& referenceValue.getLiteIBAReferenceable()
								.getReferenceID().indexOf("Ranking") == -1) {
					container.deleteAttributeValue(aValueView[i]);
				}
			} else if (aValueView[i] instanceof IntegerValueDefaultView) {
				IntegerValueDefaultView integerValue = (IntegerValueDefaultView) aValueView[i];
				if (integerValue.getReferenceValueDefaultView() == null) {
					container.deleteAttributeValue(aValueView[i]);
				}
			} else {
				container.deleteAttributeValue(aValueView[i]);
			}
		}
		// 添加发布数据属性值。
		if (getSourceInfoByIBA()) {
			holder = PublishHelper.setPublishSourceInfoByIBA(holder, props);
			container = (DefaultAttributeContainer) holder
					.getAttributeContainer();
		}

		// 添加原有属性值。
		if (vector != null) {
			for (int i = 0; i < vector.size(); i++) {
				if (vector.get(i) instanceof BooleanValueDefaultView) {
					BooleanValueDefaultView booleanValue = (BooleanValueDefaultView) vector
							.get(i);
					BooleanValueDefaultView booleanValue1 = new BooleanValueDefaultView(
							(BooleanDefView) booleanValue.getDefinition(),
							booleanValue.isValue());
					if (booleanValue.getReferenceValueDefaultView() != null) {
						booleanValue1.setReferenceValueDefaultView(booleanValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(booleanValue1);
				} else if (vector.get(i) instanceof FloatValueDefaultView) {
					FloatValueDefaultView floatValue = (FloatValueDefaultView) vector
							.get(i);
					FloatValueDefaultView floatValue1 = new FloatValueDefaultView(
							(FloatDefView) floatValue.getDefinition(),
							floatValue.getValue(), floatValue.getPrecision());
					if (floatValue.getReferenceValueDefaultView() != null) {
						floatValue1.setReferenceValueDefaultView(floatValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(floatValue1);
				} else if (vector.get(i) instanceof IntegerValueDefaultView) {
					IntegerValueDefaultView integerValue = (IntegerValueDefaultView) vector
							.get(i);
					IntegerValueDefaultView integerValue1 = new IntegerValueDefaultView(
							(IntegerDefView) integerValue.getDefinition(),
							integerValue.getValue());
					if (integerValue.getReferenceValueDefaultView() == null) {
						container.addAttributeValue(integerValue1);
					}
				} else if (vector.get(i) instanceof RatioValueDefaultView) {
					RatioValueDefaultView ratioValue = (RatioValueDefaultView) vector
							.get(i);
					RatioValueDefaultView ratioValue1 = new RatioValueDefaultView(
							(RatioDefView) ratioValue.getDefinition(),
							ratioValue.getValue(), ratioValue.getDenominator());
					if (ratioValue.getReferenceValueDefaultView() != null) {
						ratioValue1.setReferenceValueDefaultView(ratioValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(ratioValue1);
				} else if (vector.get(i) instanceof ReferenceValueDefaultView) {
					ReferenceValueDefaultView referenceValue1 = (ReferenceValueDefaultView) vector
							.get(i);
					if (referenceValue1.getLiteIBAReferenceable() != null
							&& referenceValue1.getLiteIBAReferenceable()
									.getReferenceID().indexOf("Ranking") == -1) {
						container.setConstraintParameter("CSM");
						container.addAttributeValue(referenceValue1);
					}
				} else if (vector.get(i) instanceof StringValueDefaultView) {
					StringValueDefaultView stringValue = (StringValueDefaultView) vector
							.get(i);
					StringValueDefaultView stringValue1 = new StringValueDefaultView(
							(StringDefView) stringValue.getDefinition(),
							stringValue.getValue());
					if (stringValue.getReferenceValueDefaultView() != null) {
						stringValue1.setReferenceValueDefaultView(stringValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(stringValue1);
				} else if (vector.get(i) instanceof TimestampValueDefaultView) {
					TimestampValueDefaultView timestampValue = (TimestampValueDefaultView) vector
							.get(i);
					TimestampValueDefaultView timestampValue1 = new TimestampValueDefaultView(
							(TimestampDefView) timestampValue.getDefinition(),
							timestampValue.getValue());
					if (timestampValue.getReferenceValueDefaultView() != null) {
						timestampValue1
								.setReferenceValueDefaultView(timestampValue
										.getReferenceValueDefaultView());
					}
					container.addAttributeValue(timestampValue1);
				} else if (vector.get(i) instanceof UnitValueDefaultView) {
					UnitValueDefaultView unitValue = (UnitValueDefaultView) vector
							.get(i);
					UnitValueDefaultView unitValue1 = new UnitValueDefaultView(
							(UnitDefView) unitValue.getDefinition(), unitValue
									.getValue(), unitValue.getPrecision());
					if (unitValue.getReferenceValueDefaultView() != null) {
						unitValue1.setReferenceValueDefaultView(unitValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(unitValue1);
				} else if (vector.get(i) instanceof URLValueDefaultView) {
					URLValueDefaultView urlValue = (URLValueDefaultView) vector
							.get(i);
					URLValueDefaultView urlValue1 = new URLValueDefaultView(
							(URLDefView) urlValue.getDefinition(), urlValue
									.getValue(), urlValue.getDescription());
					if (urlValue.getReferenceValueDefaultView() != null) {
						urlValue1.setReferenceValueDefaultView(urlValue
								.getReferenceValueDefaultView());
					}
					container.addAttributeValue(urlValue1);
				}
			}
		}

		holder.setAttributeContainer(container);
		container = (DefaultAttributeContainer) ((IBAValueService) PublishHelper
				.getEJBService("IBAValueService")).updateAttributeContainer(
				holder, container != null ? container.getConstraintParameter()
						: null, null, null);

		holder.setAttributeContainer(container);
		return holder;
	}

	public static QMPartIfc deleteOldStruct(QMPartIfc parent)
			throws QMException {

		Iterator result = null;
		if (parent instanceof GenericPartInfo) {
			result = ((PersistService) PublishHelper
					.getEJBService("PersistService")).navigateValueInfo(parent,
					"usedBy", "GenericPartUsageLink", false).iterator();
		} else {
			result = ((StandardPartService) PublishHelper
					.getEJBService("StandardPartService")).getUsesPartMasters(
					parent).iterator();
		}
		PartUsageLinkIfc temp;

		while (result.hasNext()) {
			temp = (PartUsageLinkIfc) result.next();
			// PersistenceHelper.manager.delete(temp);
			((PersistService) PublishHelper.getEJBService("PersistService"))
					.removeValueInfo(temp);
		}
		return parent;
	}

	public static QMPartIfc deleteOldDocAssociate(QMPartIfc part)
			throws QMException {
		PersistService pservice = (PersistService) PublishHelper
				.getEJBService("PersistService");

		// 删除零部件的参考关联
		Vector result = (Vector) pservice.navigateValueInfo(part,
				"referencedBy", "PartReferenceLink", false);
		PartReferenceLinkIfc refLinkT;
		for (Iterator ite = result.iterator(); ite.hasNext();) {
			refLinkT = (PartReferenceLinkIfc) ite.next();
			pservice.removeValueInfo(refLinkT);
		}

		// 删除零部件的描述关联
		result = (Vector) pservice.navigateValueInfo(part, "describes",
				"PartDescribeLink", false);
		Enumeration eum = result.elements();
		PartDescribeLinkIfc descLinkT;
		while (eum.hasMoreElements()) {
			descLinkT = (PartDescribeLinkIfc) eum.nextElement();
			pservice.removeValueInfo(descLinkT);
		}

		// 删除零部件与epm的构造规则
		QMQuery branchQuery = new QMQuery("EPMBuildLinksRule");
		QueryCondition condition = new QueryCondition("rightBsoID", "=", part
				.getBranchID());
		branchQuery.addCondition(condition);
		Collection queryresult = pservice.findValueInfo(branchQuery);
		for (Iterator iter = queryresult.iterator(); iter.hasNext();) {
			EPMBuildRuleIfc epmbuildrule = (EPMBuildRuleIfc) iter.next();
			pservice.removeValueInfo(epmbuildrule);
		}
		// 删除零部件与epm的历史规则
		branchQuery = new QMQuery("EPMBuildHistory");
		condition = new QueryCondition("rightBsoID", "=", part.getBsoID());
		branchQuery.addCondition(condition);
		queryresult = pservice.findValueInfo(branchQuery);
		for (Iterator iter = queryresult.iterator(); iter.hasNext();) {
			EPMBuildHistoryIfc link = (EPMBuildHistoryIfc) iter.next();
			pservice.removeValueInfo(link);
		}

		return part;
	}

	public static void revisePartIfNeedRightByNumber(String partNumber) {
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out
				.println("=====>PublishHelper.revisePartIfNeedRightByNumber start,partNumber: "
						+ partNumber);
		try {
			revisePartIfNeedRight((QMPartInfo) getPartInfoByNumber(partNumber));
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out
				.println("=====>PublishHelper.revisePartIfNeedRightByNumber end!");
	}

	public static void revisePartIfNeedRight(QMPartInfo part) {
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("=====>PublishHelper.revisePartIfNeedRight start!");
		if (part == null) {
	    //CCBegin by liunan 2008-09-03
	    //为输出语句加开关。
	    if (PublishHelper.VERBOSE)
	    //CCEnd by liunan 2008-09-03
			System.out
					.println("=====>PublishHelper.revisePartIfNeedRight:传入参数为空！");
			return;
		}
		if (part.getAdHocAcl().getEntrySet().getValue() == null
				|| part.getAdHocAcl().getEntrySet().getValue().equals("")) {
	    //CCBegin by liunan 2008-09-03
	    //为输出语句加开关。
	    if (PublishHelper.VERBOSE)
	    //CCEnd by liunan 2008-09-03
			System.out.println("=====>PublishHelper.revisePartIfNeedRight:零部件"
					+ part.getPartNumber() + "不需要处理!");
		}
		// System.out
		// .println("one:" + part.getAdHocAcl().getEntrySet().getValue());
		StringTokenizer tokens = new StringTokenizer(part.getAdHocAcl()
				.getEntrySet().getValue(), ",");
		StringBuffer newRightString = new StringBuffer();
		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken();
			// System.out.println(token);
			if (token.startsWith("X")) {
				newRightString.append(token + ",");
			}
		}
		String ss = newRightString.toString();
		// System.out.println("dfdfdfdf: " + ss);
		if (ss != null && ss.length() > 0) {
			AclEntrySet set = new AclEntrySet(ss.substring(0, ss.length() - 1));
			AdHocAcl acl = AdHocAcl.newAdHocAcl();
			acl.setEntrySet(set);
			try {
				part = (QMPartInfo) ((VersionControlService) PublishHelper
						.getEJBService("VersionControlService"))
						.newVersion((VersionedIfc) part);
				// System.out.println("two:"
				// + part.getAdHocAcl().getEntrySet().getValue());
			} catch (VersionControlException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println("ori:" + acl.getEntrySet().getValue());
			// part.setAdHocAcl(null);
			try {
				part = (QMPartInfo) ((PersistService) PublishHelper
						.getEJBService("PersistService")).saveValueInfo(part);
			} catch (QMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// System.out.println("fiiin:"
			// + part.getAdHocAcl().getEntrySet().getValue());
		}
		// WorkItem_4406314:+:U.User_11295:-1,lifecycle:+:U.User_11295:99
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("=====>PublishHelper.revisePartIfNeedRight end!");
	}

	public static void updatePartRightByPartNumber(String partNumber) {
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out
				.println("=====>PublishHelper.updatePartRightByPartNumber start,partNumber: "
						+ partNumber);
		try {
			updatePartRightByPart((QMPartInfo) getPartInfoByNumber(partNumber));
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  //CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	 	if (PublishHelper.VERBOSE)
	 	//CCEnd by liunan 2008-09-03
		System.out
				.println("=====>PublishHelper.updatePartRightByPartNumber end!");
	}

	public static void updatePartRightByPart(QMPartInfo part) {
	 	//CCBegin by liunan 2008-09-03
	  //为输出语句加开关。
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("=====>PublishHelper.updatePartRightByPart start!");
		if (part == null) {
	    //CCBegin by liunan 2008-09-03
	   	//为输出语句加开关。
	   	if (PublishHelper.VERBOSE)
	   	//CCEnd by liunan 2008-09-03
			System.out
					.println("=====>PublishHelper.updatePartRightByPart:传入参数为空！");
			return;
		}
		try {
			String childRight = part.getAdHocAcl().getEntrySet().getValue();
			if (childRight == null) {
				childRight = "";
			}
			if (childRight.length() > 0) {
				childRight = childRight + ",";
			}
			StringBuffer childBuffer = new StringBuffer(childRight);
			Collection coll = getParentPart((QMPartMasterIfc) part.getMaster());
			Iterator iter = coll.iterator();
			while (iter.hasNext()) {
				QMPartIfc parent = (QMPartIfc) iter.next();
				String parentRight = parent.getAdHocAcl().getEntrySet()
						.getValue();
				if (parentRight == null || parentRight.indexOf("X") < 0) {
					continue;
				} else {
					StringTokenizer tokens = new StringTokenizer(parentRight,
							",");
					while (tokens.hasMoreTokens()) {
						String token = tokens.nextToken();
						// System.out.println(token);
						if (token.startsWith("X")
								|| childRight.indexOf(token) < 0) {
							childBuffer.append(token + ",");
							childRight = childBuffer.toString();
						}
					}
				}
			}
			AclEntrySet set = new AclEntrySet(childBuffer.substring(0,
					childBuffer.length() - 1));
			AdHocAcl acl = AdHocAcl.newAdHocAcl();
			acl.setEntrySet(set);
			part.setAdHocAcl(acl);
			//CCBegin by liunan 2010-01-31 修改updateBso更新时不再修改时间戳。
			//由原来的两个参数改成三个参数的方法
			((PersistService) PublishHelper.getEJBService("PersistService"))
					.updateBso(part, false, false);
			//CCEnd by liunan 2010-01-31
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  //CCBegin by liunan 2008-09-03
  	//为输出语句加开关。
  	if (PublishHelper.VERBOSE)
	 	//CCEnd by liunan 2008-09-03
		System.out.println("=====>PublishHelper.updatePartRightByPart end!");
	}

	/**
	 * 从一个受动态权限管理的对象中取出借阅单所授予的权限字符串
	 * 
	 * @param ad
	 * @return
	 */
	public static String getXRightString(AdHocControlledIfc ad) {
		if (ad == null) {
			return null;
		}
		String rightString = ad.getAdHocAcl().getEntrySet().getValue();
		if (rightString == null || rightString.equals("")
				|| rightString.indexOf("X") < 0) {
			return null;
		}
		StringBuffer returnBuffer = new StringBuffer();
		StringTokenizer tokens = new StringTokenizer(rightString, ",");
		while (tokens.hasMoreTokens()) {
			String token = tokens.nextToken();
			if (token.startsWith("X")) {
				returnBuffer.append(token + ",");
			}
		}
		String resultString = returnBuffer.toString();
		return resultString.substring(0, resultString.length() - 1);
	}

	public static void addXRight(String partNumber) {
	 	//CCBegin by liunan 2008-09-03
	 	//为输出语句加开关。
	 	if (PublishHelper.VERBOSE)
	 	//CCEnd by liunan 2008-09-03
		System.out
				.println("=====>PublishHelper.addXRight start: " + partNumber);
		try {
			addXRight((QMPartInfo) getPartInfoByNumber(partNumber));
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  //CCBegin by liunan 2008-09-03
	 	//为输出语句加开关。
	 	if (PublishHelper.VERBOSE)
	 	//CCEnd by liunan 2008-09-03
		System.out.println("=====>PublishHelper.addXRight end.");
	}

	public static void addXRight(QMPartIfc part) {
		if (part == null) {
			return;
		}
		String xright = getXRightString(part);
		// System.out.println("=====>right string: " + xright);
		try {
			Collection descDocs = getDescribeDocs(part);
			if (descDocs != null && descDocs.size() > 0) {
				Iterator iter = descDocs.iterator();
				while (iter.hasNext()) {
					DocIfc doc = (DocIfc) iter.next();
					// System.out.println("doc:
					// "+doc.getDocNum()+",rightBeforeMerge:
					// "+doc.getAdHocAcl().getEntrySet().getValue());
					mergeAclEntrySet(xright, doc);
					// System.out.println("rightAfterMerge:
					// "+doc.getAdHocAcl().getEntrySet());
				}
			}
			Collection epmDocs = getRelatedEpmDocs(part);
			if (epmDocs != null && epmDocs.size() > 0) {
				Iterator iter = epmDocs.iterator();
				while (iter.hasNext()) {
					EPMDocumentIfc epm = (EPMDocumentIfc) iter.next();
					// System.out.println("epm:
					// "+epm.getDocNumber()+",rightBeforeMerge:
					// "+epm.getAdHocAcl().getEntrySet().getValue());
					mergeAclEntrySet(xright, epm);
					// System.out.println("rightAfterMerge:
					// "+epm.getAdHocAcl().getEntrySet());
				}
			}
			Collection childs = getPartUsageLinks(part);
			QMPartMasterIfc master = null;
			QMPartIfc child = null;
			PartUsageLinkIfc link;
			Iterator iter = childs.iterator();
			while (iter.hasNext()) {
				link = (PartUsageLinkIfc) iter.next();
				master = (QMPartMasterIfc) link.getUses();
				ViewService viewService = (ViewService) PublishHelper
						.getEJBService("ViewService");
				//CCBegin SS5
				//ViewObjectIfc view = viewService.getView("工程视图");
				ViewObjectIfc view = viewService.getView("中心设计视图");
				//CCEnd SS5
				PartConfigSpecIfc partConfigSpecIfc = new PartConfigSpecInfo();
				partConfigSpecIfc = new PartConfigSpecInfo();
				partConfigSpecIfc.setStandardActive(true);
				partConfigSpecIfc.setBaselineActive(false);
				partConfigSpecIfc.setEffectivityActive(false);
				PartStandardConfigSpec partStandardConfigSpec = new PartStandardConfigSpec();
				partStandardConfigSpec.setViewObjectIfc(view);
				partStandardConfigSpec.setLifeCycleState(null);
				partStandardConfigSpec.setWorkingIncluded(true);
				partConfigSpecIfc.setStandard(partStandardConfigSpec);
				ConfigService cservice = (ConfigService) getEJBService("ConfigService");
				Collection part1 = cservice.filteredIterationsOf(
						(MasteredIfc) master, new PartConfigSpecAssistant(
								partConfigSpecIfc));
				Iterator ite = part1.iterator();
				// 如果没有找到工程视图的版本则使用最新的制造视图版本
				if (ite.hasNext()) {
					Object[] obj = (Object[]) ite.next();
					child = (QMPartIfc) obj[0];
				}
				if (child != null) {
					//System.out.println("child: " + child.getPartNumber()
							//+ ",rightBeforeMerge: "
							//+ child.getAdHocAcl().getEntrySet().getValue());
					child = (QMPartIfc) mergeAclEntrySet(xright, child);
					//System.out.println("rightAfterMerge: "
							//+ child.getAdHocAcl().getEntrySet());
					addXRight(child);
				}
			}
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static AdHocControlledIfc mergeAclEntrySet(String aclValue,
			AdHocControlledIfc ad) {
		if (ad == null) {
			return null;
		}
		if (aclValue == null || aclValue.equals("")) {
			return ad;
		}
		AclEntrySet set = new AclEntrySet(aclValue);
		try {
			set.mergeSet(ad.getAdHocAcl().getEntrySet());
		} catch (InvalidAclEntryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AdHocAcl acl = AdHocAcl.newAdHocAcl();
		acl.setEntrySet(set);
		ad.setAdHocAcl(acl);
		try {
			//CCBegin by liunan 2010-01-31 修改updateBso更新时不再修改时间戳。
			//由原来的两个参数改成三个参数的方法。
			return (AdHocControlledIfc) ((PersistService) PublishHelper
					.getEJBService("PersistService")).updateBso(ad, false, false)
					.getValueInfo();
			//CCEnd by liunan 2010-01-31
		} catch (QMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	private static WorkableIfc checkin(WorkableIfc obj, String note)
			throws QMException {
		if (obj == null) {
			return obj;
		}
		if (WorkInProgressHelper.isCheckedOut(obj)) {
			obj = ((WorkInProgressService) PublishHelper
					.getEJBService("WorkInProgressService")).checkin(obj, note);
		}
		return obj;
	}

	private static WorkableIfc checkout(WorkableIfc obj, String note)
			throws QMException {
		if (obj == null) {
			return obj;
		}
		if (WorkInProgressHelper.isCheckedOut(obj)) {
			if (!WorkInProgressHelper.isWorkingCopy(obj)) {
				obj = (WorkableIfc) PublishHelper.workingCopyOf(obj);
			}
		} else {
			// if (!helper.inPersonalFolder(obj))
			{
				PublishHelper.checkOut(obj, note);
				obj = (WorkableIfc) PublishHelper.workingCopyOf(obj);
			}
		}
		return obj;
	}

	/**
	 * 技术中心发布的对象要根据子资料夹进行映射
	 * 
	 * @param location
	 *            资料夹全路径
	 * @return 截取后的子资料夹
	 */
	public static String cutPathString(String location) {
		if (location == null || location.equals("")) {
			return null;
		}
		if (!(location.indexOf("/") < 0)) {
			StringTokenizer tokens = new StringTokenizer(location, "/");
			tokens.nextToken();
			return tokens.nextToken();
		} else if (!(location.indexOf("\\") < 0)) {
			StringTokenizer tokens = new StringTokenizer(location, "\\");
			tokens.nextToken();
			return tokens.nextToken();
		} else {
			return location;
		}
	}

	/**
	 * 根据文档ID判断文档是否是 采用通知书 的数据接收确认通知书。
	 * 
	 * @param docId
	 *            文档BsoID
	 * @return
	 * @author liunan 2010-01-20
	 */
	public static String isCaiYong(String docId) 
	{
		DocIfc doc = null;
		try 
		{
			doc = getDocInfoById(docId);
		} 
		catch (QMException e) 
		{			
			e.printStackTrace();
		}
		return isCaiYong(doc);
	}

	/**
	 * 根据文档值对象判断文档是否是 采用通知书 的数据接收确认通知书。
	 * 
	 * @param doc
	 *            文档值对象
	 * @return
	 * @author liunan 2010-01-20
	 */
	public static String isCaiYong(DocIfc doc) 
	{
		if (doc == null) 
		{
			return "false";
		}
		//CCBegin by liunan 2010-12-17 修改是否是采用通知书的判断条件，从mappingreceive.properties里
		/*if (doc.getDocNum().startsWith("CONFIRMATION-CT")) 
		{
			return "true";
		}
		else 
		{
			return "false";
		}*/
		//获取复合条件的采用通知书编号前缀。
		String cyName = "";
		try
		{
			cyName = RemoteProperty.getProperty("jfpublish.doc.isCaiYong", "");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		//System.out.println("doc.getDocNum()========"+doc.getDocNum());
		//System.out.println("cyName========"+cyName);
		if(cyName.equals(""))
		{
			return "false";
		}
		
		YQTokenizer tokens = new YQTokenizer(cyName, ",");
		//System.out.println("tokens========"+tokens);
		while (tokens.hasMoreTokens())
		{
			String tempStr = tokens.nextToken();
		//System.out.println("tempStr========"+tempStr);
			if (doc.getDocNum().startsWith(tempStr))
			{
				return "true";
			}
		}
		return "false";
	}

	/**
	 * 获取 采用通知书 的数据接收确认通知书 类文档在技术中心系统中的描述信息.
	 * @param changeOrder
	 * @return
	 * @author liunan 2010-01-20
	 */
	public static String getCaiYongDesc(String docId)
	{
		DocIfc changeOrder = null;
		try 
		{
			changeOrder = getDocInfoById(docId);
		} 
		catch (QMException e) 
		{
			e.printStackTrace();
		}
		return getCaiYongDesc(changeOrder);
	}

	/**
	 * 获取 采用通知书 的数据接收确认通知书 类文档在技术中心系统中的描述信息.
	 * @param changeOrder
	 * @return
	 * @author liunan 2010-01-20
	 */
	public static String getCaiYongDesc(DocIfc changeOrder) 
	{
		if (changeOrder == null) 
		{
			return null;
		} 
		else 
		{
			String desc = changeOrder.getContDesc();
			if (desc == null || desc.length() == 0) 
			{
				return null;
			}
			//此处采用 也使用变更的分隔符。
			YQTokenizer tokens = new YQTokenizer(desc,
					CHANGEORDER_DES_PART_SEPERATOR);
			if (tokens.countTokens() != 2) 
			{
				return desc;
			}
			return (String) tokens.nextToken();
		}
	}
	
	/**
	 * 根据 采用通知书 的数据接收确认通知书 类文档id获取采用的零部件
	 * @param docId 采用通知书 的数据接收确认通知书 类文档
	 * @return 字符串数组集合,每个字符串数组记录了一个零部件的id、编号、名称、技术中心版本
	 * @author liunan 2010-01-20
	 */
	public static Collection getPartCaiYong(String docId) 
	{
		DocIfc changeOrder = null;
		try 
		{
			changeOrder = getDocInfoById(docId);
		} 
		catch (QMException e) 
		{
			e.printStackTrace();
		}
		return getPartCaiYong(changeOrder);
	}

	/**
	 * 根据 采用通知书 的数据接收确认通知书 类文档获取采用的零部件
	 * @param changeOrder  采用通知书 的数据接收确认通知书 类文档
	 * @return 字符串数组集合,每个字符串数组记录了一个零部件的id、编号、名称、技术中心版本
	 * @author liunan 2010-01-20
	 */
	public static Collection getPartCaiYong(DocIfc changeOrder) 
	{
		Collection result = new ArrayList();
		if (changeOrder == null) 
		{
			return result;
		} 
		else 
		{
			String desc = changeOrder.getContDesc();
			if (desc == null || desc.length() == 0) 
			{
				return result;
			}
			//CCBegin by liunan 2010-01-29 说明过长时的处理
			if (desc.trim().indexOf("TOOLONG")>=0) 
			{
				try 
				{
					desc = getCaiYongDescFromContent(changeOrder);
				} 
				catch (QMException e) 
				{
					e.printStackTrace();
				}
			}
			//CCEnd by liunan 2010-01-29
			return getCaiYongByString(desc);
		}
	}
	
	/*
	 * 通过文档描述字符串获取采用的零部件
	 * @author liunan 2010-01-20
	 */
	public static Collection getCaiYongByString(String desc)
	{
		Collection result = new ArrayList();
		if (desc == null || desc.length() == 0) 
		{
			return result;
		}
		YQTokenizer tokens = new YQTokenizer(desc,
				CHANGEORDER_DES_PART_SEPERATOR);
		if (tokens.countTokens() != 2) 
		{
			return result;
		}
		tokens.nextToken();// 跳过第一个
		String cyPartsMessage = tokens.nextToken();
		tokens = new YQTokenizer(cyPartsMessage,
				CHANGEORDER_PART_PART_SEPERATOR);
		while (tokens.hasMoreTokens()) 
		{
			YQTokenizer partTokens = new YQTokenizer(tokens.nextToken(),
					CHANGEORDER_PART_NUMBER_NAME_VERSION_SEPERATOR);
			//避免以前无状态情况下，显示不出内容的问题
			if (partTokens.countTokens() == 3) 
			{
				String partNumber = partTokens.nextToken();
				String partName = partTokens.nextToken();
				String partVersion = partTokens.nextToken();
				String partId = "null";
				QMPartInfo part = null;
				try 
				{
					part = getPartInfoByOrigInfo(partNumber, partVersion);
				} 
				catch (QMException e) 
				{
					e.printStackTrace();
				}
				if (part != null)
				{
					partId = part.getBsoID();
				}
				result.add(new String[] {partId, partNumber, partName, partVersion});
		  }
		}
		return result;
	}
	
	/*
	 * 将发布过来的采用零部件列表的字符串整理成需要的格式
	 * 发布来的格式：{status=工作中, level=00, version=A.2, number=2010123, name=123}@@@{status=工作中, level=01, version=A.2, number=20100118-4, name=hill}
	 * 整理后的格式：!$%*&2010123!*!123!*!A.2!*%#!20100118-4!*!hill!*!A.2
	 * @author liunan 2010-01-21
	 */
	public static String doCaiYongList(String desc)
	{
		if(desc.equals(""))
		{
			return "";
		}
		if(desc.indexOf("@@@")<0)
		{
			return CHANGEORDER_DES_PART_SEPERATOR+doCaiYongPartString(desc);
		}
		String result = "";
		YQTokenizer tokens = new YQTokenizer(desc, "@@@");
		while (tokens.hasMoreTokens()) 
		{
			if(result.equals(""))
			{
				result = doCaiYongPartString(tokens.nextToken());
			}
			else
			{
				result = result + CHANGEORDER_PART_PART_SEPERATOR + doCaiYongPartString(tokens.nextToken());
			}
		}
		return CHANGEORDER_DES_PART_SEPERATOR+result;
	}
	
	/*
	 * 整理采用零部件表中的一个零部件的属性字符串。
	 * @author liunan 2010-01-21
	 */
	public static String doCaiYongPartString(String desc)
	{
		YQTokenizer tokens = new YQTokenizer(desc, ",");
		tokens.nextToken();  //状态
		tokens.nextToken();  //层级
		String temp = tokens.nextToken();
		String versionStr = temp.substring(temp.indexOf("=")+1);
		temp = tokens.nextToken();
		String numStr = temp.substring(temp.indexOf("=")+1);
		temp = tokens.nextToken();
		String nameStr = temp.substring(temp.indexOf("=")+1, temp.length()-1);
		return numStr+CHANGEORDER_PART_NUMBER_NAME_VERSION_SEPERATOR+nameStr
		          +CHANGEORDER_PART_NUMBER_NAME_VERSION_SEPERATOR+versionStr;
	}

	/*
	 * 根据 采用通知书 的数据发布确认通知书文档 获取关联的记录说明信息的主要内容。
	 * @author liunan 2010-01-29
	 */	
	private static String getCaiYongDescFromContent(DocIfc changeOrder)
			throws QMException 
	{
		ContentService cs = (ContentService) PublishHelper.getEJBService("ContentService");
		PersistService ps = (PersistService) PublishHelper.getEJBService("PersistService");
		
		Vector v = cs.getContents(changeOrder);
		ContentItemIfc item = null;
		ApplicationDataInfo appDataInfo = null;
		for (Iterator iter = v.iterator(); iter.hasNext(); )
		{
			item = (ContentItemIfc) iter.next();
			if (item instanceof ApplicationDataInfo) 
			{
				appDataInfo = (ApplicationDataInfo) item;
				if (appDataInfo.getFileName().equals("caiyongdescription.txt")) 
				{
					//CCBegin SS4
					if(fileVaultUsed)
					{
						ContentClientHelper helper = new ContentClientHelper();
						byte[] b = helper.requestDownload(appDataInfo.getBsoID());
						return new String(b);
					}
					else
					{
						StreamDataInfo stream = (StreamDataInfo)ps.refreshInfo(appDataInfo.getStreamDataID());
						return new String(stream.getDataContent());
					}
					//CCEnd SS4
				}
			}
		}
		return null;
	}
	
	
	//CCBegin SS3
	/**
	 * 根据文档ID判断文档是否是“数据接收确认通知书”类文档
	 * 
	 * @param docId
	 *            文档BsoID
	 * @return
	 */
	public static String isConfirmDoc(String docId) {
		DocIfc doc = null;
		try {
			doc = getDocInfoById(docId);
		} catch (QMException e) {
			e.printStackTrace();
		}
		return isConfirmDoc(doc);
	}

	/**
	 * 根据文档值对象判断是否是“数据接收确认通知书”类文档
	 * 
	 * @param doc
	 *            文档值对象
	 * @return
	 */
	public static String isConfirmDoc(DocIfc doc) {
		if (doc == null) {
			return "false";
		}
		
		if (doc.getDocCfName().equals("数据接收确认通知书")) {
			return "true";
		} else {
			return "false";
		}
	}
	
	/**
	 * 获取数据确认通知书的关联变更单
	 * @param docId 数据确认通知书BsoID
	 * @return docIfc 关联的变更单值对象
	 * @throws QMException
	 */
	public static Collection getConfirmDoc(String docId) throws QMException {
		//数据确认通知书
		DocIfc doc = getDocInfoById(docId);
		String ConfirmDocNum = doc.getDocNum();
		String key = ConfirmDocNum.substring(ConfirmDocNum.indexOf("CONFIRMATION-") + 13, ConfirmDocNum.length());
		
		//变更单
		DocIfc docIfc = null;
		PersistService ps = (PersistService) EJBServiceHelper.getPersistService();
		
		QMQuery query = new QMQuery("Doc", "DocMaster");
		int k = query.appendBso("DocClassification", false);
		//QueryCondition con1 = new QueryCondition("technicsName",QueryCondition.LIKE,"%"+stepName+"%");
		query.addCondition(0, new QueryCondition("location", QueryCondition.LIKE, "%技术中心变更单%"));
		query.addAND();
		query.addCondition(1, new QueryCondition("docNum", QueryCondition.LIKE, "%" + key + "%"));
		query.addAND();
		query.addCondition(k, new QueryCondition("docCfName", QueryCondition.EQUAL, "技术中心变更单"));
		query.addAND();
		query.addCondition(0, new QueryCondition("iterationIfLatest", true));
		query.addAND();
		query.addCondition(0, 1, new QueryCondition("masterBsoID", "bsoID"));
		query.addAND();
		query.addCondition(0, k, new QueryCondition("docCfBsoID", "bsoID"));
		
		query.addOrderBy(0, "createTime", true);
		
		query.setVisiableResult(1);
		
		Collection result = ps.findValueInfo(query);
		
		if (result != null && result.size() != 0) {
      	  	Iterator iter = result.iterator();
      	  	
      	  	while (iter.hasNext()) {
            	docIfc = (DocIfc) iter.next();
            }
		}

		return result;
	}
	//CCEnd SS3

	//CCBegin SS7
	public static String getIBAValue(IBAHolderIfc holder, String def)
	{
		try
		{
			IBAValueService vs = (IBAValueService) EJBServiceHelper
					.getService("IBAValueService");
			holder = vs.refreshAttributeContainerWithoutConstraints(holder);
			DefaultAttributeContainer attrCont = (DefaultAttributeContainer) holder
					.getAttributeContainer();
			AbstractValueView values[] = attrCont.getAttributeValues();
			boolean verOk = false;
			for (int i = 0; i < values.length; i++)
			{
				if (values[i] instanceof StringValueDefaultView)
				{
					StringValueDefaultView strValue = (StringValueDefaultView) values[i];
					StringDefView defView = (StringDefView) strValue.getDefinition();
					String value = strValue.getValue();
					if (defView.getName().equals(def) && value != null)
					{
						return value;//有值或空串
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		return null;
	}
	//CCEnd SS7
	
	//CCBegin SS8
	/*
	 * 将发布过来的试制任务表的字符串，生成到excel中，并给发给解放的试制状态零部件设置 更改通知书号 属性
	 * 发布来的格式：{status=工作中, level=是, version=A.2, number=2010123, name=123}@@@{status=工作中, level=否, version=A.2, number=20100118-4, name=hill}
	 */
	public static void createShiZhiRenWu(DocInfo docInfo, String desc)
	{
		System.out.println("进入 createShiZhiRenWu docInfo="+docInfo.getDocNum()+" desc="+desc);
		if(desc.equals(""))
		{
			return;
		}
		YQTokenizer tokens = new YQTokenizer(desc, CHANGEORDER_DES_PART_SEPERATOR);
		if (tokens.countTokens() != 2)
		{
			return;
		}
		tokens.nextToken();// 跳过第一个
		
		String dirStr = RemoteProperty.getProperty("ShiZhiRenWu", "");
		File f1 = new File(dirStr);
		if(!f1.exists())
		{
			f1.mkdir();
		}
		
		String urlroot = "http://" + RemoteProperty.getProperty("server.hostName", "") + RemoteProperty.getProperty("routeListTemplate", "/PhosphorPDM/template/");
    String url = urlroot + "ShiZhiRenWu.xls";
    
    InputStream stream = null;
    try
    {
      URL aurl = new URL(url);
      stream = aurl.openStream();
      POIFSFileSystem fs = null;
      HSSFWorkbook wb = null;
      fs = new POIFSFileSystem(stream);
      wb = new HSSFWorkbook(fs);
      
      PersistService ps = (PersistService) EJBServiceHelper.getPersistService();
      
      tokens = new YQTokenizer(tokens.nextToken(), "@@@");
      int i = 1;
      HSSFSheet sheet = wb.getSheetAt(0);
      while (tokens.hasMoreTokens())
      {
      	String desction = tokens.nextToken();
      	System.out.println("desction="+desction);
      	YQTokenizer ts = new YQTokenizer(desction, ",");
      	String temp = ts.nextToken();
      	String status = temp.substring(temp.indexOf("=")+1);
      	temp = ts.nextToken();
      	String isjf = temp.substring(temp.indexOf("=")+1);
      	temp = ts.nextToken();
      	String versionStr = temp.substring(temp.indexOf("=")+1);
      	temp = ts.nextToken();
      	String numStr = temp.substring(temp.indexOf("=")+1);
      	temp = ts.nextToken();
      	String nameStr = temp.substring(temp.indexOf("=")+1);
      	
      	HSSFRow row = sheet.createRow((short) i);
      	HSSFCell cell = row.createCell( (short) 0);
      	cell.setCellValue(isjf);//是否解放准备
      	
      	cell = row.createCell( (short) 1);
      	cell.setCellValue(numStr);//编号
      	
      	cell = row.createCell( (short) 2);
      	cell.setCellValue(nameStr);//名称
      	
      	cell = row.createCell( (short) 3);
      	cell.setCellValue(versionStr);//版本
      	
      	QMPartInfo part = (QMPartInfo)getPartInfoByOrigInfo(numStr, versionStr);
      	System.out.println("part="+part);
      	
      	if(part==null)
      	{
      		part = (QMPartInfo)getPartInfoByNumber(numStr);
      	}
      	
      	cell = row.createCell( (short) 4);
      	cell.setCellValue(part.getLifeCycleState().getDisplay());//状态
      	i++;
      	System.out.println("isjf="+isjf+"  numStr="+numStr+"  nameStr="+nameStr+"  versionStr="+versionStr+"  status="+status);
      	//如果是试制状态零部件，则给在零部件的版本注释里，记录是“汽研试制”还是“解放试制”
      	if(status.indexOf("TEST")!=-1)
      	{
      		if(isjf.equals("是"))
      		{
      			part.setIterationNote("解放试制");
      			part = (QMPartInfo) ps.saveValueInfo(part, false);
      		}
      		else if(isjf.equals("否"))
      		{
      			part.setIterationNote("汽研试制");
      			part = (QMPartInfo) ps.saveValueInfo(part, false);
      		}
      	}
      	//如果是发给解放的试制状态零部件，则维护IBA属性“更改通知书号”
      	/*if(isjf.equals("是")&&status.indexOf("TEST")!=-1)
      	{
      		QMQuery query1 = new QMQuery("StringValue");
      		query1.addCondition(new QueryCondition("iBAHolderBsoID", "=",part.getBsoID()));
      		query1.addAND();
      		query1.addCondition(new QueryCondition("definitionBsoID", "=","StringDefinition_6116703"));
      		Collection result1 = ps.findValueInfo(query1,false);
      		if(result1!=null&&result1.size()>0)
      		{
      			StringValueInfo svi = (StringValueInfo) result1.iterator().next();
      			System.out.println("零部件 "+numStr+" 的原更改通知书号为："+svi.getValue()+" 新试制任务单号为："+docInfo.getDocNum());
      			PublishPartsLog.log("零部件 "+numStr+" 的原更改通知书号为："+svi.getValue()+" 新试制任务单号为："+docInfo.getDocNum());
      			if(!svi.getValue().equals(docInfo.getDocNum()))
      			{
      				svi.setValue(docInfo.getDocNum());
      				ps.updateBso(svi, false);
      			}
      		}
      		else
      		{
      			System.out.println("零部件 "+numStr+" 的试制任务单号为："+docInfo.getDocNum());
      			PublishPartsLog.log("零部件 "+numStr+" 的试制任务单号为："+docInfo.getDocNum());
      			StringDefinitionIfc definition = (StringDefinitionIfc) ps.refreshInfo("StringDefinition_6116703", false);
      			AttributeDefDefaultView defaultView = IBADefinitionObjectsFactory.newAttributeDefDefaultView(definition);
      			StringValueDefaultView stringValueView = new StringValueDefaultView((StringDefView) defaultView, docInfo.getDocNum());
      			IBAValueService ibaService = (IBAValueService) EJBServiceHelper.getService("IBAValueService");
      			IBAHolderIfc holder = ibaService.refreshAttributeContainer(part, null, null, null);
      			DefaultAttributeContainer container = (DefaultAttributeContainer) holder.getAttributeContainer();
      			container.addAttributeValue(stringValueView);
      			holder.setAttributeContainer(container);
      			container = (DefaultAttributeContainer)ibaService.updateAttributeContainer(holder, container != null ? container.getConstraintParameter() : null, null, null);
      			holder.setAttributeContainer(container);
      			ps.saveValueInfo(holder, false);
      		}
      	}*/
      }
    	File jfile = new File(dirStr+docInfo.getDocNum()+".xls");
    	FileOutputStream out = new FileOutputStream(jfile);
    	wb.write(out);
    	out.close();
    	//文件保存完毕
    	System.out.println("文件保存完毕，jfile="+jfile.getPath());
    	//将文件挂到文档附件上。
			ContentService cser = (ContentService) PublishHelper.getEJBService("ContentService");
    	//File f = new File(dirStr+docInfo.getDocNum()+".xls");
    	if(fileVaultUsed)
    	{
    		ContentClientHelper helper = new ContentClientHelper();
    		ApplicationDataInfo app = helper.requestUpload(jfile);
    		app.setFileName(jfile.getName());
    		app.setUploadPath(jfile.getPath());
    		app.setFileSize(jfile.length());
    		app = (ApplicationDataInfo) cser.uploadContent(docInfo, app);
    	}
    	else
    	{
    		ApplicationDataInfo app = new ApplicationDataInfo();
    		app.setFileName(jfile.getName());
    		app.setUploadPath(jfile.getPath());
    		app.setFileSize(jfile.length());
    		ApplicationDataInfo datainfo = (ApplicationDataInfo) cser.uploadContent(docInfo, app);
    		String streamid = datainfo.getStreamDataID();
    		DataInputStream dataInputStream = null;
    		byte[] data = null;
    		data = new byte[(int) jfile.length()];
    		dataInputStream = new DataInputStream(new FileInputStream(jfile));
    		dataInputStream.read(data);
    		dataInputStream.close();
    		StreamUtil.writeData(streamid, data);
    	}
    	System.out.println("文档附件添加完毕！");
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    catch (Exception ex1)
    {
      ex1.printStackTrace();
    }
	}
	
	/**
	 * 根据"技术中心变更单"类文档获取发布给解放的零部件
	 * @param changeOrder "技术中心变更单"类文档
	 * @return 字符串数组集合,每个字符串数组记录了一个零部件的id、编号、名称、技术中心版本
	 */
	public static Collection getJFShiZhiPart(String docId)
	{
		Collection result = new ArrayList();
		DocIfc changeOrder = null;
		try
		{
			changeOrder = getDocInfoById(docId);
		} 
		catch (QMException e) 
		{
			e.printStackTrace();
		}
			
		String value = "";
		if (changeOrder == null)
		{
			return result;
		}
		else
		{
			String desc = changeOrder.getContDesc();
			if (desc == null || desc.length() == 0)
			{
				return result;
			}
			if (desc.trim().equals("TOOLONG"))
			{
				try
				{
					desc = getChangeOrderDescFromContent(changeOrder);
				}
				catch (QMException e)
				{
					e.printStackTrace();
				}
			}
			if (desc == null || desc.length() == 0)
			{
				return result;
			}
			YQTokenizer tokens = new YQTokenizer(desc, CHANGEORDER_DES_PART_SEPERATOR);
			if (tokens.countTokens() != 2)
			{
				return result;
			}
			tokens.nextToken();// 跳过第一个
      tokens = new YQTokenizer(tokens.nextToken(), "@@@");
      while (tokens.hasMoreTokens())
      {
      	String desction = tokens.nextToken();
      	System.out.println("desction="+desction);
      	YQTokenizer ts = new YQTokenizer(desction, ",");
      	String temp = ts.nextToken();
      	String partState = temp.substring(temp.indexOf("=")+1);//状态
      	temp = ts.nextToken();
      	String isjf = temp.substring(temp.indexOf("=")+1);//是否解放试制
      	
      	//if(isjf.equals("否")||partState.indexOf("TEST")==-1)
      	if(partState.indexOf("TEST")!=-1)
      	{
      		temp = ts.nextToken();
      		String partVersion = temp.substring(temp.indexOf("=")+1);//版本
      		temp = ts.nextToken();
      		String partNumber = temp.substring(temp.indexOf("=")+1);//编号
      		temp = ts.nextToken();
      		String partName = temp.substring(temp.indexOf("=")+1);//名称
      		
      		String partId = "null";
      		QMPartInfo part = null;
      		try
      		{
      			part = getPartInfoByOrigInfo(partNumber, partVersion);
      		}
      		catch (QMException e)
      		{
      			e.printStackTrace();
      		}
      		if (part != null)
      		{
      			partId = part.getBsoID();
      			partState = part.getLifeCycleState().getDisplay();
      		}
      		result.add(new String[] {partId, partNumber, partName, partVersion, partState});
      	}
			}
			return result;
		}
	}
	//CCEnd SS8
	
	
	//CCBegin SS9
	/*
	 * 通过文档描述字符串获取参考的零部件
	 */
	public static Vector getParts(String desc)
	{
		Vector result = new Vector();
		if (desc == null || desc.length() == 0)
		{
			return result;
		}
		YQTokenizer tokens = new YQTokenizer(desc, CHANGEORDER_DES_PART_SEPERATOR);
		System.out.println("描述的分段数："+tokens.countTokens());
		if (tokens.countTokens() != 2)
		{
			return result;
		}
		tokens.nextToken();// 跳过第一个
		String beforePartsMessage = tokens.nextToken();
		tokens = new YQTokenizer(beforePartsMessage, CHANGEORDER_PART_PART_SEPERATOR);
		while (tokens.hasMoreTokens())
		{
			YQTokenizer partTokens = new YQTokenizer(tokens.nextToken(), CHANGEORDER_PART_NUMBER_NAME_VERSION_SEPERATOR);
			System.out.println("part属性的分段数："+partTokens.countTokens());
			//避免以前无状态情况下，显示不出内容的问题
			if (partTokens.countTokens() == 4||partTokens.countTokens() == 3)
			{
				String partNumber = partTokens.nextToken();
				String partName = partTokens.nextToken();
				String partVersion = partTokens.nextToken();
				String partId = "null";
				QMPartInfo part = null;
				try
				{
					part = getPartInfoByOrigInfo(partNumber, partVersion);
					if (part != null)
					{
						result.add(part.getBsoID());
					}
				}
				catch (QMException e)
				{
					e.printStackTrace();
				}
		  }
		}
		return result;
	}
	//CCEnd SS9
	
	//CCBegin SS10
	/**
	 * 根据零部件的编号,发布源版本，定位JF结尾的零部件对象
	 * @param num String 零部件的编号
	 * @param iteration String 发布源的小版本号
	 * @throws QMException
	 * @return QMPartInfo 零部件对象
	 */
	public static QMPartInfo getJFPartInfoByOrigInfo(String num,
			String sourceVersion) throws QMException {
		QMPartInfo part = null;
		QMQuery query = new QMQuery("QMPartMaster");
		QueryCondition condition = new QueryCondition("partNumber", "=", num);
		query.addCondition(condition);
		QMPartMasterIfc master = null;
		PersistService ser = (PersistService) EJBServiceHelper
				.getService("PersistService");
		Collection collmaster = (Collection) ser.findValueInfo(query, false);
		if (collmaster == null)
		{
			return null;
		}
		Iterator iterator = collmaster.iterator();
		while (iterator.hasNext())
		{
			master = (QMPartMasterInfo) iterator.next();
			Collection coll = getAllIterationsOf(master);
			IBAHolderIfc holder;
			// 集合中对象以版本排序,所以找到第一个符合条件的即可
			for (Iterator iter = coll.iterator(); iter.hasNext();)
			{
				part = (QMPartInfo) iter.next();
				if(part.getVersionValue().equals(sourceVersion))
				{
					return part;
				}
			}
		}
		return null;
	}
	//CCEnd SS10
}
