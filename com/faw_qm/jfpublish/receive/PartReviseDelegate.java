/**
 * SS1 �޶�ʱ�������һ��ĸ���������˴��޶��ļ�û�з����������ͻῴ������һ�渽����
 * ��ǰ���޶��󱣴�ǰ��������ɾ�����������ᵽlogicbase��Ϣ��������δ����ˡ��������ɾ���� liunan 2012-12-10
 * SS2 ������Ψһ�ġ������ظ�����ʱ��ʾ�ģ������ɹ����ء� liunan 2014-2-19
 * SS3 ���ڱ���������㲿��������������1�����غż������ͼ������������ͼ�� 2����������ͼ�����汾�� liunan 2014-4-4
 * SS4 ����������Ѿ������������д�����������쳣������ʾ�����ɹ��� liunan 2014-4-14
 * SS5 �������ķ��㲿�����������������ͼ ������ 2014-7-8
 * SS6 �޶�֮����������Ϊ���������ͼ ������ 2014-10-8
 * SS7 �����Ƶ����к󣬽������㲿�������������У���˻����·�������ţ�Ŀǰ����㲿����û�з���Դ�汾��������������������
 * ������ͽ����һ�������һ����汾�С���Ϊ���ڽ��ϵͳ��û�з���Դ�汾ʱ��������м���A������С���򣬷����޶���liunan 2017-5-23
 * SS8 �㲿������󣬽��汾ע��������ա� liunan 2017-7-3
 * SS9 ��������״̬�㲿���� liunan 2017-7-6
 * SS10 ˫�汾������������� liunan 2017-7-25
 * SS11 �ɶ��ص������ǹ�����ͼ����Ϊ������ͼ���ң������ҵ�������ͼ�ļ��������°汾�޶������ liunan 2018-1-11
 */
 // 
package com.faw_qm.jfpublish.receive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;	
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
//CCBegin by liunan 2008-09-02
import java.util.StringTokenizer;
//CCEnd by liunan 2008-09-02

import com.faw_qm.acl.model.AdHocControlledIfc;
import com.faw_qm.acl.util.AclEntrySet;
import com.faw_qm.acl.util.AdHocAcl;
import com.faw_qm.config.ejb.service.ConfigService;
import com.faw_qm.epm.build.model.EPMBuildHistoryIfc;
import com.faw_qm.epm.build.model.EPMBuildRuleIfc;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.iba.definition.litedefinition.BooleanDefView;
import com.faw_qm.iba.definition.litedefinition.FloatDefView;
import com.faw_qm.iba.definition.litedefinition.IntegerDefView;
import com.faw_qm.iba.definition.litedefinition.RatioDefView;
import com.faw_qm.iba.definition.litedefinition.StringDefView;
import com.faw_qm.iba.definition.litedefinition.TimestampDefView;
import com.faw_qm.iba.definition.litedefinition.URLDefView;
import com.faw_qm.iba.definition.litedefinition.UnitDefView;
import com.faw_qm.iba.value.ejb.service.IBAValueObjectsFactory;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
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
import com.faw_qm.iba.value.model.AbstractValueIfc;
import com.faw_qm.iba.value.model.IBAHolderIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartConfigSpecIfc;
import com.faw_qm.part.model.PartConfigSpecInfo;
import com.faw_qm.part.model.PartDescribeLinkIfc;
import com.faw_qm.part.model.PartReferenceLinkIfc;
import com.faw_qm.part.model.PartUsageLinkIfc;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.PartConfigSpecAssistant;
import com.faw_qm.part.util.PartStandardConfigSpec;
import com.faw_qm.part.util.ProducedBy;
import com.faw_qm.part.util.QMPartType;
import com.faw_qm.pcfg.family.model.GenericPartInfo;
import com.faw_qm.pcfg.logic.LogicBase;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.version.model.MasteredIfc;
import com.faw_qm.version.model.VersionedIfc;
import com.faw_qm.viewmanage.ejb.service.ViewService;
import com.faw_qm.viewmanage.model.ViewObjectIfc;
//CCBegin by liunan 2008-10-22
import com.faw_qm.version.model.IteratedIfc;
//CCEnd by liunan 2008-10-22

//CCBegin by liunan 2008-11-17
import com.faw_qm.wip.ejb.service.WorkInProgressService;
import com.faw_qm.folder.model.FolderIfc;
import com.faw_qm.folder.ejb.service.FolderService;
import com.faw_qm.wip.util.WorkInProgressHelper;
import com.faw_qm.lock.util.LockHelper;
import com.faw_qm.wip.model.WorkableIfc;
import com.faw_qm.lock.exception.LockException;
//CCEnd by liunan 2008-11-17

//CCBegin by liunan 2009-11-06
import java.io.IOException;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import com.faw_qm.integration.util.QMProperties;
//CCEnd by liunan 2009-11-06

import com.faw_qm.session.ejb.service.SessionService;

//CCBegin by liunan 2010-01-20
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentHolderIfc;
//CCEnd by liunan 2010-01-20
import com.jf.util.changeVersion;
//CCBegin SS10
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//CCEnd SS10

public class PartReviseDelegate extends AbstractStoreDelegate {

	public static final String DEFAULT_PART_LOCATION = "\\Root\\Administrator";

	private Group myParts;

	private Hashtable myPartIBAMap;
	
	//CCBegin by liunan 2009-11-06 ��Ӷ�logicbase�ı���
	private static String LOGICBASE_PATH;

	static {
		try {
			LOGICBASE_PATH = QMProperties.getLocalProperties().getProperty(
					"publish.logicbase.path");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static SimpleDateFormat logDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd-HH.mm");
	//CCEnd by liunan 2009-11-06
	
	public PartReviseDelegate(Group parts, Hashtable partIBAMap,
			PublishLoadHelper helper) {
		this.myParts = parts;
		this.myPartIBAMap = partIBAMap;
		this.myHelper = helper;
	}

	public ResultReport process() {
		userLog("##RevisePart");
		ResultReport result = new ResultReport();
		if (myParts == null || myParts.getElementCount() == 0) {
			log("û��Ҫ�޶����㲿��");
			return result;
		}
		else
		{
			System.out.println("�����㲿���ĸ���Ϊ��"+myParts.getElementCount());
		}
		StandardPartService partSer = null;
		try {
			partSer = (StandardPartService) PublishHelper
					.getEJBService("StandardPartService");
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		IBAValueService ibaService = null;
		try {
			ibaService = (IBAValueService) PublishHelper
					.getEJBService("IBAValueService");
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ConfigService cservice = null;
		try {
			cservice = (ConfigService) PublishHelper
					.getEJBService("ConfigService");
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Enumeration enumeration = myParts.getElements();
		PersistService ps = null;
		try {
			ps = (PersistService) PublishHelper.getEJBService("PersistService");
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Element ele = null;
		String num = null;
		String name = null;
		String location = null;
		String partType = null;
		String source = null;
		String type = null;
		String lifecycle = null;
		String project = null;
		String viewName = null;
		String maxCompIdUsed = null;
		String sourceVersion = null;
		String modifier = null;
		QMPartIfc part = null;
		int serNum = 0;
		boolean isgpart = false;
		log("�޶��㲿����Ϣ:");
		QMTransaction transaction = null;
		DefaultAttributeContainer attc = null;
		AbstractValueView[] valueview = null;
		AbstractValueIfc value = null;
		String fbsourversion = "";
		String receproversion = "";
		//CCBegin by liunan 2008-10-28
		//�����������״̬�ķ�����
		String lifecyclestate = "";
		String qylifecyclestate = "";
		//CCEnd by liunan 2008-10-28
		//CCBegin by liunan 2009-12-07 ����Ƿ��и����ı�ʶ��
		//String isHasCont = ""; 
		//CCEnd by liunan 2009-12-07
		while (enumeration.hasMoreElements()) {
			myHelper.rezong++;
			serNum++;
			ele = (Element) enumeration.nextElement();
			num = ((String) ele.getValue("num")).trim();
			partType = (String) ele.getValue("partType");
			type = (String) ele.getValue("type");
			source = (String) ele.getValue("source");
			sourceVersion = (String) ele.getValue("sourceVersion");
			modifier = (String) ele.getValue("modify");
			isgpart = type.toLowerCase().trim().equals("genericpart");
			//CCBegin by liunan 2011-09-29 ȥ�������пո�
			//name = (String) ele.getValue("name");
			name = ((String) ele.getValue("name")).trim();
			//CCEnd by liunan 2011-09-29
			//CCBegin by liunan 2009-09-24 ��������к���Ӣ�ġ�;������ĳ����ġ�����
			name = name.replaceAll(";","��");
			//CCBegin by liunan 2009-09-24
			viewName = (String) ele.getValue("viewName");
		  //CCBegin by liunan 2008-10-28
	  	//�����������״̬�ķ�����
			lifecyclestate = (String) ele.getValue("lifecyclestate");
			qylifecyclestate = lifecyclestate;
			
			//CCBegin by liunan 2009-12-07 ����Ƿ��и����ı�ʶ��
			//isHasCont = (String) ele.getValue("isHasCont");
			//CCEnd by liunan 2009-12-07
			
			//CCBegin by liunan 2011-03-14 �����û��ֹ������㲿��ʱ��fbsourversionû�и�ֵ��������һ����¼��
			fbsourversion = "";
			//CCEnd by liunan 2011-03-14

			//System.out.println("�����㲿����������״̬======="+lifecyclestate);
			//ͨ�������ļ���ȡ��֮��Ӧ�Ľ����������״̬��
			lifecyclestate = myHelper.mapPro.getProperty("part.lifecyclestate." + lifecyclestate);
			//System.out.println("����㲿����������״̬======="+lifecyclestate);
		  //CCEnd by liunan 2008-10-28

			// ��ȡ�����ļ��������ͼ��Ӧֵ
			viewName = myHelper.mapPro.getProperty("part.viewName." + viewName);
			if (num == null || num.trim().length() == 0) {
				result.failureOne();
				String msg = "at save Part (num=" + num + " name=" + name
						+ ") ERROR: �㲿�����Ϊ��";
				result.addErroMsg(num + "," + part.getName() + ","
						+ sourceVersion + "," + partType + ",ʧ��");
				errorLog(msg);
				continue;
			}
			location = (String) ele.getValue("path");
			if (location == null || location.trim().equals("")) {
				//location = DEFAULT_PART_LOCATION;
				location = myHelper.mapPro.getProperty("part.location.default");
			} else {
				location=PublishHelper.cutPathString(location);
				if (location.startsWith("/")) {
					location = location.substring(1);
				}
				if (location.startsWith("Standard_part")) {
					location = location.substring(0, 13);
					String stand = location.substring(13);
					int l = stand.indexOf("/");
					if (l != -1 && l != 0) {
						location = location + stand.substring(0, l);
					}
				} else {
					int h = location.indexOf("/");
					int m = 0;
					if (h == -1) {
						m = location.indexOf("\\");
						if (m != -1) {
							location = location.substring(0, m);
						}
					} else {
						location = location.substring(0, h);

					}
				}

				if (isgpart) {
					location = myHelper.mapPro.getProperty("gpart.location."
							+ location, null);
					if (location == null || location.trim().equals("")) {
						location = myHelper.mapPro
								.getProperty("gpart.location.default");
					}
				} else {
					location = myHelper.mapPro.getProperty("part.location."
							+ location);
					if (location == null || location.trim().equals("")) {
						location = myHelper.mapPro
								.getProperty("part.location.default");
					}
				}
			}
			// location = PublishHelper.strDecoding(location, "ISO8859-1");
			location = PublishHelper.normalLocation(location);

			lifecycle = myHelper.mapPro.getProperty("jfpublish.part.lifecycle",
					null);
			if (lifecycle == null || lifecycle.trim().equals("")) {
				lifecycle = PublishLoadHelper.DEFAULT_LIFECYCLE;
			} // else {
			// lifecycle = PublishHelper.strDecoding(lifecycle, "ISO8859-1");
			// }

			Properties sourceProps = new Properties();
			if (myHelper.dataSource != null && myHelper.dataSource.length() > 0) {
				sourceProps.put("dataSource", myHelper.dataSource);
			}
			if (myHelper.publishDate != null
					&& myHelper.publishDate.length() > 0) {
				sourceProps.put("publishDate", myHelper.publishDate);
			}
			if (sourceVersion != null && sourceVersion.length() > 0) {
				sourceProps.put("sourceVersion", sourceVersion);
			}
			else
			{
				System.out.println("anantt revise   �㲿�� "+num+" sourceVersion  is  null!!!       and =="+ (String) ele.getValue("sourceVersion"));//anan
			}
			if (myHelper.publishForm != null
					&& myHelper.publishForm.length() > 0) {
				sourceProps.put("publishForm", myHelper.publishForm);
			}
			if (modifier != null && modifier.length() > 0) {
				sourceProps.put("modifier", modifier);
			}
			if (myHelper.noteNum != null && myHelper.noteNum.length() > 0) {
				sourceProps.put("noteNum", myHelper.noteNum);
			}
			System.out.println("anantt revise   �㲿�� "+num+" and sourceVersion=="+ (String) ele.getValue("sourceVersion")+"  and  sourceProps.get==="+sourceProps.get("sourceVersion"));//anan
			try {
				transaction = new QMTransaction();
				transaction.begin();
				LogicBase lb = null;
				//CCBegin by liunan 2008-10-22
				//������޶���������С�汾�ı�ʶ��true��ʾ�޶���false��ʾ����С�汾��
				boolean isReviseOrUp = true;
				//CCEnd by liunan 2008-10-22
				if (isgpart) {
					part = PublishHelper.getGPartInfoByNumber(num);
					receproversion = part.getVersionValue();

					//CCBegin by liunan 2009-01-21
					String partState = part.getLifeCycleState().toString();
					String partnote = part.getIterationNote();
					//CCEnd by liunan 2009-01-21

					part = (QMPartInfo) ibaService
							.refreshAttributeContainerWithoutConstraints(part);
					attc = (DefaultAttributeContainer) part
							.getAttributeContainer();
					valueview = attc.getAttributeValues();
					int m = valueview.length;
					for (int i = 0; i < m; i++) {
						if ((valueview[i].getDefinition().getName())
								.toLowerCase().trim().equals("sourceversion")) {
							value = IBAValueObjectsFactory.newAttributeValue(
									valueview[i], part);
							fbsourversion = ((StringValueIfc) value).getValue();
						}
					}
                                        //CCBegin by liunan 2008-09-02
                                        //�޸���ǰ�İ汾�ȽϷ������޷���ȷ�Ƚϳ�AA��B��C��D�Ȱ汾�Ĵ�С��
                                        //���ò�Ʒ�а汾������õİ汾�ȽϷ�����
                                        //if(!(sourceVersion.compareTo(fbsourversion)>0))
                                        //System.out.println(sourceVersion+"==============process()��gpart============="+fbsourversion);
                                        //CCBegin by liunan 2009-01-21
                                        //Դ�����£�
                                        //if(!getPublishFlag(sourceVersion,fbsourversion))
                                        //{
                                        System.out.println(sourceVersion+"=="+fbsourversion+"=="+partState+"=="+qylifecyclestate);
                                        if(getPublishFlag(sourceVersion,fbsourversion))
                                        {
                                        }
                                        //CCBegin by liunan 2009-01-21
                                        //����汾һ������Ƚ���������״̬������Ϊ��׼�����Ϊ����׼���򷢲�����ʵ�ʱ���ʱֻ��״̬�Ͳ��á�����֪ͨ�������޸ġ�
                                        //CCBegin by liunan 2012-05-14 ��������״̬���ٰ���׼״̬�������������⡣
                                        //else if(sourceVersion.equals(fbsourversion)&&!partState.equals("PREPARING")&&qylifecyclestate.equals("PREPARE"))
                                        else if(sourceVersion.equals(fbsourversion)&&!partState.equals("PREPARING")&&!partState.equals("MANUFACTURING")&&qylifecyclestate.equals("PREPARE"))
                                        //CCEnd by liunan 2012-05-14
                                        {
                                        }
                                        //CCBegin by liunan 2011-08-01
                                        //����汾һ������Ƚ���������״̬������Ϊǰ׼�����Ϊ����׼����ǰ׼���򷢲�����ʵ�ʱ���ʱֻ��״̬�Ͳ��á�����֪ͨ�������޸ġ�
                                        else if(sourceVersion.equals(fbsourversion)&&!partState.equals("PREPARING")&&!partState.equals("ADVANCEPREPARE")&&qylifecyclestate.equals("ADVANCEPREPARE"))
                                        {
                                        	System.out.println("new ADVANCEPREPARE �������============="+num);
                                        }
                                        //CCEnd by liunan 2011-08-01
                                        //����汾һ������Ƚ���������״̬������Ϊ��׼�����Ϊ��׼���ҽ�ŵġ��汾ע�͡����Ժ��С�ǰ׼���������򷢲�����ʵ�ʱ���ʱֻ��״̬�Ͳ��á�����֪ͨ�������޸ġ�
                                        //CCBegin by liunan 2011-04-22 ǰ׼�ж���>�ĳ�>=
                                        else if(sourceVersion.equals(fbsourversion)&&partState.equals("PREPARING")&&partnote!=null&&partnote.indexOf("ǰ׼")>=0&&qylifecyclestate.equals("PREPARE"))
                                        {
                                        }
                                        //CCBegin SS9
                                        //����汾һ������Ƚ���������״̬������Ϊ���ƣ����Ϊ����׼�ҷ�ǰ׼���������򷢲�����ʵ�ʱ���ʱֻ��״̬�Ͳ��á�����֪ͨ�������޸ġ�
                                        else if(sourceVersion.equals(fbsourversion)&&!partState.equals("PREPARING")&&!partState.equals("ADVANCEPREPARE")&&!partState.equals("MANUFACTURING")&&!partState.equals("SHIZHI")&&qylifecyclestate.indexOf("TEST")!=-1)
                                        {
                                        	System.out.println("anan  �����Ƽ���");
                                        }
                                        //CCEnd SS9
                                        //CCBegin by liunan 2010-05-19
                                        //���fbsourversionΪ""��˵���ü���ϵͳ�д��ڣ��ǽ���û��ֹ������ġ�ֱ������
                                        else if(fbsourversion.equals(""))
                                        {
                                        	//CCBegin SS7
                                        	//isReviseOrUp = false;
                                        	if(sourceVersion.startsWith("A"))
                                        	{
                                        		isReviseOrUp = false;
                                        	}
                                        	//CCEnd SS7
                                        }
                                        //CCEnd by liunan 2010-05-19
                                        else
                                        {
                                        //CCEnd by liunan 2009-01-21
                                        //CCEnd by liunan 2008-09-02
						try {
							transaction.rollback();
						} catch (QMException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						this.myHelper.partsCannotStore.add(part.getPartNumber());
						log("���岿�� " + num + " ����Ҫ�޶�");
						userLog(serNum + "," + name + "," + num + ","
								+ "�����㲿��" + "," + fbsourversion + ","
								+ sourceVersion + "," + receproversion + ","
								+ part.getVersionValue() + ",�ɹ�");
						//CCBegin by liunan 2011-04-22 �����Ѵ��ڵģ���ʾ�ɹ���������partVector����ӣ���ֹ�ṹ���ס�
						//myHelper.partVector.addElement(num);
						//CCEnd by liunan 2011-04-22
						result.successOne();
						myHelper.resucnum++;
						continue;
					}
					//CCBegin by liunan 2008-10-22
					//�ߵ��˴�����ʾ���㲿����Ҫ����������С�Ȼ��Ƚ�������汾�����һ����ʾ�㲿��������С�汾���������޶�������
					if(sourceVersion.indexOf(".")>0&&fbsourversion.indexOf(".")>0)
					{
					  if(sourceVersion.substring(0,sourceVersion.indexOf(".")).equals(fbsourversion.substring(0,fbsourversion.indexOf("."))))
					  {
						  isReviseOrUp = false;
					  }
					  //CCBegin SS10
					  //P.2���P2.1��������з���Դ�汾�����Խ�Ŵ�汾Ϊ��ͷ��ĸ�����ʾ�ɻ���������С�汾������
					  //if(sourceVersion.substring(0,sourceVersion.indexOf(".")).startsWith(fbsourversion.substring(0,fbsourversion.indexOf("."))))
					  if(matchResult(Pattern.compile("[a-z||A-Z]"),sourceVersion).equals(matchResult(Pattern.compile("[a-z||A-Z]"),fbsourversion)))
					  {
						  isReviseOrUp = false;
					  }
					  //CCEnd SS10
				  }
					//CCEnd by liunan 2008-10-22
					maxCompIdUsed = (String) ele.getValue("maxCompIdUsed");
					String logicBase = (String) ele.getValue("logicBase");
					
					//CCBegin by liunan 2010-01-20 ��ֹlogicbaseΪnull��
					if(logicBase==null)
					{
						logicBase = "";
					}
					//CCEnd by liunan 2010-01-20
					
					//CCBegin by liunan 2009-11-06 ��Ӷ�logicbase�ı��档
					try 
					{
						String path = LOGICBASE_PATH+num+"-"+logDateFormat.format(new java.util.Date())+".xml";
						FileWriter writer = new FileWriter(path);
						writer.write(logicBase);
						writer.flush();
						writer.close();
					}
					catch (Exception e) 
					{
						e.printStackTrace();
					}
					//CCEnd by liunan 2009-11-06
					System.out.println("GPart number =========="+num);
					if(logicBase==null)
						System.out.println("logicBase is null!!!");
					else
						System.out.println("logicBase.length()=========="+logicBase.length());
					lb = LogicBaseConversion.xmlToLogicBase(logicBase);
					if(lb==null)
						System.out.println("logicBase is null!!!");
					else
						System.out.println("logicBase is not null!!!");
				} else {
					ViewService viewService = (ViewService) PublishHelper
							.getEJBService("ViewService");
					//CCBegin SS5 SS11
					ViewObjectIfc view = viewService.getView("������ͼ");
					//ViewObjectIfc view = viewService.getView("���������ͼ");
					//CCEnd SS5 SS11
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

					QMPartMasterIfc partmaster = PublishHelper
							.getPartMasterByNumber(num);
					if (PublishHelper.VERBOSE)
						System.out.println("==============================>"
								+ num);
					Collection part1 = cservice.filteredIterationsOf(
							(MasteredIfc) partmaster,
							new PartConfigSpecAssistant(partConfigSpecIfc));
					Iterator ite = part1.iterator();
					//���û���ҵ�������ͼ�İ汾��ʹ�����µ�������ͼ�汾
					if (ite.hasNext()) {
						Object[] obj = (Object[]) ite.next();

						part = (QMPartInfo) obj[0];
					} else {
						part = PublishHelper.getPartInfoByNumber(num);
					}
					
					//CCBegin SS3
					//System.out.println("part000======"+part.getPartName());
					if(part.getViewName().trim().equals("�����ͼ"))
					//if(part.getViewName().trim().equals("�����ͼ"))
					{
						System.out.println("�������㲿������ͼ��partNum == "+num+" and  version=="+part.getVersionValue());
						com.jf.util.changeVersion.queryPartInfo(num);
						part = PublishHelper.getPartInfoByNumber(num);
			
					}	
//					if(part.getViewName().trim().equals("������ͼ"))
//						//if(part.getViewName().trim().equals("�����ͼ"))
//						{
//							com.jf.util.changeVersion.queryPartInfo(num);
//							part = PublishHelper.getPartInfoByNumber(num);
//						}	
					//CCEnd SS3
					
					receproversion = part.getVersionValue();

					//CCBegin by liunan 2009-01-21
					String partState = part.getLifeCycleState().toString();
					String partnote = part.getIterationNote();
					//CCEnd by liunan 2009-01-21

					part = (QMPartInfo) ibaService
							.refreshAttributeContainerWithoutConstraints(part);
					attc = (DefaultAttributeContainer) part
							.getAttributeContainer();
					valueview = attc.getAttributeValues();
					int m = valueview.length;
					for (int i = 0; i < m; i++) {
						if ((valueview[i].getDefinition().getName())
								.toLowerCase().trim().equals("sourceversion")) {
							value = IBAValueObjectsFactory.newAttributeValue(
									valueview[i], part);
							fbsourversion = ((StringValueIfc) value).getValue();
						}
					}
                                        //CCBegin by liunan 2008-09-02
                                        //�޸���ǰ�İ汾�ȽϷ������޷���ȷ�Ƚϳ�AA��B��C��D�Ȱ汾�Ĵ�С��
                                        //���ò�Ʒ�а汾������õİ汾�ȽϷ�����
                                        //if(!(sourceVersion.compareTo(fbsourversion)>0))
                                        System.out.println(sourceVersion+"==============�㲿��("+num+")============= ("+fbsourversion+")");
                                        //System.out.println(sourceVersion+"=="+fbsourversion+"=="+partState+"=="+qylifecyclestate);
                                        //CCBegin by liunan 2009-01-21
                                        //Դ�����£�
                                        //if(!getPublishFlag(sourceVersion,fbsourversion))
                                        //{
                                        if(getPublishFlag(sourceVersion,fbsourversion))
                                        {
                                        }
                                        //CCBegin by liunan 2009-01-21
                                        //����汾һ������Ƚ���������״̬������Ϊ��׼�����Ϊ����׼���򷢲�����ʵ�ʱ���ʱֻ��״̬�Ͳ��á�����֪ͨ�������޸ġ�
                                        //CCBegin by liunan 2012-05-14 ��������״̬���ٰ���׼״̬�������������⡣
                                        //else if(sourceVersion.equals(fbsourversion)&&!partState.equals("PREPARING")&&qylifecyclestate.equals("PREPARE"))
                                        else if(sourceVersion.equals(fbsourversion)&&!partState.equals("PREPARING")&&!partState.equals("MANUFACTURING")&&qylifecyclestate.equals("PREPARE"))
                                        //CCEnd by liunan 2012-05-14
                                        {
                                        }
                                        //CCBegin by liunan 2011-08-01
                                        //����汾һ������Ƚ���������״̬������Ϊ��׼�����Ϊ����׼���򷢲�����ʵ�ʱ���ʱֻ��״̬�Ͳ��á�����֪ͨ�������޸ġ�
                                        else if(sourceVersion.equals(fbsourversion)&&!partState.equals("PREPARING")&&!partState.equals("ADVANCEPREPARE")&&qylifecyclestate.equals("ADVANCEPREPARE"))
                                        {
                                        	System.out.println("new ADVANCEPREPARE �������============="+num);
                                        }
                                        //CCEnd by liunan 2011-08-01
                                        //����汾һ������Ƚ���������״̬������Ϊ��׼�����Ϊ��׼���ҽ�ŵġ��汾ע�͡����Ժ��С�ǰ׼���������򷢲�����ʵ�ʱ���ʱֻ��״̬�Ͳ��á�����֪ͨ�������޸ġ�
                                        //CCBegin by liunan 2011-04-22 ǰ׼�ж���>�ĳ�>=
                                        else if(sourceVersion.equals(fbsourversion)&&partState.equals("PREPARING")&&partnote!=null&&partnote.indexOf("ǰ׼")>=0&&qylifecyclestate.equals("PREPARE"))
                                        {
                                        }
                                        //CCBegin SS9
                                        //����汾һ������Ƚ���������״̬������Ϊ���ƣ����Ϊ����׼�ҷ�ǰ׼���������򷢲�����ʵ�ʱ���ʱֻ��״̬�Ͳ��á�����֪ͨ�������޸ġ�
                                        else if(sourceVersion.equals(fbsourversion)&&!partState.equals("PREPARING")&&!partState.equals("ADVANCEPREPARE")&&!partState.equals("MANUFACTURING")&&!partState.equals("SHIZHI")&&qylifecyclestate.indexOf("TEST")!=-1)
                                        {
                                        }
                                        //CCEnd SS9
                                        //CCBegin by liunan 2010-05-19
                                        //���fbsourversionΪ""��˵���ü���ϵͳ�д��ڣ��ǽ���û��ֹ������ġ�ֱ������
                                        else if(fbsourversion.equals(""))
                                        {
                                        	//CCBegin SS7
                                        	//isReviseOrUp = false;
                                        	if(sourceVersion.startsWith("A"))
                                        	{
                                        		isReviseOrUp = false;
                                        	}
                                        	//CCEnd SS7
                                        }
                                        //CCEnd by liunan 2010-05-19
                                        else
                                        {
                                        //CCEnd by liunan 2009-01-21
                                        //CCEnd by liunan 2008-09-02
						try {
							transaction.rollback();
						} catch (QMException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						this.myHelper.partsCannotStore.add(part.getPartNumber());
						log("���� " + num + " ����Ҫ�޶�");
						userLog(serNum + "," + name + "," + num + ","
								+ "�㲿��" + "," + fbsourversion + "," + sourceVersion
								+ "," + receproversion + ","
								+ part.getVersionValue() + ",�ɹ�");
						//CCBegin by liunan 2011-04-22 �����Ѵ��ڵģ���ʾ�ɹ���������partVector����ӣ���ֹ�ṹ���ס�
						//myHelper.partVector.addElement(num);
						//CCEnd by liunan 2011-04-22
						result.successOne();
						myHelper.resucnum++;
						continue;
					}
					//CCBegin by liunan 2008-10-22
					//�ߵ��˴�����ʾ���㲿����Ҫ����������С�Ȼ��Ƚ�������汾�����һ����ʾ�㲿��������С�汾���������޶�������
					if(sourceVersion.indexOf(".")>0&&fbsourversion.indexOf(".")>0)
					{
					  if(sourceVersion.substring(0,sourceVersion.indexOf(".")).equals(fbsourversion.substring(0,fbsourversion.indexOf("."))))
					  {
						  isReviseOrUp = false;
					  }
					  //CCBegin SS10
					  //P.2���P2.1��������з���Դ�汾�����Խ�Ŵ�汾Ϊ��ͷ��ĸ�����ʾ�ɻ���������С�汾������
					  //if(sourceVersion.substring(0,sourceVersion.indexOf(".")).startsWith(fbsourversion.substring(0,fbsourversion.indexOf("."))))
					  if(matchResult(Pattern.compile("[a-z||A-Z]"),sourceVersion).equals(matchResult(Pattern.compile("[a-z||A-Z]"),fbsourversion)))
					  {
						  isReviseOrUp = false;
					  }
					  //CCEnd SS10
				  }
					//CCEnd by liunan 2008-10-22
				}
				String rightString=PublishHelper.getXRightString(part);//��ȡ�޶�ǰ��������Ȩ�ַ���

				//CCBegin by liunan 2008-10-22
				//ͨ��isReviseOrUp�ж��Ƕ��㲿�������޶�����С�汾������
				//����޶�����newVersion();��������С�汾��newIteration()��
                                //System.out.println("begin version is ============="+part.getVersionValue());

        WorkInProgressService wService = (WorkInProgressService) PublishHelper
					.getEJBService("WorkInProgressService");
				SessionService sservice = (SessionService) PublishHelper
						.getEJBService("SessionService");
				//System.out.println("*********************revisedelegate********************=="+sservice.isAccessEnforced());
				
				//CCBegin by liunan 2011-06-21 ���fbsourversionΪ""����ʾ����Լ�����part������������޶���ֱ�������롣
				if(fbsourversion.equals("")&&part.getOwner()!=null&&!part.getOwner().equals(""))
				{
					
				}
				//if(isReviseOrUp)
				else if(isReviseOrUp)
				//CCEnd by liunan 2011-06-21
				{
				    part = (QMPartInfo) ((VersionControlService) PublishHelper
						.getEJBService("VersionControlService"))
						     .newVersion((VersionedIfc) part);
				}
				else if(isCheckoutAllowed(part))
				{
            FolderIfc folder = wService.getCheckoutFolder();
            wService.checkout(part, folder, "");
            part = (QMPartIfc) wService.workingCopyOf(part);
					  /*QMPartIfc iterationPartTemp = (QMPartInfo) ((VersionControlService) PublishHelper
						.getEJBService("VersionControlService"))
						     .newIteration((IteratedIfc) part);
						part = (QMPartInfo) ((VersionControlService) PublishHelper
						.getEJBService("VersionControlService"))
						     .supersede((IteratedIfc) part,(IteratedIfc) iterationPartTemp);
						*/
        }
                                //System.out.println("after version is ============="+part.getVersionValue());
				//CCEnd by liunan 2008-10-22
                                //������޶��ķ��������и�newIteration����������С����
                                //�����жϵ�ǰ�汾��ԭ�汾�Ĵ�汾�Ƿ���ͬ��һ��������С�汾����һ�����޶���
				String lifeCyID = PublishHelper.getLifeCyID(lifecycle);
				part.setLifeCycleTemplate(lifeCyID);

		    //CCBegin by liunan 2008-10-28
	    	//���÷����������㲿��״̬�������ǽ����ġ���׼����
	    	//Դ�����£�
				//part.setLifeCycleState(State.toState("PREPARING"));//����״̬����Ҫ�ĳɽ�ŷ�����״̬�ˡ�
				//CCBegin by liunan 2009-12-23 �޸�״̬��ʾ
				part.setLifeCycleState(LifeCycleState.toLifeCycleState(lifecyclestate));
				//CCEnd by liunan 2009-12-23
				//CCEnd by liunan 2008-10-28
				//CCBegin SS1
				viewName = "���������ͼ";
				//CCEnd SS1
				if (viewName != null && PublishHelper.isHasView(viewName)) {
					part.setViewName(viewName);
					
				} else {
					part.setViewName(PublishLoadHelper.DEFAULT_VIEW);
			
				}
	
				if (source == null || source.trim().length() == 0) {
					part.setProducedBy(ProducedBy.getProducedByDefault());
				} else {
					source = myHelper.mapPro.getProperty("part.source."
							+ source);
					ProducedBy produce = ProducedBy.toProducedBy(source);
					if (produce == null) {
						part.setProducedBy(ProducedBy.getProducedByDefault());
					} else {
						part.setProducedBy(produce);
					}
				}
				// ��������
				if (partType == null || partType.trim().length() == 0) {
					part.setPartType(QMPartType.getQMPartTypeDefault());
				} else {

					partType = myHelper.mapPro.getProperty("part.parttype."
							+ partType);
					// partType = PublishHelper.strDecoding(partType,
					// "ISO8859-1");

					QMPartType type1 = QMPartType.toQMPartType(partType);

					if (type1 == null) {

						part.setPartType(QMPartType.getQMPartTypeDefault());
					} else {

						part.setPartType(type1);
					}
				}
				if (myHelper.user != null) {
					part.setIterationCreator(myHelper.user.getBsoID());
					part.setIterationModifier(myHelper.user.getBsoID());
					//CCBegin by liunan 2009-1-21
					//����ǰ׼״̬��������Ҫ��ǡ�
					/*if(qylifecyclestate.equals("ADVANCEPREPARE"))
					{
					  part.setIterationNote("ǰ׼");
					}*/
					//CCEnd by liunan 2011-04-26
					//CCEnd by liunan 2009-1-21
					//CCBegin by liunan 2008-10-08
					//�޸��㲿������ʱ����owner�ֶε����⡣ע�͵����´��롣
					//part.setOwner(myHelper.user.getBsoID());
					part.setOwner("");
					//CCEnd by liunan 2008-10-08
				}
				
				//CCBegin by liunan 2010-01-20 Ϊ�㲿�����logicbase֮ǰɾ������Contents���ݡ�
				//��Ϊ�㲿����Contents����logicbase�͸��������������logicbase֮ǰ������ա�
				//Ȼ���ں����������logicbase���������µĸ�����
				//Ŀǰ��Ϊ�㲿����Contents����ֻ��logicbase���߸�����
				try 
				{
					ContentService cs = (ContentService) PublishHelper.getEJBService("ContentService");
					Vector v = cs.getContents(part);
					if(v!=null&&v.size()>0)
					{
						System.out.println(num+" ��Contents�к��� "+v.size()+" �����ݡ�");
						for (int i = 0; i < v.size(); i++)
						{
							ApplicationDataInfo application = (ApplicationDataInfo) v.get(i);
							cs.deleteApplicationData((ContentHolderIfc)part, application);
						}
				  }
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
				//CCEnd by liunan 2010-01-20
				

				// �����㲿��
				part.setAttributeContainer(new DefaultAttributeContainer());
				
				//CCBegin by liunan 2011-06-21 ���fbsourversionΪ""����ʾ����Լ�����part������������޶���ֱ�������롣
				if(fbsourversion.equals("")&&part.getOwner()!=null&&!part.getOwner().equals(""))
				{
            part = (QMPartIfc) wService.checkin(part, "");
            //CCBegin by liunan 2009-12-23 �޸�״̬��ʾ��ʽ��
            part.setLifeCycleState(LifeCycleState.toLifeCycleState(lifecyclestate));
            //CCEnd by liunan 2009-12-23
            part = (QMPartIfc) ps.saveValueInfo(part);
				}
				//if(isReviseOrUp)
				else if(isReviseOrUp)
				//CCEnd by liunan 2011-06-21
				{
					  part = (QMPartInfo) ps.saveValueInfo(part);
			  }
				else
        {
            part = (QMPartIfc) wService.checkin(part, "");
            //CCBegin by liunan 2009-12-23 �޸�״̬��ʾ��ʽ��
            part.setLifeCycleState(LifeCycleState.toLifeCycleState(lifecyclestate));
            //CCEnd by liunan 2009-12-23
            part = (QMPartIfc) ps.saveValueInfo(part);
        }
//

				//CCBegin SS8
				part.setIterationNote("");
				//CCEnd SS8
				
				//CCBegin by liunan 2011-10-27 �޶�Ҳ�ܸ����ϼС�
				//PublishHelper.assignFolder(part, location);
				String oldpath = part.getLocation();
				System.out.println("PartReviseDelegate "+num+" location:"+location);
				part = (QMPartInfo)PublishHelper.assignFolder(part, location);
				System.out.println("PartReviseDelegate  part location:"+part.getLocation());
				if(!oldpath.equals(part.getLocation()))
				{
					part = (QMPartIfc) ps.saveValueInfo(part);
				}
				//CCEnd by liunan 2011-10-27
				
				//CCBegin SS6
				changeVersion cha = new changeVersion();
				cha.updatePartView(part);
				// System.out.println("viewName8="+part.getViewName());
				//CCEnd SS6
				// ������Ƹı������������㲿��
				if (!part.getPartName().equals(name)) {
					QMPartMasterInfo master = (QMPartMasterInfo) part
							.getMaster();
					master.setPartName(name);
					partSer.renamePartMaster(master, false);
				}

				Collection links = PublishHelper.getPartUsageLinks(part);
				for (Iterator oldlinks = links.iterator(); oldlinks.hasNext();) {
					PartUsageLinkInfo link = (PartUsageLinkInfo) oldlinks
							.next();
					ps.deleteValueInfo(link);
				}
				links = null;
				part = (QMPartInfo) updateIBA((IBAHolderIfc) part, sourceProps,
						(Vector) myPartIBAMap.get(num));
				part = deleteOldStruct(part);
				// ɾ���ĵ�����
				part = deleteOldDocAssociate(part);
				//���ӽ��ĵ�Ȩ�޴���
				
				//CCBegin SS1				
				try 
				{
					ContentService cs = (ContentService) PublishHelper.getEJBService("ContentService");
					Vector v = cs.getContents(part);
					if(v!=null&&v.size()>0)
					{
						System.out.println(num+" ��Contents�к��� "+v.size()+" �����ݡ�");
						for (int i = 0; i < v.size(); i++)
						{
							ApplicationDataInfo application = (ApplicationDataInfo) v.get(i);
							//���pdf���жϡ�
							if(application.getFileName().endsWith(".pdf")||application.getFileName().endsWith(".edz"))
							{
								cs.deleteApplicationData((ContentHolderIfc)part, application);
							}
						}
				  }
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}
				//CCEnd SS1
				
				
				if(rightString!=null&&rightString.length()>0)
				{
					AdHocAcl acl = AdHocAcl.newAdHocAcl();
					acl.setEntrySet(new AclEntrySet(rightString));
					part.setAdHocAcl(acl);
					//CCBegin by liunan 2010-01-31 �޸�updateBso����ʱ�����޸�ʱ�����
					//��ԭ�������������ĳ����������ķ���
					part=(QMPartIfc) ((PersistService) PublishHelper
							.getEJBService("PersistService")).updateBso(part, false, false)
							.getValueInfo();
					//CCEnd by liunan 2010-01-31			
					myHelper.partNeedAcl.add(part);
				}
				
				
				if (isgpart) {
					try
					{
					System.out.println(num+" �ǹ��岿������������logicbase������");
					part = (QMPartIfc)ps.refreshInfo(part);
					((GenericPartInfo) part).setMaxCompIdUsed(Long.valueOf(
							maxCompIdUsed).longValue());

					if (lb != null) {
						System.out.println(num+" ��logicbase��Ϊ�գ����Ա��棡����");
						((GenericPartInfo) part).setLogicBase(lb);
						ps.saveValueInfo(part);
						System.out.println(num+" ��logicbase������ϣ�����");
					}
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}

				}
				
				transaction.commit();
				myHelper.partVector.addElement(num);
				result.successOne();
				result.addSuccessMsg(num + "," + part.getName() + ","
						+ sourceVersion + "," + type + ",�ɹ�");
				//CCBegin by liunan 2008-12-11 ������Ϣʱ�����޶�����С�汾������
				//CCBegin by liunan 2009-12-07 �޸Ľ��ͳ�Ƶ����ݣ����Ӹ�������Ϣ��
				String temp = "";
				if(isReviseOrUp)
				{
					//temp = isHasCont.trim().equals("true") ? "�и���" : "�ɹ�" ;
					temp = "�ɹ�";
				}
				else
				{
					//temp = isHasCont.trim().equals("true") ? "С�汾�������и�����" : "С�汾����" ;
					temp = "С�汾����" ;
				}
				if (isgpart) {
					log("�޶����岿�� " + num + " �ɹ�");
                                        if(isReviseOrUp)
					  userLog(serNum + "," + part.getPartName() + "," + num + ","
							+ "�����㲿��" + "," + fbsourversion + ","
							+ sourceVersion + "," + receproversion + ","
							+ part.getVersionValue() + "," + temp);
							//+ part.getVersionValue() + ",�ɹ�");
                                        else
                                          userLog(serNum + "," + part.getPartName() + "," + num + ","
                                                        + "�����㲿��" + "," + fbsourversion + ","
                                                        + sourceVersion + "," + receproversion + ","
                                                        + part.getVersionValue() + "," + temp);
                                                        //+ part.getVersionValue() + ",С�汾����");
				} else {
					log("�޶��㲿�� " + num + " �ɹ�");
                                        if(isReviseOrUp)
					  userLog(serNum + "," + part.getPartName() + "," + num + ","
							+ "�㲿��" + "," + fbsourversion + "," + sourceVersion
							+ "," + receproversion + ","
							+ part.getVersionValue() + "," + temp);
							//+ part.getVersionValue() + ",�ɹ�");
                                        else
                                          userLog(serNum + "," + part.getPartName() + "," + num + ","
                                                        + "�㲿��" + "," + fbsourversion + "," + sourceVersion
                                                        + "," + receproversion + ","
                                                        + part.getVersionValue() + "," + temp);
                                                        //+ part.getVersionValue() + ",С�汾����");
				}
				//CCEnd by liunan 2009-12-07
				//CCEnd by liunan 2008-12-11
				myHelper.resucnum++;
			} catch (QMException ex) {
				ex.printStackTrace();
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.myHelper.partsCannotStore.add(num);
				this.myHelper.failedParts.add(num);
				this.myHelper.logErrorPart(num);// ��δ�ܳɹ��޶����㲿����¼��������־��
				result.failureOne();
				String msg = "at save Part (num=" + num + ") ClientMessage ERROR:"
						+ ex.getClientMessage();
				result.addErroMsg(num + "," + part.getName() + ","
						+ sourceVersion + "," + partType + ",ʧ��");
				errorLog(msg);
				// ex.printStackTrace();
				errorLog(ex);
				
				//CCBegin SS4
				boolean suflag = false;
				//CCEnd SS4
				
				//CCBegin by liunan 2009-10-19 ���Ρ�������������������������쳣
				//���쳣������ͬʱ�����ļ�������д�����ͬ��Ҫ�����Ľṹ������һ���������ˣ�
				//��һ�����޷��޸�ʱ�������쳣��ʵ���Ѿ������ɹ������޸��á��������ݷ������ͳ��.html��
				//�в�����ʾ���쳣��������ʾ�ɹ���
				//�� ���֡��������쳣�����м�¼�����ɹ���������
				if(msg.indexOf("�����������������������")>0)
				{
					myHelper.resucnum++;
				}
				//CCBegin by liunan 2011-10-27 ����������Ϣ�������Ѿ��޶����������°汾��
				if(msg.indexOf("�����Ѿ��޶����������°汾")>0)
				{
					myHelper.resucnum++;
					//CCBegin SS4
					errorLog("num= "+num+" ����Ҫ�޶� ����Ϊ�����Ѿ��޶����������°汾��");
					suflag = true;
					//CCEnd SS4
				}
				//CCEnd by liunan 2011-10-27
				//CCEnd by liunan 2009-10-19
				
				//CCBegin SS2
				if(msg.indexOf("����Ψһ��")>0)
				{
					myHelper.resucnum++;
					//CCBegin SS4
					errorLog("num= "+num+" ����Ҫ�޶� ����Ϊ ����Ψһ�ģ�");
					suflag = true;
					//CCEnd SS4
				}
				//CCEnd SS2
				
				//CCBegin by liunan 2011-06-21 ����쳣�ǡ��ڼ���ö���֮ǰ���뽫����������������ʾ��
				String msg1 = ex.getLocalizedMessage();
				if(msg1!=null&&msg1.startsWith("�ڼ���ö���֮ǰ���뽫����"))
				{
					msg1 = "�ڸ������ϼз���ʧ��";
				}
				if (isgpart) {
					userLog(serNum + "," + part.getPartName() + "," + num + ","
							+ "�����㲿��" + "," + fbsourversion + ","
							+ sourceVersion + "," + receproversion + "," + " "
							//+ ",ʧ��" + "," + ex.getClientMessage());
							//CCBegin SS4
							//+ ",ʧ��" + "," + msg1);
							+ (suflag?",�ɹ�":",ʧ��") + "," + msg1);
							//CCEnd SS4
				} else {
					userLog(serNum + "," + part.getPartName() + "," + num + ","
							+ "�㲿��" + "," + fbsourversion + "," + sourceVersion
							//CCBegin SS4
							//+ "," + receproversion + "," + " " + ",ʧ��" + ","
							+ "," + receproversion + "," + " " + (suflag?",�ɹ�":",ʧ��") + ","
							//CCEnd SS4
							//+ ex.getClientMessage());
							+ msg1);
				}
				//CCEnd by liunan 2011-06-21
			} catch (Exception ex) {
				ex.printStackTrace();
				try {
					transaction.rollback();
				} catch (QMException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				this.myHelper.partsCannotStore.add(num);
				this.myHelper.failedParts.add(num);
				this.myHelper.logErrorPart(num);// ��δ�ܳɹ��޶����㲿����¼��������־��
				result.failureOne();
				String msg = "at save Part (num=" + num + ") LocalizedMessage ERROR:"
						+ ex.getLocalizedMessage();
				result.addErroMsg(num + "," + part.getName() + ","
						+ sourceVersion + "," + partType + ",ʧ��");
				errorLog(msg);
				// ex.printStackTrace();
				errorLog(ex);				
				
				//CCBegin SS4
				boolean suflag = false;
				//CCEnd SS4
				
				//CCBegin SS2
				if(msg.indexOf("����Ψһ��")>0)
				{
					myHelper.resucnum++;
					//CCBegin SS4
					errorLog("num= "+num+" ����Ҫ�޶� ����Ϊ ����Ψһ�ģ�");
					suflag = true;
					//CCEnd SS4
				}
				//CCEnd SS2
				
				
				//CCBegin by liunan 2011-06-21 ����쳣�ǡ��ڼ���ö���֮ǰ���뽫����������������ʾ��
				String msg1 = ex.getLocalizedMessage();
				if(msg1!=null&&msg1.startsWith("�ڼ���ö���֮ǰ���뽫����"))
				{
					msg1 = "�ڸ������ϼз���ʧ��";
				}
				if (isgpart) {
					userLog(serNum + "," + part.getPartName() + "," + num + ","
							+ "�����㲿��" + "," + fbsourversion + ","
							+ sourceVersion + "," + receproversion + "," + " "
							//+ ",ʧ��" + "," + ex.getLocalizedMessage());
							//CCBegin SS4
							//+ ",ʧ��" + "," + msg1);
							+ (suflag?",�ɹ�":",ʧ��") + "," + msg1);
							//CCEnd SS4
				} else {
					userLog(serNum + "," + part.getPartName() + "," + num + ","
							+ "�㲿��" + "," + fbsourversion + "," + sourceVersion
							//CCBegin SS4
							//+ "," + receproversion + "," + " " + ",ʧ��" + ","
							+ "," + receproversion + "," + " " + (suflag?",�ɹ�":",ʧ��") + ","
							//CCEnd SS4
							//+ ex.getLocalizedMessage());
							+ msg1);
				}
				//CCEnd by liunan 2011-06-21
			}
		} // end while
		return result;

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
	private IBAHolderIfc updateIBA(IBAHolderIfc holder, Properties props,
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
		container.setConstraintGroups(new Vector());
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
		if (myHelper.saveSourceInfoByIBA) {
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

	private QMPartIfc deleteOldStruct(QMPartIfc parent) throws Exception {

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

	private QMPartIfc deleteOldDocAssociate(QMPartIfc part) throws Exception {
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

        /**
         * �Ƚ������汾�Ĵ�С�������Ƿ񷢲��˶���
         * @param s1 String �������°汾��
         * @param s2 String ���pdm�м�¼�ĸö����Ӧ���еİ汾��
         * @return boolean ��Ҫ����ʱ����true�����򷵻�false��
         * @author liunan 2008-10-04
         */
        private boolean getPublishFlag(String s1, String s2)
        {
        	//CCBegin by liunan 2011-03-15 ��������û��Դ�汾��˵���ǽ���Լ��ֹ������ģ������ķ���false���Ժ󰴸��¼�����봦��
        	if(s2.equals(""))
        	{
        		return false;
        	}
        	//CCEnd by liunan 2011-03-15
          if(s1.indexOf(".")<0)
          {
            s1 = s1 + ".1";
          }
          if (s1.equals(s2)) {
            return false;
          }

          //����StringTokenizer�ָ�st1
          StringTokenizer st1 = new StringTokenizer(s1, ".");
          //����StringTokenizer�ָ�st2
          StringTokenizer st2 = new StringTokenizer(s2, ".");
          int level1 = st1.countTokens();
          int level2 = st2.countTokens();
          //�����汾ֵ���ȴ�Ϊ���µ�
          if (level1 < level2) {
            return false;
          }
          if (level1 > level2) {
            return true;
          }
          String[] sarray1 = new String[level1];
          String[] sarray2 = new String[level2];
          //�ѷָ����ִ��������ַ�������
          for (int i = 0; i < level1; i++) {
            sarray1[i] = st1.nextToken();
            sarray2[i] = st2.nextToken();
          }
          //��ѭ���бȽ������е��ִ�
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

    /**
     * �ж��Ƿ����������
     * @param workable ���������
     * @return ��ʶ��
     * @throws LockException
     * @throws QMException
     * @author liunan 2008-11-17
     */
    private static boolean isCheckoutAllowed(WorkableIfc workable)
            throws LockException, QMException
    {
        boolean flag = false;
        //����ļ��з���
        FolderService folderService = (FolderService) PublishHelper
					.getEJBService("FolderService");
        if(!WorkInProgressHelper.isWorkingCopy(workable))
        {
            flag = !WorkInProgressHelper.isCheckedOut(workable)
                    && !LockHelper.isLocked(workable)
                    && !folderService.inPersonalFolder(workable);
        }
        return flag;
    }
    
    //CCBegin SS10
    public static String matchResult(Pattern p,String str)
    {
    	StringBuilder sb = new StringBuilder();
    	Matcher m = p.matcher(str);
    	while (m.find())
    	{
    		for (int i = 0; i <= m.groupCount(); i++)
    		{
    			sb.append(m.group());
    		}
    	}
    	return sb.toString();
    } 
    //CCEnd SS10
}
