//SS1 �鿴�ĵ������Ӷ��ĵ������Ƿ�Ϊ�յ��жϡ� liunan 2012-11-5
//SS2 �㲿�������ͬ���������part��gpart�⣬���ڻ����˱��������ͬ��������qmpartmasterbsoid��ͬ����(BSXUP)��׺�� liunan 2013-7-24
//SS3 ����Դ���ݷ��� �����ݽ���ȷ��֪ͨ�顱�ĵ�ҳ����ӹ���������������� liudongming 2013-10-21
//SS4 ����ļ������������߼� 2013-12-10
//SS5 �������ķ��㲿�����������������ͼ ������ 2014-7-8
//SS6 ���BOM���� ������ zhaoqiuying 2014-5-15 
//SS7 ����windchill����10.2��������epm�������ļ�������part�ϡ� liunan 2015-8-21
//SS8 ��������������Ҫ�������е��㲿���嵥���д������������嵥�͸�������Ƶ����Ƽ������������񵥱�����ԡ� liunan 2017-6-27
//SS9 �������񵥣�������̿����㲿����ȡ�� liunan 2017-7-12
//SS10 ������ǰ�㲿���ǽ������㲿����û�з���Դ�汾����ô���ҽ�Ű汾����λ�㲿���� liunan 2017-7-18
//SS11 ������������񵥣���ͨ��Դ�汾������ liunan 2017-9-7

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
 * �������ݵİ�����
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
 * @author ������
 * @version 1.0
 */
public class PublishHelper {
	
	//CCBegin by liunan 2008-09-03
	//�������俪������Ϊfalse�����ٽ��е������������
	//Դ�����¡�
	//public static final boolean VERBOSE = true;
	public static final boolean VERBOSE = false;
	//CCEnd by liunan 2008-09-03

	public static final String STATE_BUSY = "BUSY";

	public static final String STATE_FREE = "FREE";

	private static int publishState = 0;

	// �ĵ��������cache key--����·���� value--����ID
	private static HashMap docCfMap = new HashMap();

	// �ĵ�����Դ��չ���Զ��� key--������ value--����ID
	private static HashMap docAffixDef = new HashMap();

	private static HashMap docAffixCon = new HashMap();

	// IBAcache key--���Զ���������� value--���Զ��������
	private static HashMap ibaAttrDefMap = new HashMap();

	// �㲿��cache, key -- ��� value--�㲿��ֵ���� (���ڻ������ṹ�Ĳ���ɾ��)
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

	// ��¼����Դ�����Ϣ:IBA������֯����
	private static String attrOrgName = "";

	// ��¼����Դ�����Ϣ:IBA���Զ���(����Դurl)
	private static String attrDefName_source = "";

	private static String attrDefName_creater = "";

	private static String attrDefName_modifier = "";

	// ��¼����Դ�����Ϣ:IBA���Զ���(����Դid)
	private static String attrDefName_form = "";

	// ��¼����Դ�����Ϣ:IBA���Զ���(����Դ��汾��)
	private static String attrDefName_version = "";

	// ��¼����Դ�����Ϣ:IBA���Զ���(����ԴС�汾��)
	private static String attrDefName_date = "";

	private static String attrDefName_num = "";

	// ��¼����Դ�����Ϣ:�ĵ���չ����������
	// private String docAffixAttrConName;
	// ��¼����Դ�����Ϣ:�ĵ���չ����Լ����
	// private String docAffixAttrResName;
	// ��¼����Դ�����Ϣ:�ĵ���չ���Զ���(����url)
	// private String docAffixAttrDefName_url;
	// ��¼����Դ�����Ϣ:�ĵ���չ���Զ���(����Դid)
	// private String docAffixAttrDefName_id;
	// ��¼����Դ�����Ϣ:�ĵ���չ���Զ���(����Դ��汾��)
	private static String docAffixAttrDefName_version;

	// ��¼����Դ�����Ϣ:�ĵ���չ���Զ���(����ԴС�汾��)
	// private String docAffixAttrDefName_iteration;
	
	//CCBegin by liunan 2010-01-29 �ĳɹ��б�����
	public static String CHANGEORDER_DES_PART_SEPERATOR = "!$%*&";// ���������ʱ���ڷָ���������ԭ��˵���ͱ������ز�����ʾ�ַ���

	public static String CHANGEORDER_PART_PART_SEPERATOR = "!*%#!";// ���������ʱ���ڷָ��������ͬ�Ĳ����ı�ʾ�ַ���

	public static String CHANGEORDER_PART_NUMBER_NAME_VERSION_SEPERATOR = "!*!";// ���������ʱ�����ڱ������ĳ����ز�����ʾ�ַ����зָ���š����ƺ�ǰ��汾
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
		//Ϊ������ӿ��ء�
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("=====>PublishHelper.setStateFree,state="
				+ PublishHelper.publishState);
	}

	public static synchronized void setStateBusy() {
		PublishHelper.publishState++;
		//CCBegin by liunan 2008-09-03
		//Ϊ������ӿ��ء�
		if (PublishHelper.VERBOSE)
		//CCEnd by liunan 2008-09-03
		System.out.println("=====>PublishHelper.setStateBusy,state="
				+ PublishHelper.publishState);
	}

	public static synchronized String getPublishState() {
		//CCBegin by liunan 2008-09-03
		//Ϊ������ӿ��ء�
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
	 * ����ҵ������bsoID��Ϣ����ȡ��Ӧ��ҵ�����
	 * 
	 * @param bsoID
	 *            String ҵ������id��Ϣ
	 * @throws QMException
	 * @return BaseValueInfo ��Ӧ��ҵ�����
	 */
	public static BaseValueInfo getQMPDMObject(String bsoID) throws QMException {
		BaseValueInfo obj = null;
		PersistService ser = (PersistService) EJBServiceHelper
				.getService("PersistService");
		obj = (BaseValueInfo) ser.refreshInfo(bsoID, false);
		return obj;
	}

	/**
	 * ������������������ȡ��������ID��Ϣ
	 * 
	 * @param conName
	 *            String ����������
	 * @throws QMException
	 * @return String ��������ID��Ϣ
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
//					|| !(parentPart.getViewName().trim().equals("������ͼ"))) {
				if (parentPart == null || parentPart.getViewName() == null
						|| !(parentPart.getViewName().trim().equals("���������ͼ"))) {
				//CCEnd SS5
	    	//CCBegin by liunan 2008-09-03
	    	//Ϊ������ӿ��ء�
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartUsageLink, can't get parent��");
				return;
			}

			QMPartMasterInfo childMaster = null;
			QMPartIfc childPart = PublishHelper.getPartInfoByNumber(child);
			if (childPart == null) {
	    	//CCBegin by liunan 2008-09-03
	    	//Ϊ������ӿ��ء�
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartUsageLink, can't get child��");
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
			// ����Ĭ�ϵ�λ(ʹ�÷�ʽ)
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
	    	//Ϊ������ӿ��ء�
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartEpmLink, can't get part��");
				return;
			}
			QMPartInfo part = null;
			Collection coll = (Collection) versiobSer.allVersionsOf(partMaster);
			// ���master�µ�����С�汾�������°������У�
			// ���һ��Ԫ�ؼ�Ϊ���°汾������㲿������
			Iterator iterator = coll.iterator();
			while (iterator.hasNext()) {
				part = (QMPartInfo) iterator.next();
				//CCBegin SS5
//				if (part.getViewName() != null
//						&& part.getViewName().trim().equals("������ͼ")) {
				if (part.getViewName() != null
						&& part.getViewName().trim().equals("���������ͼ")) {
					//CCEnd SS5
					break;
				}
			}

			if (part == null) {
	    	//CCBegin by liunan 2008-09-03
	    	//Ϊ������ӿ��ء�
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartEpmLink, can't get part��");
				return;
			}

			EPMDocumentInfo doc = PublishHelper.getEPMDocInfoByNumber(epmNum);

			if (doc == null) {
	    	//CCBegin by liunan 2008-09-03
	    	//Ϊ������ӿ��ء�
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartEpmLink, can't get EPM��");
				return;
			}
			String docType = doc.getAuthoringApplication().getDisplay();
			// ����Ƿ���ڹ�����ϵ
			//CCBegin by liunan 2009-11-09 �ĳ�ͨ��EPMBuildHistory�������ҹ����Ƿ���ڡ�
			//Դ����
			//if (PublishHelper.hasBuildLinkRule(part, doc)) {
			if (PublishHelper.hasBuildHistory(part, doc)) 
			{
	    	//CCBegin by liunan 2008-09-03
	    	//Ϊ������ӿ��ء�
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartEpmLink, relation exists��");
				return;
			}

			// ����EPM�������㲿������֮��Ĺ�����ϵ
			
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
	    	//Ϊ������ӿ��ء�
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartDocLink, can't get part��");
				return;
			}
			QMPartInfo part = null;
			Collection coll = (Collection) versiobSer.allVersionsOf(partMaster);
			// ���master�µ�����С�汾�������°������У�
			// ���һ��Ԫ�ؼ�Ϊ���°汾������㲿������
			Iterator iterator = coll.iterator();
			while (iterator.hasNext()) {
				part = (QMPartInfo) iterator.next();
				//CCBegin SS5
//				if (part.getViewName() != null
//						&& part.getViewName().trim().equals("������ͼ")) {
				if (part.getViewName() != null
							&& part.getViewName().trim().equals("���������ͼ")) {
					//CCEnd SS5
					break;
				}
			}
			if (part == null) {
	    	//CCBegin by liunan 2008-09-03
	    	//Ϊ������ӿ��ء�
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartDocLink, can't get part��");
				return;
			}
			DocInfo doc = PublishHelper.getDocInfoByNumber(docNum);
			if (doc == null) {
	    	//CCBegin by liunan 2008-09-03
	    	//Ϊ������ӿ��ء�
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartDocLink, can't get doc��");
				return;
			}

			// ����Ƿ����������ϵ
			if (PublishHelper.hasPartDocDesc(part, doc)) {
	    	//CCBegin by liunan 2008-09-03
	    	//Ϊ������ӿ��ء�
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out
						.println("=====>PublishHelper.createPartDocLink, relation exists��");
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
	 * ������������ģ��������ȡ��Ӧ����������ģ��
	 * 
	 * @param lifeName
	 *            String ��������ģ����
	 * @throws QMException
	 * @return LifeCycleTemplateInfo ��Ӧ����������ģ��
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
	 * ������������ģ��������ȡ��Ӧ����������ģ��ID
	 * 
	 * @param lifeName
	 *            String ��������ģ����
	 * @throws QMException
	 * @return String ��Ӧ����������ģ��ID
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
	 * �������ϼ�
	 * 
	 * @param info
	 *            FolderEntryIfc ���������
	 * @param location
	 *            String ����·��
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
	 * �����㲿������ı�ţ���ȡ��Ӧ�㲿����ID��Ϣ
	 * 
	 * @param partNumber
	 *            String �㲿������ı��
	 * @throws QMException
	 * @return String ��Ӧ�㲿����ID��Ϣ
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
	 * �����㲿������ı�ţ���ȡ��Ӧ�㲿��
	 * 
	 * @param number
	 *            String �㲿������ı��
	 * @throws QMException
	 * @return QMPartInfo ��Ӧ�㲿��
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
	 * �����㲿������ı�ţ���ȡ��Ӧ�㲿��
	 * 
	 * @param number
	 *            String �㲿������ı��
	 * @throws QMException
	 * @return QMPartInfo ��Ӧ�㲿��
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
	 * �����ĵ�����ı�ţ���ȡ��Ӧ���ĵ�����ID��Ϣ
	 * 
	 * @param docNumber
	 *            String �ĵ�����ı��
	 * @throws QMException
	 * @return String ��Ӧ���ĵ�����ID��Ϣ
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
	 * �����ĵ�����ı�ţ���ȡ��Ӧ���ĵ�����
	 * 
	 * @param docNumber
	 *            String �ĵ�����ı��
	 * @throws QMException
	 * @return DocInfo ��Ӧ���ĵ�����
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
	 * ����Master���󣬻�ȡ�����������°汾����
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
		// ���master�µ�����С�汾�������°������У�
		// ���һ��Ԫ�ؼ�Ϊ���°汾������㲿������
		Iterator iterator = coll.iterator();
		if (iterator.hasNext()) {
			iteratedIfc = (IteratedIfc) iterator.next();
		}
		return iteratedIfc;

	}

	/**
	 * ����Master���õ������������е�С�汾��������ͬ��֦����Ҳ����master��Ӧ������С�汾���� <BR>
	 * ���汾���µ����ź���Ľ�� ��c.2,c.1,B.3,B,2,B.1,A.9,A.8,A.7...A,1
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
	 * �����㲿���ı�ţ���ȡ��Ӧ���㲿��������
	 * 
	 * @param partNumber
	 *            String �㲿���ı��
	 * @throws QMException
	 * @return QMPartMasterInfo ��Ӧ���㲿��������
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
		//CCBegin by liunan 2010-05-04 �������2��ͬ��ŵ�����Ϣ��˵�������ŵ�part��gpart���С�
		//��ʱֻȡgpart��Ŀǰ���ݷ�����Ϊ����ͬ��ŵ�part��gpart������ʱ���ٷ�����һ����gpart��
		//Դ����
		/*Iterator iterator = coll.iterator();
		if (iterator.hasNext()) {
			master = ((QMPartMasterIfc) iterator.next());
		}*/
		
		//CCBegin SS1
		//if(coll.size()==2)
		if(coll.size()>=2)
		{
			//System.out.println("in getPartMasterByNumber ������ͬ��ŵ�part��gpart������");
			//System.out.println("in getPartMasterByNumber ������ͬ��ŵ�part��gpart �� ������part������");
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
			//System.out.println("��ʱ�õ����㲿��Ϊ�� "+master);
			//CCEnd SS1
			//CCBegin by liunan 2010-05-20 ���ϵͳ�д�����ͬ��ŵĶ��partmaster���
			//����masterΪnullʱ��ʾ�������������ʱ����ǰ����ȡ��һ��������ܴ�ϵͳ�б�ֻ֤��һ��
			//partmaster���������������롣
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
	 * �����㲿���ı�ţ���ȡ��Ӧ���㲿��������
	 * 
	 * @param partNumber
	 *            String �㲿���ı��
	 * @throws QMException
	 * @return QMPartMasterInfo ��Ӧ���㲿��������
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
	 * �����ĵ���ţ���ȡ��Ӧ�ĵ������������
	 * 
	 * @param docNumber
	 *            String �ĵ����
	 * @throws QMException
	 * @return DocMasterInfo ��Ӧ�ĵ������������
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
	 * �����ĵ�����������ȡ��Ӧ���ĵ�����ID
	 * 
	 * @param docCfName
	 *            String �ĵ�������
	 * @throws QMException
	 * @return String ��Ӧ���ĵ�����ID
	 */
	public static String getDocCf(String docCfName) throws QMException {
		String docCfBsoID = (String) docCfMap.get(docCfName);
		if (docCfBsoID == null) {

			// �ֽ⸸�����·����Ϣ
			String olddocCfName = docCfName;
			DocCfComponents docCfCom = new DocCfComponents(docCfName);
			int pathLen = docCfName.length();
			int lastIndex = docCfName.lastIndexOf("\\");
			if (lastIndex == pathLen - 1) {
				docCfName = docCfName.substring(0, pathLen - 1);
			}
			String docCfNameTail = docCfCom.getTail();
			if (docCfNameTail == null || docCfNameTail.trim().length() == 0) {
				String log2 = " Doc:" + olddocCfName + "  �ĵ����� \"" + docCfName
						+ "\" ��Ϣ����ȷ��";

				throw new QMException("Error �ĵ�����:" + olddocCfName);
			}

			// ���ݷ�������������(���ܶ��ֵ)
			QMQuery query = new QMQuery("DocClassification");
			QueryCondition qc = new QueryCondition("docCfName", "=",
					docCfNameTail);
			query.addCondition(qc);
			PersistService ser = (PersistService) EJBServiceHelper
					.getService("PersistService");
			Collection coll = (Collection) ser.findValueInfo(query, false);

			// ���û���ҵ�ƥ���¼������false
			if (coll == null || coll.size() == 0) {
				String log2 = " Doc:" + olddocCfName + " Be:�ĵ����� \""
						+ docCfName + "\" �����ڡ�";

				throw new QMException("Error �ĵ�����:" + olddocCfName + " ������");
			}

			boolean findFlag = false;
			DocClassificationInfo tempDocCf = null;
			// ���ֻ��һ����¼�򷵻�true,��練游�����ֵ����
			if (coll.size() == 1) {
				Iterator iter = coll.iterator();
				tempDocCf = (DocClassificationInfo) iter.next();
				docCfBsoID = tempDocCf.getBsoID();
				findFlag = true;
			} else {
				// ��������ͬ�����࣬�ҳ�·����ȫƥ��ķ���,����true,��練�����ֵ����
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
				String log2 = " Doc:" + olddocCfName + " Be:�ĵ����� \""
						+ docCfName + "\" �����ڡ�";

				throw new QMException("Error �ĵ�����:" + olddocCfName + " ������");
			}
			docCfMap.put(olddocCfName, docCfBsoID);
			return docCfBsoID;
		} else {
			return docCfBsoID;
		}
	}

	/**
	 * ������ͼ������ȡ��Ӧ����ͼ����
	 * 
	 * @param viewName
	 *            String ��ͼ��
	 * @throws QMException
	 * @return ViewObjectInfo ��Ӧ����ͼ����
	 */
	public static ViewObjectInfo getView(String viewName) throws QMException {
		ViewService vs = (ViewService) EJBServiceHelper.getService(viewName);
		ViewObjectInfo v = vs.getView(viewName);
		return v;
	}

	/**
	 * �ж��Ƿ����ָ������ͼ����
	 * 
	 * @param viewName
	 *            String ��ͼ��
	 * @throws QMException
	 * @return boolean �Ƿ����ָ������ͼ����
	 */
	public static boolean isHasView(String viewName) throws QMException {
		ViewObjectInfo v = getView("ViewService");
		return v != null;
	}

	/**
	 * �ж��Ƿ����ָ�����㲿�����
	 * 
	 * @param partNum
	 *            String �㲿�����
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
	 * �ж��Ƿ����ָ�����ĵ����
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
	 * �ж��Ƿ����ָ�����㲿���ṹ��ϵ
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
	 * �ж��Ƿ����ָ�����㲿���ṹ��ϵ
	 * 
	 * @param parentId
	 *            String ��ID
	 * @param childId
	 *            String �Ӽ�ID
	 * @param quantity
	 *            float ʹ������
	 * @param unit
	 *            String ʹ�õ�λ
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
	 * ��ȡָ���㲿���Ľṹ������
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
	 * ��ȡһ���㲿���ĸ�������(QMPartInfo)
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
	 * ��ȡָ���㲿�����Ӽ��ṹ�����༯�ϣ�PartUsageLinkInfo��
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
	 * ��ȡһ��ҵ�����Ķ�����ʾ��ʶ
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
	 * ɾ��ָ�����㲿��,���ָ��bom����bom�ṹɾ���㲿��
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
	 * ɾ��ָ�����㲿��,���ָ��bom����bom�ṹɾ���㲿��
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
			// �����Ӽ�
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
			// ɾ������
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
				System.out.println("��ɾ���㲿��:" + parentPart.getPartNumber()
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
						System.out.println("��ɾ�������ĵ�:" + doc.getDocNum());
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
						System.out.println("��ɾ��EPM�ĵ�:" + epmDoc.getDocNumber());
				}
			}
			if (PublishHelper.VERBOSE)
				System.out.println("===========>" + childrenColl.size());
			// ɾ���Ӽ�
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
	 * ɾ��ָ�����㲿��,���ָ��bom����bom�ṹɾ���㲿��
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
		// �����Ӽ�
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
		// ɾ������
		PersistService ser = (PersistService) getEJBService("PersistService");
		ser.deleteValueInfo(parentPart);
		if (PublishHelper.VERBOSE)
			System.out.println("Delete Part [" + getIdentity(parentPart)
					+ "]  OK!");
		// ɾ���Ӽ�
		if (bom) {
			for (Enumeration enumer = childrenColl.elements(); enumer
					.hasMoreElements();) {
				deleteParts((QMPartInfo) enumer.nextElement(), bom);
			}
		}
	}

	/**
	 * ���ݲ�����Ϣɾ��ָ�����㲿�������bom����Ϊtrue�����bom�ṹɾ���㲿��
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
	    //Ϊ������ӿ��ء�
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
			// ���,Ҳ��ֹ��cache�����ã�������ݲ�׼ȷ(���cache�����������Ʒ������)
			partMap.clear();
		}
	}

	/**
	 * �����㲿���ı�š��汾�Ų��Ҷ�Ӧ���㲿��
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
			// ʹ��cache
			part = getCachedPart(num);
			if (part == null) {
				// ���ֻ�б��
				if (version == null) {
					part = getPartInfoByNumber(num);
					if (part != null) {
						partMap.put(part.getPartNumber().toUpperCase(), part);
					}
					return part;
				}

				// �����������ϲ�ѯ����
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
					 * //���ֻ�д�汾��,����Ҵ�汾�Ŷ�Ӧ�����°汾 if(iteration == null) {
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
			} // cache��û��
		} catch (QMException wte) {
			wte.printStackTrace();
		} catch (Exception wte) {
			wte.printStackTrace();
		}
		return part;

	}

	/**
	 * ��cache�в����㲿��
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
	 * �����㲿���ı�š��汾�Ų��Ҷ�Ӧ���㲿��
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
			// ���ֻ�б��
			if (version == null) {
				part = getPartInfoByNumber(num);
				if (part != null) {
					partMap.put(part.getPartNumber().toUpperCase(), part);
				}
				return part;
			}

			// �����������ϲ�ѯ����
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
				 * //���ֻ�д�汾��,����Ҵ�汾�Ŷ�Ӧ�����°汾 if(iteration == null) {
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
	 * �ж��Ƿ����ָ�����㲿�����ĵ��Ĳο���ϵ
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
	 * �ж��Ƿ����ָ�����㲿�����ĵ��Ĳο���ϵ
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
	 * �ж�ָ�����㲿�����ĵ��Ƿ��������������ϵ
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
	 * �ж�ָ�����㲿�����ĵ��Ƿ��������������ϵ
	 * 
	 * @param partID
	 *            String �㲿��ID
	 * @param docID
	 *            String �ĵ�ID
	 * @return boolean �Ƿ��������������ϵ
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
	 * �ж��Ƿ����ָ����EPM�ĵ����
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
	 * �ж�ָ�����㲿����EPM�ĵ��Ƿ���ڹ�����ϵ
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
	 * �ж�ָ�����㲿����EPM�ĵ��Ƿ���ڹ�����ϵ��
	 * ��Ϊ��������׼�ŷ��������Ҵ�����׼���㲿������С�汾������
	 * ���Ѵ���ϵͳ����EPM�ĵ��ֲ�����׼���㲿����
	 * ������Ҫ�ж�С�汾�������������Ƿ����㲿����EPM������ϵ��
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
	 * �ж�ָ����EPM�ĵ��Ƿ���ڽṹ������ϵ
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
	 * �ж�ָ����EPM�ĵ��Ƿ���ڲο�������ϵ
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
	 * ��ȡָ����EPM�ĵ��Ľṹ�����������������ڹ�����ϵ���򷵻�null
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
	 * ��ȡָ����EPM�ĵ��Ĳο������������������ڹ�����ϵ���򷵻�null
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
	 * ������㲿��������epm�ĵ�
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
	 * �����epm�ĵ��������㲿��
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
	 * ����EPM�ĵ����,��ȡEPM�ĵ�(���°汾)
	 * 
	 * @param num
	 *            String EPM�ĵ����
	 * @throws QMException
	 * @return EPMDocumentInfo EPM�ĵ�
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
	 * ����EPM�ĵ����,��ȡEPM�ĵ�������
	 * 
	 * @param num
	 *            String EPM�ĵ����
	 * @throws QMException
	 * @return EPMDocumentMasterIfc EPM�ĵ�������
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
	 * ɾ��ָ����EPM�ĵ�����
	 * 
	 * @param num
	 *            String EPM�ĵ����
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
	 * ����EPM�ĵ��ı��,����Դ��汾��,����Դ��С�汾�Ŷ�λEPM�ĵ�����
	 * 
	 * @param num
	 *            String EPM�ĵ��ı��
	 * @param version
	 *            String ����Դ��汾��
	 * @param iteration
	 *            String ����Դ��С�汾��
	 * @throws QMException
	 * @return EPMDocumentInfo ��Ӧ��EPM�ĵ�����
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
		// �����ж����԰汾����,�����ҵ���һ�����������ļ���
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
	 * �����ĵ�ID�ж��ĵ��Ƿ��ǡ��������ı���������ĵ�
	 * 
	 * @param docId
	 *            �ĵ�BsoID
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
	 * �����ĵ�ֵ�����ж��Ƿ��ǡ��������ı���������ĵ�
	 * 
	 * @param doc
	 *            �ĵ�ֵ����
	 * @return
	 */
	public static String isChangeOrder(DocIfc doc) {
		if (doc == null) {
			return "false";
		}
	  //CCBegin by liunan 2008-09-03
	  //Ϊ������ӿ��ء�
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("====" + doc.getDocCfName());
		//CCBegin SS1
		if (doc.getDocCfName()!=null&&doc.getDocCfName().equals("�������ı����")) {
		//CCEnd SS1
			return "true";
		} else {
			return "false";
		}
	}

	/**
	 * ��ȡ"�������ı����"���ĵ��ڼ�������ϵͳ�е�������Ϣ.
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
	 * ��ȡ"�������ı����"���ĵ��ڼ�������ϵͳ�е�������Ϣ.
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
	 * ����"�������ı����"���ĵ�id��ȡ���ǰ���㲿��
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
	 * ����"�������ı����"���ĵ���ȡ���ǰ���㲿��
	 * 
	 * @param changeOrder
	 *            "�������ı����"���ĵ�
	 * @return �ַ������鼯��,ÿ���ַ��������¼��һ���㲿����id����š����ơ��������İ汾
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
	 * ͨ���ĵ������ַ�����ȡ���ǰ���㲿��
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
		tokens.nextToken();// ������һ��
		String beforePartsMessage = tokens.nextToken();
		tokens = new YQTokenizer(beforePartsMessage,
				CHANGEORDER_PART_PART_SEPERATOR);
		while (tokens.hasMoreTokens()) {
			YQTokenizer partTokens = new YQTokenizer(tokens.nextToken(),
					CHANGEORDER_PART_NUMBER_NAME_VERSION_SEPERATOR);
			//ccbegin by liunan 2008-06-12
			//������ǰ��״̬����£���ʾ�������ݵ�����
			if (partTokens.countTokens() == 4||partTokens.countTokens() == 3) {
				//continue;
			//20080410��������������״̬
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
	 * ����"�������ı����"���ĵ�id��ȡ�������㲿��
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
	 * ����"�������ı����"���ĵ���ȡ�������㲿��
	 * 
	 * @param changeOrder
	 *            "�������ı����"���ĵ�
	 * @return �ַ������鼯��,ÿ���ַ��������¼��һ���㲿����id����š����ơ��������İ汾
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
			tokens.nextToken();// ������һ��
			tokens.nextToken();// �����ڶ���
			String beforePartsMessage = tokens.nextToken();
			tokens = new YQTokenizer(beforePartsMessage,
					CHANGEORDER_PART_PART_SEPERATOR);
			while (tokens.hasMoreTokens()) {
				YQTokenizer partTokens = new YQTokenizer(tokens.nextToken(),
						CHANGEORDER_PART_NUMBER_NAME_VERSION_SEPERATOR);
			//ccbegin by liunan 2008-06-12
			//������ǰ��״̬����£���ʾ�������ݵ�����
			if (partTokens.countTokens() == 4||partTokens.countTokens() == 3) {
				//continue;
				//20080410��������������״̬
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
                        if(define.equals("Ӱ�컥��"))
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
	   * �����������Գ����ߵ�id,�����iba����ֵ�ȡ�
	   * @param ibaHolderBsoID String
	   * @return Vector ���е�Ԫ��Ϊ��ά�ַ�������,��һά��Ÿ�iba����ֵ�����Զ��������,
	   * �ڶ�ά���iba���Ե�ֵ��
	   */
	  public static Vector getIBANameAndValue(String ibaHolderBsoID)
	  {
	    IBAHolderIfc ibaHolderIfc = null;
	    try {
	      PersistService pService = (PersistService) EJBServiceHelper.getService(
	          "PersistService");
	       ibaHolderIfc = (IBAHolderIfc) pService.refreshInfo(ibaHolderBsoID);
	       IBAValueService ibaService = (IBAValueService)EJBServiceHelper.getService("IBAValueService");
	       //CCBegin by liunan 2009-01-14 ����epm���������Ե������棬���ӹ���part�л�ȡ��
	       //��������:
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
	          //�޸�4 20081028 zhangq begin �޸�ԭ���㲿���ݿͻ��鿴�������λ�ĸ���������������ʾ��λΪ���Զ�������ٶ���Ĭ�ϵ�λ
	          String unitStr = "";
	          if(value instanceof UnitValueDefaultView)
	          {
	              UnitDefView definition1 = ((UnitValueDefaultView)value).getUnitDefinition();
	              QuantityOfMeasureDefaultView quantityofmeasuredefaultview = definition1.getQuantityOfMeasureDefaultView();
	              if (measurementSystem != null)
	              {
	                  //�õ����Զ���ĵ�λ
	                  unitStr = definition1.getDisplayUnitString(
	                          measurementSystem);
	                  //�õ�������λ�е���ʾ��λ
	                  if (unitStr == null)
	                  {
	                      unitStr = quantityofmeasuredefaultview.
	                           getDisplayUnitString(measurementSystem);
	                  }
	                  //�õ�������λ�е�����
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
	              //����ֵ�͵�λ�м��ÿո�ֿ�
	              nameAndValue[1] = ss+"  "+unitStr;
	          }
	          //�޸�4 20081028 zhangq end
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
	 * ����"�������ı����"���ĵ�id��ȡ����з���ȡ�����㲿��
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
	 * ����"�������ı����"���ĵ���ȡ����з���ȡ�����㲿��
	 * 
	 * @param changeOrder "�������ı����"���ĵ�
	 *            
	 * @return �ַ������鼯��,ÿ���ַ��������¼��һ���㲿����id����š����ơ��������İ汾
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
	 * ͨ���ĵ������ַ�����ȡ����з���ȡ�����㲿��
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
		tokens.nextToken();// ������һ��
		String beforePartsMessage = tokens.nextToken();
		tokens = new YQTokenizer(beforePartsMessage,
				CHANGEORDER_PART_PART_SEPERATOR);
		while (tokens.hasMoreTokens()) {
			YQTokenizer partTokens = new YQTokenizer(tokens.nextToken(),
					CHANGEORDER_PART_NUMBER_NAME_VERSION_SEPERATOR);
			//ccbegin by liunan 2008-06-12
			//������ǰ��״̬����£���ʾ�������ݵ�����
			if (partTokens.countTokens() == 4||partTokens.countTokens() == 3) {
				//continue;
			//20080410��������������״̬
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
	 * �����㲿���ı��,����Դ��汾��,����Դ��С�汾�Ŷ�λ�㲿������
	 * 
	 * @param num
	 *            String �㲿���ı��
	 * @param version
	 *            String ����Դ�Ĵ�汾��
	 * @param iteration
	 *            String ����Դ��С�汾��
	 * @throws QMException
	 * @return QMPartInfo �㲿������
	 */
	public static QMPartInfo getPartInfoByOrigInfo(String num,
			String sourceVersion) throws QMException {
		QMPartInfo part = null;
		//CCBegin by liunan 2010-05-05 ���ڴ���ͬ��ŵ�part��gpart��˶�Ҫѭ�����ˡ�
		//Դ����
		/*QMPartMasterIfc master = getPartMasterByNumber(num);
		if (master == null) {
			return null;
		}
		Collection coll = getAllIterationsOf(master);
		IBAHolderIfc holder;
		// �����ж����԰汾����,�����ҵ���һ�����������ļ���
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			part = (QMPartInfo) iter.next();
			holder = (IBAHolderIfc) part;
			if (hasIBASourceValue(holder, sourceVersion)) {
				//CCBegin by liunan 2008-12-19 ��ø�����ʱ���ж���ͼ
				if(part.getViewName().trim().equals("������ͼ"))
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
			// �����ж����԰汾����,�����ҵ���һ�����������ļ���
			for (Iterator iter = coll.iterator(); iter.hasNext();)
			{
				part = (QMPartInfo) iter.next();
				holder = (IBAHolderIfc) part;
				if (hasIBASourceValue(holder, sourceVersion))
				{
					//CCBegin by liunan 2008-12-19 ��ø�����ʱ���ж���ͼ
					//CCBegin SS5
					//if(part.getViewName().trim().equals("������ͼ"))
					if(part.getViewName().trim().equals("���������ͼ"))
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
	 * �����㲿���ı��,����Դ��汾��,����Դ��С�汾�Ŷ�λ�㲿������
	 * 
	 * @param num
	 *            String �㲿���ı��
	 * @param version
	 *            String ����Դ�Ĵ�汾��
	 * @param iteration
	 *            String ����Դ��С�汾��
	 * @throws QMException
	 * @return QMPartInfo �㲿������
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
		// �����ж����԰汾����,�����ҵ���һ�����������ļ���
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
	 * �����ĵ�����ı�ţ���ȡ��Ӧ���ĵ�����
	 * 
	 * @param docNumber
	 *            String �ĵ�����ı��
	 * @throws QMException
	 * @return DocInfo ��Ӧ���ĵ�����
	 */
	public static DocInfo getDocInfoByOrigInfo(String num, String sourceVersion)
			throws QMException {
		DocInfo doc = null;
		DocMasterInfo master = getDocMasterByNumber(num);
		if (master == null) {
			return null;
		}
		// ��ȡָ��ȫ��ѡ��С�汾
		Collection coll = getAllIterationsOf(master);
		// �����ж����԰汾����,�����ҵ���һ�����������ļ���
		for (Iterator iter = coll.iterator(); iter.hasNext();) {
			doc = (DocInfo) iter.next();
			if (hasAffixattr(doc, sourceVersion)) {
				return doc;
			}
			//CCBegin SS11
			if(doc.getLocation().indexOf("��������")!=-1)
			{
				return doc;
			}
			//CCEnd SS11
		}

		return null;
	}

	/**
	 * �ж�ָ�����ĵ��������չ�������Ƿ���ָ������Դ��Ϣ(��汾�š�С�汾��)
	 * 
	 * @param doc
	 *            DocInfo �ĵ�����
	 * @param sourceVersion
	 *            String ����Դ�Ĵ�汾��
	 * @param sourceIteration
	 *            String ����Դ��С�汾��
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
	 * ������չ��������ȡ���Զ���ID
	 * 
	 * @param defName
	 *            String ��չ������
	 * @return String ���Զ���ID
	 */
	public static String getAffixAttrDefIDByName(String defName) {
		try {
			// �ȼ��һ���ĵ�����չ���������Ƿ�����
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
	 * ������չ��������ȡ���Զ���
	 * 
	 * @param defName
	 *            String ��չ������
	 * @return AttrDefineInfo ��չ���Զ���
	 */
	public static AttrDefineInfo getAffixAttrDefByName(String defName)
			throws QMException {
		// ��cPDMϵͳ�У���չ��������Ψһ��
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
	 * ������չ��������������ȡ��Ӧ����������
	 * 
	 * @param contName
	 *            String ��չ����������
	 * @throws QMException
	 * @return AttrContainerInfo ��Ӧ����������
	 */
	public static AttrContainerInfo getAffixAttrContByName(String contName)
			throws QMException {
		if (contName == null) {
			return null;
		}
		// ��÷���
		AffixAttrService as = (AffixAttrService) EJBServiceHelper
				.getService("AffixAttrService");
		// �������������������������
		return as.findContainerByName(contName);

	} // end

	/**
	 * ������չ����Լ������ȡ��չ����Լ������
	 * 
	 * @param resName
	 *            String ��չ����Լ����
	 * @throws QMException
	 * @return AttrRestictInfo ��չ����Լ������
	 */
	public static AttrRestictInfo getAffixAttrResByName(String resName)
			throws QMException {
		if (resName == null) {
			return null;
		}
		// ��cPDMϵͳ�У���չ����Լ������Ψһ��
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
	 * �����������ƻ�ȡ����ID��
	 * 
	 * @param String
	 *            contName ��������;
	 * @return String ����ID��
	 * @exception com.faw_qm.framework.exceptions.QMException
	 */
	public static String getAffixAttrContIDByName(String contName)
			throws QMException {
		String contID = (String) docAffixCon.get(contName);
		if (contID == null) {
			AttrContainerInfo contInfo = getAffixAttrContByName(contName);
			// ��ȡ����ID
			if (contInfo != null) {
				contID = contInfo.getBsoID();
				docAffixCon.put(contName, contID);
			}
		}
		return contID;
	} // end

	/**
	 * ��IBA�����в��ҷ���Դ��Ϣ,�ж��Ƿ���ָ���İ汾��Ϣ
	 * 
	 * @param holder
	 *            IBAHolderIfc
	 * @param sourceVersion
	 *            String ����Դ�Ĵ�汾��
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
	 * ��ȡָ������ļ������
	 * 
	 * @param workable
	 *            WorkableIfc �Ѽ���Ķ���
	 * @throws QMException
	 * @return WorkableIfc �Ѽ���Ķ���Ĺ�������
	 */
	public static WorkableIfc workingCopyOf(WorkableIfc workable)
			throws QMException {
		WorkInProgressService ws = (WorkInProgressService) EJBServiceHelper
				.getService("WorkInProgressService");
		return ws.workingCopyOf(workable);
	}

	/**
	 * ����������ö���û�м̳�Workable�ӿڣ���ղ���
	 * 
	 * @param work
	 *            WorkableIfc Ҫ����Ķ���
	 * @throws QMException
	 */
	public static void checkOut(WorkableIfc work, String desc)
			throws QMException {
		WorkInProgressService ws = (WorkInProgressService) EJBServiceHelper
				.getService("WorkInProgressService");
		ws.checkout(work, ws.getCheckoutFolder(), desc);
	}

	/**
	 * �������
	 * 
	 * @param work
	 *            WorkableIfc Ҫ����Ķ���
	 * @param desc
	 *            String ����������Ϣ
	 * @throws QMException
	 */
	public static void checkIn(WorkableIfc work, String desc)
			throws QMException {
		WorkInProgressService ws = (WorkInProgressService) EJBServiceHelper
				.getService("WorkInProgressService");
		ws.checkin(work, desc);
	}

	/**
	 * �������
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
	 * �ж��������Ƿ��ڸ������ϼ���
	 * 
	 * @param folderEntry
	 *            FolderEntryIfc ������
	 * @throws QMException
	 * @return boolean �Ƿ��ڸ������ϼ���
	 */
	public static boolean inPersonalFolder(FolderEntryIfc folderEntry)
			throws QMException {
		if (PersistHelper.isPersistent(folderEntry)) {
			FolderService fs = (FolderService) EJBServiceHelper
					.getService("FolderService");
			return fs.inPersonalFolder(folderEntry);

		} else {
			FolderIfc personalFodler = getPersonalFolder(getCurUserInfo());
			// ��ǰ�û��ĸ������ϼ� ·��
			String path = personalFodler.getPath();
			;
			return path.equals(folderEntry.getLocation());
		}
	}

	/**
	 * ��ȡָ���û��ĸ������ϼ�
	 * 
	 * @param user
	 *            UserIfc ָ�����û�����
	 * @throws QMException
	 * @return FolderIfc �������ϼ�
	 */
	public static FolderIfc getPersonalFolder(UserIfc user) throws QMException {
		FolderService fs = (FolderService) EJBServiceHelper
				.getService("FolderService");
		return fs.getPersonalFolder((UserInfo) user);
	}

	/**
	 * ��������Ϣ,��ʼIBA����
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
		// ��ȡIBA����
		IBADefinitionService ds = null;
		try {
			ds = (IBADefinitionService) getEJBService("IBADefinitionService");
		} catch (Exception ex) {
			ex.printStackTrace();
			return hashtable;
		}
		// ����������
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
			//debugInfo("���յ�IBA����� num��" + partNum + " ibaDef=" + ibaDef
					//+ " ibaType=" + ibaType + " ibaValue=" + ibaValue);
			try {
				// �÷��񷽷���cache����
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
				// ����ҵ��˶�Ӧ��IBA����
				if (defaultView != null) {
					if (ibaType.equals("BooleanValue")) {
						// log("11111111 IBA����ֵ�ǣ���"+ibaValue);
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
					  	System.out.println("��ʼIBA����ʱ�����쳣��\r\n" + "����IBA������(" + ibaDef
						+ ")�ҵ���Ӧ�Ķ���������Ͳ�һ��.");
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
							errorLog("�Ҳ�������ṹ" + structurename);
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
	      	//Ϊ������ӿ��ء�
	      	//if (PublishHelper.VERBOSE)
	      	//CCEnd by liunan 2008-09-03
					System.out.println("����IBA������(" + ibaDef
							+ ")�Ҳ�����Ӧ�Ķ������,����������.");
				}
			} catch (ClassCastException e) {
	    	//CCBegin by liunan 2008-09-03
	    	//Ϊ������ӿ��ء�
	    	if (PublishHelper.VERBOSE)
	    	//CCEnd by liunan 2008-09-03
				System.out.println("��ʼIBA����ʱ�����쳣��\r\n" + "����IBA������(" + ibaDef
						+ ")�ҵ���Ӧ�Ķ���������Ͳ�һ��.");
				errorLog("��ʼIBA����ʱ�����쳣��" + "����IBA������(" + ibaDef
						+ ")�ҵ���Ӧ�Ķ���������Ͳ�һ��.");
				errorLog(e);
				e.printStackTrace();
			} catch (Exception ex) {
				errorLog("��ʼIBA����ʱ�����쳣��");
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
	 * ��IBA����������,���÷���Դ��Ϣ
	 * 
	 * @param holder
	 *            IBAHolderIfc �������Գ�����
	 * @param props
	 *            Properties ����Դ��Ϣ
	 * @return IBAHolderIfc ���ú���������Գ�����
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
		// ���ʵ�������part��ȡ���Ĵ�汾ֵ
		String sourceVersion = props.getProperty("sourceVersion", null);
		String noteNum = props.getProperty("noteNum", null);
		String creater = props.getProperty("creater", null);
		String modifier = props.getProperty("modifier", null);
		// String urlDesc = "�鿴����Դ����";
		String attrOrgDesc = "���ݷ���������֯";
		String attrDef_dataSource_Desc = "������Դ";
		String attrDef_form_Desc = "������ʽ";
		String attrDef_version_Desc = "����Դ�汾";
		String attrDef_date_Desc = "����ʱ��";
		String attrDef_num_Desc = "�������";
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
			// ����Ƿ����ָ���ķ���Դ������֯�����Զ���,���û������
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
			// ���ָ����������֯�������򴴽�������֯�����Զ���
			if (attrOrg == null) {
				String msg = "ָ����������֯������";
				errorLog(msg);
			}

			// ������Դ
			if (sourceDefView == null) {
				sourceDefView = (StringDefView) ibaAttrDefMap
						.get(attrDefName_source);
			}
			if (sourceDefView == null) {
				sourceDefView = (StringDefView) ds
						.getAttributeDefDefaultViewByPath(attrDefName_source);
			}
			if (sourceDefView == null) {
				errorLog("������Դ�����Զ��岻����");
			} else {
				ibaAttrDefMap.put(attrDefName_source, sourceDefView);

				// ������ʽ
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
				errorLog("�����ߵ����Զ��岻����");
			} else {
				ibaAttrDefMap.put(attrDefName_creater, createrView);
				// ������ʽ
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
				errorLog("�����ߵ����Զ��岻����");
			} else {
				ibaAttrDefMap.put(attrDefName_modifier, modifierView);
				// ������ʽ
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
				errorLog("������ʽ�����Զ��岻����");
			} else {
				ibaAttrDefMap.put(attrDefName_form, formDefView);

				// ����Դ�汾��
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
				errorLog("�����汾�����Զ��岻����");
			} else {
				ibaAttrDefMap.put(attrDefName_version, versionDefView);
				// ����ʱ��
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
				errorLog("����ʱ������Զ��岻����");
			} else {
				ibaAttrDefMap.put(attrDefName_date, dateDefView);
				// �������
			}
			if (numDefView == null) {
				numDefView = (StringDefView) ibaAttrDefMap.get(attrDefName_num);
			}
			if (numDefView == null) {
				numDefView = (StringDefView) ds
						.getAttributeDefDefaultViewByPath(attrDefName_num);
			}
			if (numDefView == null) {
				errorLog("������ŵ����Զ��岻����");
			} else {
				ibaAttrDefMap.put(attrDefName_num, numDefView);

				// ���÷���Դ�����Ϣ
			}
			// ��������ֵ
			if (container == null) {
				container = new DefaultAttributeContainer();
			}
			StringValueDefaultView strValue = null;
			if (publishForm != null && !publishForm.equals("")) {
				strValue = new StringValueDefaultView(formDefView, publishForm);
				container.addAttributeValue(strValue);
			}
			if (holder instanceof QMPartIfc)
			System.out.println("anantt revise   �㲿�� "+((QMPartIfc)holder).getPartNumber()+"  sourceVersion==="+sourceVersion);//anan
			if (sourceVersion != null && !sourceVersion.equals("")) {
				strValue = new StringValueDefaultView(versionDefView,
						sourceVersion);
				container.addAttributeValue(strValue);
				if (holder instanceof QMPartIfc)
				System.out.println("anantt revise   �㲿�� "+((QMPartIfc)holder).getPartNumber()+"  strValue==="+strValue);//anan
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
			errorLog("���÷���Դ��Ϣʧ��!");
			ex.printStackTrace();
			errorLog(ex);
		}

		if (holder instanceof QMPartIfc) {
			try {
				AttributeOrgNodeView attrorg1 = null;
				IBADefinitionService ds1 = (IBADefinitionService) getEJBService("IBADefinitionService");
				AttributeOrgNodeView[] orgss = ds1.getAttributeOrganizerRoots();
				for (int i = 0; i < orgss.length; i++) {
					if ((orgss[i].getName()).equals("��������")) {
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
				 * StringValueDefaultView( datasourceview, "����ͼ");
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
	 * ΪIBAHolder��������IBA����ֵ(���ӷ�ʽ)
	 * 
	 * @param holder
	 *            IBAHolderIfc IBAHoler����
	 * @param ibaValues
	 *            Vector ����ֵ��AbstractContextualValueDefaultView���ϣ�
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
				log("   IBAֵΪ��...,����.");
				return holder;
			}
			for (Iterator ite = ibaValues.iterator(); ite.hasNext();) {
				AbstractValueView valueView = (AbstractValueView) ite.next();
				// debugInfo("����������� ������" +
				// valueView.getLocalizedDisplayString());
				attrCont.setConstraintParameter("CSM");
				attrCont.addAttributeValue(valueView);

			}
		} catch (Exception ex) {
			errorLog("����IBAֵʧ��!");
			errorLog(ex);
		}
		return holder;
	}

	/**
	 * ��ȡ��ǰ�û�����
	 * 
	 * @throws QMException
	 * @return UserIfc ��ǰ�û�����
	 */
	public static UserIfc getCurUserInfo() throws QMException {
		SessionService ss = (SessionService) getEJBService("SessionService");
		return ss.getCurUserInfo();
	}

	/**
	 * ��ȡEJB����
	 * 
	 * @param serName
	 *            String EJB������
	 * @throws QMException
	 * @return BaseService EJB����
	 */
	public static BaseService getEJBService(String serName) throws QMException {
		BaseService ser = EJBServiceHelper.getService(serName);
		if (ser == null) {
			throw new QMException("�Ҳ���EJB����(" + serName + ")");
		}
		return ser;
	}

	public static BaseService getEJBService(String serName, String userName,
			String password) throws QMException {
		BaseService ser = EJBServiceHelper.getService(serName, userName,
				password);
		if (ser == null) {
			throw new QMException("�Ҳ���EJB����(" + serName + ")");
		}
		return ser;
	}

	/**
	 * �����ļ�·��,��չ����ȡָ��·���µ�ָ����չ�����ļ�����
	 * 
	 * @param fqfn
	 *            String ָ�����ļ�·����Ϣ
	 * @param ext
	 *            String �ļ�����չ��(Ĭ��Ϊlist)
	 * @return Collection Blob�α��ļ�����(FileItem)
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
	// //�����Ŀ¼,������.listΪ��չ�����ļ���
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
				throw new Exception("�׳��쳣������Ӧ�ύ!");
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
	  //Ϊ������ӿ��ء�
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

						// ��ѯ�㲿�����й������ĵ���ɾ��
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
	          	//Ϊ������ӿ��ء�
	          	if (PublishHelper.VERBOSE)
	          	//CCEnd by liunan 2008-09-03
							System.out.println("ɾ�����ĵ���:" + partdoc.getDocNum());

						}
						// ��ѯ�㲿��������EPM�ĵ���ɾ��
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
	        	//Ϊ������ӿ��ء�
	        	if (PublishHelper.VERBOSE)
	        	//CCEnd by liunan 2008-09-03
						System.out.println("ɾ�����㲿����:" + part.getPartNumber());
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
	 * ���ݻ��ߺ�ɾ���û����е��㲿�����㲿���������ĵ���EPM�ĵ�
	 * 
	 * @param baselineNumber
	 *            ���߱��
	 * @throws Exception
	 */
	public static void deletePublishDataByBaseline(String baselineNumber)
			throws Exception {
	  //CCBegin by liunan 2008-09-03
	  //Ϊ������ӿ��ء�
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
	  //Ϊ������ӿ��ء�
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
	    //Ϊ������ӿ��ء�
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
					// System.out.println("��ɾ���㲿��:" + part.getPartNumber());
					// if (describeDocs != null) {
					// Iterator iter = describeDocs.iterator();
					// while (iter.hasNext()) {
					// DocIfc doc = (DocIfc) iter.next();
					// docService.deleteDoc(doc.getBsoID());
					// pservice.removeValueInfo(doc);
					// System.out
					// .println("��ɾ�������ĵ�:" + doc.getDocNum());
					// }
					// }
					// if (epmDocs != null) {
					// Iterator iter = epmDocs.iterator();
					// while (iter.hasNext()) {
					// EPMDocumentInfo epmDoc = (EPMDocumentInfo) iter
					// .next();
					// pservice.removeValueInfo(epmDoc);
					// System.out.println("��ɾ��EPM�ĵ�:"
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
	  //Ϊ������ӿ��ء�
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out
				.println("=====>leave PublishHelper.deletePublishDataByBaseline,baselineNumber.");
	}

	private static void removeFromBaselines(QMPartIfc part) {
	  //CCBegin by liunan 2008-09-03
	  //Ϊ������ӿ��ء�
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out
				.println("enter PublishHelper.removeFromBaselines, part number: "
						+ part == null ? null : part.getPartNumber());
		if (part == null) {
	    //CCBegin by liunan 2008-09-03
	    //Ϊ������ӿ��ء�
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
	  //Ϊ������ӿ��ء�
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("leave PublishHelper.removeFromBaselines!");
	}

	protected static Collection getDescribeDocs(QMPartIfc part)
			throws QMException {
	  //CCBegin by liunan 2008-09-03
	  //Ϊ������ӿ��ء�
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out
				.println("=====>enter PublishHelper.getDescribeDocs,part number: "
						+ part.getPartNumber());
		Collection result = new ArrayList();
		PersistService pservice = (PersistService) EJBServiceHelper
				.getService("PersistService");
		// ��ѯ�㲿�����й������ĵ���ɾ��
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
	    //Ϊ������ӿ��ء�
	    if (PublishHelper.VERBOSE)
	    //CCEnd by liunan 2008-09-03
			System.out.println("����㲿���������ĵ�: " + partdoc.getDocNum());
		}
	  //CCBegin by liunan 2008-09-03
	  //Ϊ������ӿ��ء�
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
	  //Ϊ������ӿ��ء�
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out
				.println("=====>enter PublishHelper.getRelatedEpmDocs,part number: "
						+ part.getPartNumber());
		Collection result = new ArrayList();
		PersistService pservice = (PersistService) EJBServiceHelper
				.getService("PersistService");
		// ��ѯ�㲿�����й������ĵ���ɾ��
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
	    //Ϊ������ӿ��ء�
	    if (PublishHelper.VERBOSE)
	    //CCEnd by liunan 2008-09-03
			System.out.println("���EPM�ĵ� :" + epmdoc.getDocNumber());
		}
	  //CCBegin by liunan 2008-09-03
	  //Ϊ������ӿ��ء�
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
		// ���master�µ�����С�汾�������°������У�
		// ���һ��Ԫ�ؼ�Ϊ���°汾������㲿������
		Iterator iterators = collVersion.iterator();
		if (iterators.hasNext()) {
			iteratedIfc = (IteratedIfc) iterators.next();
		}

		epmdoc = (EPMDocumentInfo) iteratedIfc;
	  //CCBegin by liunan 2008-09-03
	  //Ϊ������ӿ��ء�
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("********** the epmdoc version is :"
				+ epmdoc.getVersionValue());
		epmdoc = (EPMDocumentInfo) ((VersionControlService) EJBServiceHelper
				.getService("VersionControlService"))
				.newVersion((VersionedIfc) epmdoc);
		pservice.saveValueInfo(epmdoc);
	  //CCBegin by liunan 2008-09-03
	  //Ϊ������ӿ��ء�
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
		// ���master�µ�����С�汾�������°������У�
		// ���һ��Ԫ�ؼ�Ϊ���°汾������㲿������
		Iterator iterators1 = collVersion1.iterator();
		if (iterators1.hasNext()) {
			iteratedIfc1 = (IteratedIfc) iterators1.next();
		}

		QMPartInfo part = (QMPartInfo) iteratedIfc1;

	  //CCBegin by liunan 2008-09-03
	  //Ϊ������ӿ��ء�
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("****** the part is :" + part.getName());
		EPMBuildLinksRuleInfo tempRule = new EPMBuildLinksRuleInfo(epmdoc, part);
		tempRule.setSourceInstance(epmdoc.getFamilyInstance());
		tempRule.setDescription("Build rule for (generic) instance:"
				+ epmdoc.getDocName());

		pservice.saveValueInfo(tempRule);
	  //CCBegin by liunan 2008-09-03
	  //Ϊ������ӿ��ء�
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("***** save linkrule successful "
				+ tempRule.getRightBsoID() + "," + tempRule.getLeftBsoID());
		EPMBuildHistoryInfo buildHistory = new EPMBuildHistoryInfo();
		buildHistory.setLeftBsoID(epmdoc.getBsoID());
		buildHistory.setRightBsoID(part.getBsoID());
		pservice.saveValueInfo(buildHistory);
	  //CCBegin by liunan 2008-09-03
	  //Ϊ������ӿ��ء�
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
	 * ����IBAֵ,����������
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
		// �������Գ����ߡ�
		holder = ((IBAValueService) PublishHelper
				.getEJBService("IBAValueService")).refreshAttributeContainer(
				holder, null, null, null);
		// ����������
		DefaultAttributeContainer container = (DefaultAttributeContainer) holder
				.getAttributeContainer();
		// container.setConstraintGroups(new ArrayList());
		AbstractValueView aValueView[] = container.getAttributeValues();
		// ɾ��������ԭ�е�����ֵ��
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
		// ��ӷ�����������ֵ��
		if (getSourceInfoByIBA()) {
			holder = PublishHelper.setPublishSourceInfoByIBA(holder, props);
			container = (DefaultAttributeContainer) holder
					.getAttributeContainer();
		}

		// ���ԭ������ֵ��
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

		// ɾ���㲿���Ĳο�����
		Vector result = (Vector) pservice.navigateValueInfo(part,
				"referencedBy", "PartReferenceLink", false);
		PartReferenceLinkIfc refLinkT;
		for (Iterator ite = result.iterator(); ite.hasNext();) {
			refLinkT = (PartReferenceLinkIfc) ite.next();
			pservice.removeValueInfo(refLinkT);
		}

		// ɾ���㲿������������
		result = (Vector) pservice.navigateValueInfo(part, "describes",
				"PartDescribeLink", false);
		Enumeration eum = result.elements();
		PartDescribeLinkIfc descLinkT;
		while (eum.hasMoreElements()) {
			descLinkT = (PartDescribeLinkIfc) eum.nextElement();
			pservice.removeValueInfo(descLinkT);
		}

		// ɾ���㲿����epm�Ĺ������
		QMQuery branchQuery = new QMQuery("EPMBuildLinksRule");
		QueryCondition condition = new QueryCondition("rightBsoID", "=", part
				.getBranchID());
		branchQuery.addCondition(condition);
		Collection queryresult = pservice.findValueInfo(branchQuery);
		for (Iterator iter = queryresult.iterator(); iter.hasNext();) {
			EPMBuildRuleIfc epmbuildrule = (EPMBuildRuleIfc) iter.next();
			pservice.removeValueInfo(epmbuildrule);
		}
		// ɾ���㲿����epm����ʷ����
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
	  //Ϊ������ӿ��ء�
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
	  //Ϊ������ӿ��ء�
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out
				.println("=====>PublishHelper.revisePartIfNeedRightByNumber end!");
	}

	public static void revisePartIfNeedRight(QMPartInfo part) {
	  //CCBegin by liunan 2008-09-03
	  //Ϊ������ӿ��ء�
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("=====>PublishHelper.revisePartIfNeedRight start!");
		if (part == null) {
	    //CCBegin by liunan 2008-09-03
	    //Ϊ������ӿ��ء�
	    if (PublishHelper.VERBOSE)
	    //CCEnd by liunan 2008-09-03
			System.out
					.println("=====>PublishHelper.revisePartIfNeedRight:�������Ϊ�գ�");
			return;
		}
		if (part.getAdHocAcl().getEntrySet().getValue() == null
				|| part.getAdHocAcl().getEntrySet().getValue().equals("")) {
	    //CCBegin by liunan 2008-09-03
	    //Ϊ������ӿ��ء�
	    if (PublishHelper.VERBOSE)
	    //CCEnd by liunan 2008-09-03
			System.out.println("=====>PublishHelper.revisePartIfNeedRight:�㲿��"
					+ part.getPartNumber() + "����Ҫ����!");
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
	  //Ϊ������ӿ��ء�
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("=====>PublishHelper.revisePartIfNeedRight end!");
	}

	public static void updatePartRightByPartNumber(String partNumber) {
	  //CCBegin by liunan 2008-09-03
	  //Ϊ������ӿ��ء�
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
	  //Ϊ������ӿ��ء�
	 	if (PublishHelper.VERBOSE)
	 	//CCEnd by liunan 2008-09-03
		System.out
				.println("=====>PublishHelper.updatePartRightByPartNumber end!");
	}

	public static void updatePartRightByPart(QMPartInfo part) {
	 	//CCBegin by liunan 2008-09-03
	  //Ϊ������ӿ��ء�
	  if (PublishHelper.VERBOSE)
	  //CCEnd by liunan 2008-09-03
		System.out.println("=====>PublishHelper.updatePartRightByPart start!");
		if (part == null) {
	    //CCBegin by liunan 2008-09-03
	   	//Ϊ������ӿ��ء�
	   	if (PublishHelper.VERBOSE)
	   	//CCEnd by liunan 2008-09-03
			System.out
					.println("=====>PublishHelper.updatePartRightByPart:�������Ϊ�գ�");
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
			//CCBegin by liunan 2010-01-31 �޸�updateBso����ʱ�����޸�ʱ�����
			//��ԭ�������������ĳ����������ķ���
			((PersistService) PublishHelper.getEJBService("PersistService"))
					.updateBso(part, false, false);
			//CCEnd by liunan 2010-01-31
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  //CCBegin by liunan 2008-09-03
  	//Ϊ������ӿ��ء�
  	if (PublishHelper.VERBOSE)
	 	//CCEnd by liunan 2008-09-03
		System.out.println("=====>PublishHelper.updatePartRightByPart end!");
	}

	/**
	 * ��һ���ܶ�̬Ȩ�޹���Ķ�����ȡ�����ĵ��������Ȩ���ַ���
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
	 	//Ϊ������ӿ��ء�
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
	 	//Ϊ������ӿ��ء�
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
				//ViewObjectIfc view = viewService.getView("������ͼ");
				ViewObjectIfc view = viewService.getView("���������ͼ");
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
				// ���û���ҵ�������ͼ�İ汾��ʹ�����µ�������ͼ�汾
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
			//CCBegin by liunan 2010-01-31 �޸�updateBso����ʱ�����޸�ʱ�����
			//��ԭ�������������ĳ����������ķ�����
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
	 * �������ķ����Ķ���Ҫ���������ϼн���ӳ��
	 * 
	 * @param location
	 *            ���ϼ�ȫ·��
	 * @return ��ȡ��������ϼ�
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
	 * �����ĵ�ID�ж��ĵ��Ƿ��� ����֪ͨ�� �����ݽ���ȷ��֪ͨ�顣
	 * 
	 * @param docId
	 *            �ĵ�BsoID
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
	 * �����ĵ�ֵ�����ж��ĵ��Ƿ��� ����֪ͨ�� �����ݽ���ȷ��֪ͨ�顣
	 * 
	 * @param doc
	 *            �ĵ�ֵ����
	 * @return
	 * @author liunan 2010-01-20
	 */
	public static String isCaiYong(DocIfc doc) 
	{
		if (doc == null) 
		{
			return "false";
		}
		//CCBegin by liunan 2010-12-17 �޸��Ƿ��ǲ���֪ͨ����ж���������mappingreceive.properties��
		/*if (doc.getDocNum().startsWith("CONFIRMATION-CT")) 
		{
			return "true";
		}
		else 
		{
			return "false";
		}*/
		//��ȡ���������Ĳ���֪ͨ����ǰ׺��
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
	 * ��ȡ ����֪ͨ�� �����ݽ���ȷ��֪ͨ�� ���ĵ��ڼ�������ϵͳ�е�������Ϣ.
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
	 * ��ȡ ����֪ͨ�� �����ݽ���ȷ��֪ͨ�� ���ĵ��ڼ�������ϵͳ�е�������Ϣ.
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
			//�˴����� Ҳʹ�ñ���ķָ�����
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
	 * ���� ����֪ͨ�� �����ݽ���ȷ��֪ͨ�� ���ĵ�id��ȡ���õ��㲿��
	 * @param docId ����֪ͨ�� �����ݽ���ȷ��֪ͨ�� ���ĵ�
	 * @return �ַ������鼯��,ÿ���ַ��������¼��һ���㲿����id����š����ơ��������İ汾
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
	 * ���� ����֪ͨ�� �����ݽ���ȷ��֪ͨ�� ���ĵ���ȡ���õ��㲿��
	 * @param changeOrder  ����֪ͨ�� �����ݽ���ȷ��֪ͨ�� ���ĵ�
	 * @return �ַ������鼯��,ÿ���ַ��������¼��һ���㲿����id����š����ơ��������İ汾
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
			//CCBegin by liunan 2010-01-29 ˵������ʱ�Ĵ���
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
	 * ͨ���ĵ������ַ�����ȡ���õ��㲿��
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
		tokens.nextToken();// ������һ��
		String cyPartsMessage = tokens.nextToken();
		tokens = new YQTokenizer(cyPartsMessage,
				CHANGEORDER_PART_PART_SEPERATOR);
		while (tokens.hasMoreTokens()) 
		{
			YQTokenizer partTokens = new YQTokenizer(tokens.nextToken(),
					CHANGEORDER_PART_NUMBER_NAME_VERSION_SEPERATOR);
			//������ǰ��״̬����£���ʾ�������ݵ�����
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
	 * �����������Ĳ����㲿���б���ַ����������Ҫ�ĸ�ʽ
	 * �������ĸ�ʽ��{status=������, level=00, version=A.2, number=2010123, name=123}@@@{status=������, level=01, version=A.2, number=20100118-4, name=hill}
	 * �����ĸ�ʽ��!$%*&2010123!*!123!*!A.2!*%#!20100118-4!*!hill!*!A.2
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
	 * ��������㲿�����е�һ���㲿���������ַ�����
	 * @author liunan 2010-01-21
	 */
	public static String doCaiYongPartString(String desc)
	{
		YQTokenizer tokens = new YQTokenizer(desc, ",");
		tokens.nextToken();  //״̬
		tokens.nextToken();  //�㼶
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
	 * ���� ����֪ͨ�� �����ݷ���ȷ��֪ͨ���ĵ� ��ȡ�����ļ�¼˵����Ϣ����Ҫ���ݡ�
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
	 * �����ĵ�ID�ж��ĵ��Ƿ��ǡ����ݽ���ȷ��֪ͨ�顱���ĵ�
	 * 
	 * @param docId
	 *            �ĵ�BsoID
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
	 * �����ĵ�ֵ�����ж��Ƿ��ǡ����ݽ���ȷ��֪ͨ�顱���ĵ�
	 * 
	 * @param doc
	 *            �ĵ�ֵ����
	 * @return
	 */
	public static String isConfirmDoc(DocIfc doc) {
		if (doc == null) {
			return "false";
		}
		
		if (doc.getDocCfName().equals("���ݽ���ȷ��֪ͨ��")) {
			return "true";
		} else {
			return "false";
		}
	}
	
	/**
	 * ��ȡ����ȷ��֪ͨ��Ĺ��������
	 * @param docId ����ȷ��֪ͨ��BsoID
	 * @return docIfc �����ı����ֵ����
	 * @throws QMException
	 */
	public static Collection getConfirmDoc(String docId) throws QMException {
		//����ȷ��֪ͨ��
		DocIfc doc = getDocInfoById(docId);
		String ConfirmDocNum = doc.getDocNum();
		String key = ConfirmDocNum.substring(ConfirmDocNum.indexOf("CONFIRMATION-") + 13, ConfirmDocNum.length());
		
		//�����
		DocIfc docIfc = null;
		PersistService ps = (PersistService) EJBServiceHelper.getPersistService();
		
		QMQuery query = new QMQuery("Doc", "DocMaster");
		int k = query.appendBso("DocClassification", false);
		//QueryCondition con1 = new QueryCondition("technicsName",QueryCondition.LIKE,"%"+stepName+"%");
		query.addCondition(0, new QueryCondition("location", QueryCondition.LIKE, "%�������ı����%"));
		query.addAND();
		query.addCondition(1, new QueryCondition("docNum", QueryCondition.LIKE, "%" + key + "%"));
		query.addAND();
		query.addCondition(k, new QueryCondition("docCfName", QueryCondition.EQUAL, "�������ı����"));
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
						return value;//��ֵ��մ�
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
	 * �����������������������ַ��������ɵ�excel�У�����������ŵ�����״̬�㲿������ ����֪ͨ��� ����
	 * �������ĸ�ʽ��{status=������, level=��, version=A.2, number=2010123, name=123}@@@{status=������, level=��, version=A.2, number=20100118-4, name=hill}
	 */
	public static void createShiZhiRenWu(DocInfo docInfo, String desc)
	{
		System.out.println("���� createShiZhiRenWu docInfo="+docInfo.getDocNum()+" desc="+desc);
		if(desc.equals(""))
		{
			return;
		}
		YQTokenizer tokens = new YQTokenizer(desc, CHANGEORDER_DES_PART_SEPERATOR);
		if (tokens.countTokens() != 2)
		{
			return;
		}
		tokens.nextToken();// ������һ��
		
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
      	cell.setCellValue(isjf);//�Ƿ���׼��
      	
      	cell = row.createCell( (short) 1);
      	cell.setCellValue(numStr);//���
      	
      	cell = row.createCell( (short) 2);
      	cell.setCellValue(nameStr);//����
      	
      	cell = row.createCell( (short) 3);
      	cell.setCellValue(versionStr);//�汾
      	
      	QMPartInfo part = (QMPartInfo)getPartInfoByOrigInfo(numStr, versionStr);
      	System.out.println("part="+part);
      	
      	if(part==null)
      	{
      		part = (QMPartInfo)getPartInfoByNumber(numStr);
      	}
      	
      	cell = row.createCell( (short) 4);
      	cell.setCellValue(part.getLifeCycleState().getDisplay());//״̬
      	i++;
      	System.out.println("isjf="+isjf+"  numStr="+numStr+"  nameStr="+nameStr+"  versionStr="+versionStr+"  status="+status);
      	//���������״̬�㲿����������㲿���İ汾ע�����¼�ǡ��������ơ����ǡ�������ơ�
      	if(status.indexOf("TEST")!=-1)
      	{
      		if(isjf.equals("��"))
      		{
      			part.setIterationNote("�������");
      			part = (QMPartInfo) ps.saveValueInfo(part, false);
      		}
      		else if(isjf.equals("��"))
      		{
      			part.setIterationNote("��������");
      			part = (QMPartInfo) ps.saveValueInfo(part, false);
      		}
      	}
      	//����Ƿ�����ŵ�����״̬�㲿������ά��IBA���ԡ�����֪ͨ��š�
      	/*if(isjf.equals("��")&&status.indexOf("TEST")!=-1)
      	{
      		QMQuery query1 = new QMQuery("StringValue");
      		query1.addCondition(new QueryCondition("iBAHolderBsoID", "=",part.getBsoID()));
      		query1.addAND();
      		query1.addCondition(new QueryCondition("definitionBsoID", "=","StringDefinition_6116703"));
      		Collection result1 = ps.findValueInfo(query1,false);
      		if(result1!=null&&result1.size()>0)
      		{
      			StringValueInfo svi = (StringValueInfo) result1.iterator().next();
      			System.out.println("�㲿�� "+numStr+" ��ԭ����֪ͨ���Ϊ��"+svi.getValue()+" ���������񵥺�Ϊ��"+docInfo.getDocNum());
      			PublishPartsLog.log("�㲿�� "+numStr+" ��ԭ����֪ͨ���Ϊ��"+svi.getValue()+" ���������񵥺�Ϊ��"+docInfo.getDocNum());
      			if(!svi.getValue().equals(docInfo.getDocNum()))
      			{
      				svi.setValue(docInfo.getDocNum());
      				ps.updateBso(svi, false);
      			}
      		}
      		else
      		{
      			System.out.println("�㲿�� "+numStr+" ���������񵥺�Ϊ��"+docInfo.getDocNum());
      			PublishPartsLog.log("�㲿�� "+numStr+" ���������񵥺�Ϊ��"+docInfo.getDocNum());
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
    	//�ļ��������
    	System.out.println("�ļ�������ϣ�jfile="+jfile.getPath());
    	//���ļ��ҵ��ĵ������ϡ�
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
    	System.out.println("�ĵ����������ϣ�");
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
	 * ����"�������ı����"���ĵ���ȡ��������ŵ��㲿��
	 * @param changeOrder "�������ı����"���ĵ�
	 * @return �ַ������鼯��,ÿ���ַ��������¼��һ���㲿����id����š����ơ��������İ汾
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
			tokens.nextToken();// ������һ��
      tokens = new YQTokenizer(tokens.nextToken(), "@@@");
      while (tokens.hasMoreTokens())
      {
      	String desction = tokens.nextToken();
      	System.out.println("desction="+desction);
      	YQTokenizer ts = new YQTokenizer(desction, ",");
      	String temp = ts.nextToken();
      	String partState = temp.substring(temp.indexOf("=")+1);//״̬
      	temp = ts.nextToken();
      	String isjf = temp.substring(temp.indexOf("=")+1);//�Ƿ�������
      	
      	//if(isjf.equals("��")||partState.indexOf("TEST")==-1)
      	if(partState.indexOf("TEST")!=-1)
      	{
      		temp = ts.nextToken();
      		String partVersion = temp.substring(temp.indexOf("=")+1);//�汾
      		temp = ts.nextToken();
      		String partNumber = temp.substring(temp.indexOf("=")+1);//���
      		temp = ts.nextToken();
      		String partName = temp.substring(temp.indexOf("=")+1);//����
      		
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
	 * ͨ���ĵ������ַ�����ȡ�ο����㲿��
	 */
	public static Vector getParts(String desc)
	{
		Vector result = new Vector();
		if (desc == null || desc.length() == 0)
		{
			return result;
		}
		YQTokenizer tokens = new YQTokenizer(desc, CHANGEORDER_DES_PART_SEPERATOR);
		System.out.println("�����ķֶ�����"+tokens.countTokens());
		if (tokens.countTokens() != 2)
		{
			return result;
		}
		tokens.nextToken();// ������һ��
		String beforePartsMessage = tokens.nextToken();
		tokens = new YQTokenizer(beforePartsMessage, CHANGEORDER_PART_PART_SEPERATOR);
		while (tokens.hasMoreTokens())
		{
			YQTokenizer partTokens = new YQTokenizer(tokens.nextToken(), CHANGEORDER_PART_NUMBER_NAME_VERSION_SEPERATOR);
			System.out.println("part���Եķֶ�����"+partTokens.countTokens());
			//������ǰ��״̬����£���ʾ�������ݵ�����
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
	 * �����㲿���ı��,����Դ�汾����λJF��β���㲿������
	 * @param num String �㲿���ı��
	 * @param iteration String ����Դ��С�汾��
	 * @throws QMException
	 * @return QMPartInfo �㲿������
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
			// �����ж����԰汾����,�����ҵ���һ�����������ļ���
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
