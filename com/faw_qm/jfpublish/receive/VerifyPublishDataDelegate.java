package com.faw_qm.jfpublish.receive;
//SS1 �㲿�������ͬ���������part��gpart�⣬���ڻ����˱��������ͬ��������qmpartmasterbsoid��ͬ����(BSXUP)��׺�� liunan 2013-7-24
//SS2 �������ķ��㲿�����������������ͼ ������ 2014-7-8
//SS3 ��������״̬�㲿���� liunan 2017-7-6
//SS4 �ɶ��ص������ǹ�����ͼ����Ϊ������ͼ���ң������ҵ�������ͼ�ļ��������°汾�޶������ liunan 2018-1-11
/**
 * <p>Title: cPDM��������</p>
 * <p>Description: cPDM��������</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: FAW_QM</p>
 * @author whj
 * @version 1.0
 */

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
//CCBegin by liunan 2008-09-02
import java.util.StringTokenizer;
//CCEnd by liunan 2008-09-02

import com.faw_qm.adapter.BaseCommandDelegate;
import com.faw_qm.affixattr.ejb.service.AffixAttrService;
import com.faw_qm.affixattr.model.AttrDefineInfo;
import com.faw_qm.affixattr.model.AttrValueInfo;
import com.faw_qm.content.ejb.service.ContentService;
import com.faw_qm.content.model.ApplicationDataInfo;
import com.faw_qm.content.model.ContentItemInfo;
import com.faw_qm.doc.ejb.service.StandardDocService;
import com.faw_qm.doc.model.DocIfc;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.doc.model.DocMasterIfc;
import com.faw_qm.doc.util.DocFormData;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.iba.value.ejb.service.IBAValueObjectsFactory;
import com.faw_qm.iba.value.ejb.service.IBAValueService;
import com.faw_qm.iba.value.litevalue.AbstractValueView;
import com.faw_qm.iba.value.model.AbstractValueIfc;
import com.faw_qm.iba.value.model.StringValueIfc;
import com.faw_qm.iba.value.util.DefaultAttributeContainer;
import com.faw_qm.integration.model.Att;
import com.faw_qm.integration.model.Command;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.integration.model.Script;
import com.faw_qm.lifecycle.ejb.service.LifeCycleService;
import com.faw_qm.lifecycle.model.LifeCycleTemplateInfo;
import com.faw_qm.lifecycle.util.LifeCycleState;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.persist.util.QMQuery;
import com.faw_qm.persist.util.QueryCondition;
import com.faw_qm.session.ejb.service.SessionService;
import com.faw_qm.users.model.UserIfc;
import com.faw_qm.util.EJBServiceHelper;
import com.faw_qm.version.ejb.service.VersionControlService;

public class VerifyPublishDataDelegate extends BaseCommandDelegate {

	// private String notenumber = null;

	public VerifyPublishDataDelegate() {
	}

	public Script _invoke(Script script) throws QMException {

		Command cmd = script.getCommand();
		SessionService sservice = (SessionService) getEJBService("SessionService");
		UserIfc user = sservice.getCurUserInfo();
		// Ϊ��ǰ�û����ù���ԱȨ��
		sservice.setAdministrator();
		String notenumber = cmd.paramValue("NOTENUMBER");
		// ���������
		Group groupIn = script.getGroupIn(cmd.getGroupInName());
		// ��������
		Group groupOut = script.getGroupOut(cmd.getGroupOutName());
		if (groupOut == null) {
			groupOut = new Group();
			groupOut.setName(cmd.getGroupOutName());
		}
		// System.out.println("���յ���������:" + cmd.getName());
		String type = cmd.paramValue("BSONAME");
		if (type.toUpperCase().trim().equals("DOC")) {
			groupOut = getNotExistDoc(groupIn, groupOut);
		}
		if (type.toUpperCase().trim().equals("PART")) {
			log("���ݷ�����ʼ����������ǣ�" + notenumber);
			// groupIn.toXML(new PrintWriter(System.out), true);
			groupOut = getPublishParts(groupIn, groupOut);
			// 2006-06-16��ӣ���û�������Ҫ����ʱ��Ҳ������־�ĵ�
			if (groupOut.getElementCount() == 0) {
				if (PublishHelper.VERBOSE)
					System.out.println("��������ǣ�" + notenumber + ",û��Ҫ�������㲿��������");
				// createLogDoc(notenumber);
			}
		}
		//CCBegin by liunan 2010-03-31
		if (type.toUpperCase().trim().equals("CONNECT")) {
			System.out.println("%%%%%%%%%%%%%%%%%���Ӳ���%%%%%%%%%%%%%%%%%");
			groupOut = new Group();
		}
		//CCEnd by liunan 2010-03-31
		sservice.freeAdministrator();
		Script response = new Script();
		response.addVdb(groupOut);
		// String s = response.get236

		// Group outGrp = response.getGroupOut();
		// outGrp.toXML(new PrintWriter(System.out), true);
		return response;
	}

	/**
	 * ������־�ĵ���˵������û��������Ҫ����
	 *
	 * @param notenumber
	 * @throws QMException
	 */
	private void createLogDoc(String notenumber) throws QMException {
		if (PublishHelper.VERBOSE)
			System.out.println("=====>createLogDoc,notenumber: " + notenumber);
		if (notenumber == null || notenumber.equals("")) {
			return;
		}
		PersistService ps = (PersistService) getEJBService("PersistService");
		StandardDocService docServ = (StandardDocService) getEJBService("StandardDocService");
		SessionService sservice = (SessionService) getEJBService("SessionService");
		DocInfo docInfo = PublishHelper.getDocInfoByNumber(notenumber);
		String docCfBsoID = PublishHelper.getDocCf("������\\���ݷ���������־");
		DocFormData formData = new DocFormData();
		if (docInfo == null) {
			formData.setDocumentAttribute("docName", notenumber);
			// �����ĵ����
			formData.setDocumentAttribute("docNum", notenumber);
			// �������ݼ���
			formData.setDocumentAttribute("contDesc", "�˲���֪ͨ�����ز�����ϵͳ���Ѿ����ڣ�");
			// ���ô�����λ
			formData.setDocumentAttribute("createDept", "��������");
			// �����ĵ����
			formData.setDocumentAttribute("docCfBsoID", docCfBsoID);
			// �������ϼ�
			formData.setDocumentAttribute("folder", "\\Root\\���ݽ�������\\���ݽ���ȷ��֪ͨ��");
			// ������������
			formData.setDocumentAttribute("lifecycleTemplate", "ȱʡ");
			UserIfc user = sservice.getCurUserInfo();
			if (user != null) {
				formData.setDocumentAttribute("iterationCreator", user
						.getBsoID());
				formData.setDocumentAttribute("iterationModifier", user
						.getBsoID());
				formData.setDocumentAttribute("aclOwner", user.getBsoID());
				formData.setDocumentAttribute("creator", user.getBsoID());
			}

      //CCBegin by liunan 2008-08-07
      //�ְ汾doc�������Ѿ�����createDoc�����������ص���vector���м䴦�����һ�£�
      //�˴�ֱ�ӵ������°汾����Ȼ��õ�docInfo��
      //ԭ��������
			//docInfo = (DocInfo) docServ.createDoc(formData);
			Vector docvec = (Vector) docServ.createDoc(formData);
			docInfo = (DocInfo)docvec.elementAt(0);
			//CCBegin by liunan 2008-08-07

			docInfo = (DocInfo) ps.refreshInfo(docInfo);
		} else {
			ContentService cService = (ContentService) getEJBService("ContentService");
			ContentItemInfo item = (ContentItemInfo) cService
					.getPrimaryContent(docInfo);
			if (item != null && item instanceof ApplicationDataInfo) {
				cService.deleteApplicationData(docInfo,
						(ApplicationDataInfo) item);
			}
			// formData.setDocumentAttribute("lifeCState",State.toState("RECEIVING"));
			// �������ݼ���
			// formData.setDocumentAttribute("contDesc",
			// "�˲���֪ͨ�����ز�����ϵͳ���Ѿ����ڣ�");
			LifeCycleService lifecycleService = (LifeCycleService) EJBServiceHelper
					.getService("LifeCycleService");
			LifeCycleTemplateInfo info = lifecycleService
					.getLifeCycleTemplate("ȱʡ");
			docInfo.setLifeCycleTemplate(info.getBsoID());
			docInfo.setContDesc("�˲���֪ͨ�����ز�����ϵͳ���Ѿ����ڣ�");
			docInfo.setLifeCycleState(LifeCycleState.toLifeCycleState("QIYENEIKONG"));
			docInfo = (DocInfo) docServ.reviseDoc(docInfo, formData);
		}
	}

	private Group getNotExistDoc(Group groupIn, Group groupOut)

	{
		if (PublishHelper.VERBOSE)
			System.out.println("===============>>>�����ĵ���֤");
		log("�ĵ���֤�����");
		if (groupIn == null) {
			log("������ĵ�����Ϊ�գ�û����Ҫ��֤���ĵ���");
			return groupOut;
		}
		Enumeration enu = groupIn.getElements();
		Element ele = null;
		String numBeforeTrim = null;
		String numAfterTrim = null; // Ϊ���wichill�˸����㲿�����ĵ�����д��ڿո�������ڽ��ܶ��������ͱ���ʱҪ��ȥ���ո���֤ʱ���ظ����Ͷ˵�Ҫ�����ո�
		String version = null;
		StringBuffer resultLogBuff = new StringBuffer();// ��¼��֤���
		int creatNum = 0;
		int reviseNum = 0;
		try {
			PersistService pservice = (PersistService) getEJBService("PersistService");
			AffixAttrService affService = (AffixAttrService) getEJBService("AffixAttrService");
			StandardDocService docService = (StandardDocService) getEJBService("StandardDocService");
			while (enu.hasMoreElements()) {

				ele = (Element) enu.nextElement();
				numBeforeTrim = (String) ele.getValue("num");
				numAfterTrim = numBeforeTrim.trim();
				version = (String) ele.getValue("version");
				QMQuery query = new QMQuery("DocMaster");
				query.addCondition(new QueryCondition("docNum", "=",
						numAfterTrim));
				Collection result = pservice.findValueInfo(query);
				if (result == null || result.size() == 0) {
					Element elem = new Element();
					elem.addAtt(new Att("create", numBeforeTrim));
					groupOut.addElement(elem);
					resultLogBuff.append(numBeforeTrim + "(create),");
					creatNum++;
				} else {
					// ϵͳ�д�����ͬ��ŵ��ĵ�����ȡ���ĵ�����
					DocMasterIfc docMaster = (DocMasterIfc) result.iterator()
							.next();
					if (PublishHelper.VERBOSE)
						System.out.println("------------------------------->"
								+ docMaster.getDocNum());
					VersionControlService ser = (VersionControlService) getEJBService("VersionControlService");
					Collection coll = (Collection) ser
							.allVersionsOf((DocMasterIfc) docMaster);

					Iterator iterator = coll.iterator();
					DocIfc doc = null;
					if (iterator.hasNext()) {
						doc = (DocIfc) iterator.next();
						// �õ��ĵ������и�������ֵ���󼯺�
					}
					Vector attvalues = affService.getAffixAttr(doc);
					// ���帽������ֵ����
					AttrValueInfo attvalue = null;
					// ���帽�����Զ���ֵ����
					AttrDefineInfo attDefine = null;
					int i = attvalues.size();
					boolean flag = false;
					for (int j = 0; j < i; j++) {
						attvalue = (AttrValueInfo) attvalues.elementAt(j);
						// ͨ����������ֵ�Ķ���ȡ�����Զ����ID��ˢ��ֵ����
						attDefine = (AttrDefineInfo) pservice.refreshInfo(
								attvalue.getAttrDefID(), false);
						// ������Զ�������Ƶ���"version"��ȡ����������ֵ����յ����ݱȽ�
						if (attDefine.getAttrName().toLowerCase().trim()
								.equals("sourceversion")) {
							flag = true;
							String verValue = attvalue.getValue();

                                                        //CCBegin by liunan 2008-09-02
                                                        //�޸���ǰ�İ汾�ȽϷ������޷���ȷ�Ƚϳ�AA��B��C��D�Ȱ汾�Ĵ�С��
                                                        //���ò�Ʒ�а汾������õİ汾�ȽϷ�����
							//if (version.compareTo(verValue) > 0) {
                              //System.out.println(version+"==============getNotExistDoc()============="+verValue);
                                                        if(getPublishFlag(version,verValue)){
                                                        //CCEnd by liunan 2008-09-02
								Element redocele = new Element();
								redocele
										.addAtt(new Att("revise", numBeforeTrim));
								groupOut.addElement(redocele);
								resultLogBuff.append(numBeforeTrim
										+ "(revise),");
								reviseNum++;
							} else {
								resultLogBuff.append(numBeforeTrim
										+ "(notPublish),");
							}
						}
					}
					// �������������û��version��Ϣ��������ͬ���ĵ����ڣ��޶����ĵ�
					if (!flag) {
						Element redocele = new Element();
						redocele.addAtt(new Att("revise", numBeforeTrim));
						groupOut.addElement(redocele);
						resultLogBuff.append(numBeforeTrim + "(revise),");
						reviseNum++;
					}
				}
			}
		} catch (QMException e) {
			String msg = "��ѯ���ʧ�ܣ�����������";
			errorLog(msg);
			errorLog(e);
		}
		log(resultLogBuff);
		log("�ĵ���֤���ͳ��, ����:" + groupIn.getElementCount() + ",��Ҫ�������ĵ�����:"
				+ creatNum + ",��Ҫ�޶����ĵ�����:" + reviseNum);
		return groupOut;
	}

	private Group getPublishParts(Group groupIn, Group groupOut)

	{
		if (PublishHelper.VERBOSE)
			System.out.println("===============>>>�����㲿����֤");
		log("�㲿����֤�����");
		if (groupIn == null) {
			log("������ĵ�����Ϊ�գ�û����Ҫ��֤���ĵ���");
			return groupOut;
		}
		Enumeration enumer = groupIn.getElements();
		Element ele = null;
		String numBeforeTrim = null; // Ϊ���wichill�˸����㲿�����ĵ�����д��ڿո�������ڽ��ܶ��������ͱ���ʱҪ��ȥ���ո���֤ʱ���ظ����Ͷ˵�Ҫ�����ո�
		String numAfterTrim = null;
		String version = null;
		String classType = null;
		//CCBegin by liunan 2009-01-21 �����㲿������������״̬�������޸�֮ǰȫ״̬����ʱδ��׼״̬���㲿����Ϣ��֤��
		String lifecyclestate = null;
		//CCEnd by liunan 2009-01-21
		//CCBegin by liunan 2009-11-08 ���㲿������Ų��ѭ���ڣ���ֹ����õ���һѭ���е�ֵ��
		//QMPartInfo part = null;
		//QMPartMasterIfc master = null;
		//CCEnd by liunan 2009-11-08
		StringBuffer resultLogBuffer = new StringBuffer();
		int createNum = 0;
		int reviseNum = 0;

		IBAValueService ibaService = null;
		try {
			ibaService = (IBAValueService) getEJBService("IBAValueService");
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PersistService pservice = null;
		try {
			pservice = (PersistService) getEJBService("PersistService");
		} catch (QMException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while (enumer.hasMoreElements()) {
			//CCBegin by liunan 2009-11-08 �㲿������Ų��ѭ����
			QMPartInfo part = null;
			QMPartMasterIfc master = null;
			//CCEnd by liunan 2009-11-08
			ele = (Element) enumer.nextElement();
			numBeforeTrim = (String) ele.getValue("num");
			numAfterTrim = numBeforeTrim.trim();
			version = (String) ele.getValue("version");
			classType = (String) ele.getValue("classtype");
			//CCBegin by liunan 2009-01-21
			lifecyclestate = (String) ele.getValue("lifecyclestate");
			//CCEnd by liunan 2009-01-21
			try {
				log("��֤�㲿����" + numBeforeTrim);
				// notenumber=(String)ele.getValue("notenumber");
				QMQuery query = new QMQuery("QMPartMaster");
				query.setChildQuery(true);
				query.addCondition(new QueryCondition("partNumber", "=",
						numAfterTrim));
				Collection result = pservice.findValueInfo(query);
				if (result.isEmpty() || result.size() == 0) {
					Element elem = new Element();
					elem.addAtt(new Att("create", numBeforeTrim));
					groupOut.addElement(elem);
					resultLogBuffer.append(numBeforeTrim + "(create),");
					createNum++;
				} else {
					DefaultAttributeContainer attc = null;
					AbstractValueView[] valueview = null;
					AbstractValueIfc value = null;
					int m = 0;
					//CCBegin by liunan 2010-05-04 �������2��ͬ��ŵ�����Ϣ��˵�������ŵ�part��gpart���С�
					//��ʱֻȡgpart��Ŀǰ���ݷ�����Ϊ����ͬ��ŵ�part��gpart������ʱ���ٷ�����һ����gpart��
					//Դ����
					//master = (QMPartMasterInfo) result.iterator().next();
					//CCBegin SS1
					//if(result.size()==2)
					if(result.size()>=2)
					{
						//System.out.println("in getPublishParts ������ͬ��ŵ�part��gpart������");
						//System.out.println("in getPublishParts ������ͬ��ŵ�part��gpart �� ������part������");
						QMPartMasterIfc mastertemp = null;
						QMPartMasterIfc qmparttemp = null;
					//CCEnd SS1
						Iterator iterator = result.iterator();
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
							master = (QMPartMasterInfo) result.iterator().next();
						}
						//CCEnd by liunan 2010-05-20
					}
					else
					{
						master = (QMPartMasterInfo) result.iterator().next();
					}
					//CCEnd by liunan 2010-05-04
					
					try {
						VersionControlService ser = (VersionControlService) EJBServiceHelper
								.getService("VersionControlService");
						Collection coll = (Collection) ser
								.allVersionsOf(master);
						Iterator iterator = coll.iterator();
						//ȡ�����µĹ�����ͼ�汾
						while (iterator.hasNext()) {
							QMPartInfo aPart = (QMPartInfo) iterator.next();
							//CCBegin SS2 SS4
							if (aPart.getViewName() != null
									&& (aPart.getViewName().trim().equals("������ͼ")||aPart.getViewName().trim().equals("���������ͼ"))) {
//							if (aPart.getViewName() != null
//									&& aPart.getViewName().trim().equals("���������ͼ")) {
								//CCEnd SS2 SS4
								part=aPart;
								break;
							}
						}
						//���û�й�����ͼ����ȡ���µ�������ͼ
						if(part==null)
						{
							part=(QMPartInfo) coll.iterator().next();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if (((part.getBsoName().toLowerCase()).equals("qmpart") && classType
							.toLowerCase().equals("genericpart"))
							|| (part.getBsoName().toLowerCase().equals(
									"genericpart") && classType.toLowerCase()
									.equals("wtpart"))) {
						String msg = "";
						//CCBegin by liunan 2010-05-04 ���ϵͳ�д���part�����˴η�������gpart����create��gpart��
						//Դ����
						/*if (classType.toLowerCase().trim().equals("part")
								|| classType.toLowerCase().trim().equals(
										"wtpart")) {
							msg = "�㲿��" + numAfterTrim
									+ "��Ŀ��ϵͳ�д�����ͬ��ŵĹ��岿������Ϣ��ͻ������ʧ�ܣ�";
						} else {
							msg = "���岿��" + numAfterTrim
									+ "��Ŀ��ϵͳ�д�����ͬ��ŵ��㲿������Ϣ��ͻ������ʧ�ܣ�";
						}
						errorLog(msg);
						groupOut = null;
						return groupOut;*/
						if (classType.toLowerCase().trim().equals("part")
								|| classType.toLowerCase().trim().equals(
										"wtpart")) 
						{
							msg = "�㲿��" + numAfterTrim
									+ "��Ŀ��ϵͳ�д�����ͬ��ŵĹ��岿������Ϣ��ͻ������ʧ�ܣ�";
							errorLog(msg);
							groupOut = null;
							return groupOut;
						} 
						else 
						{
							Element elem = new Element();
							elem.addAtt(new Att("create", numBeforeTrim));
							groupOut.addElement(elem);
							resultLogBuffer.append(numBeforeTrim + "(create),");
							createNum++;
							continue;
						}
						//CCEnd by liunan 2010-05-04
						
					}

                                        //CCBegin by liunan 2009-01-21
                                        String partState = part.getLifeCycleState().toString();
                                        String partnote = part.getIterationNote();
                                        //CCEnd by liunan 2009-01-21

					part = (QMPartInfo) ibaService
							.refreshAttributeContainerWithoutConstraints(part);
					attc = (DefaultAttributeContainer) part
							.getAttributeContainer();
					valueview = attc.getAttributeValues();
					m = valueview.length;
					boolean flag = false;
					boolean note1 = false;
					for (int i = 0; i < m; i++) {
						if ((valueview[i].getDefinition().getName())
								.toLowerCase().trim().equals("sourceversion")) {
							flag = true;
							value = IBAValueObjectsFactory.newAttributeValue(
									valueview[i], part);
							String s = ((StringValueIfc) value).getValue();
							//log("�㲿�����ԣ�" + numBeforeTrim + " , " + version + " , " + classType + " , " + lifecyclestate + " , " + partState + " , " + partnote + " , " + s);
							//CCBegin by liunan 2008-09-02
							//�޸���ǰ�İ汾�ȽϷ������޷���ȷ�Ƚϳ�AA��B��C��D�Ȱ汾�Ĵ�С��
							//���ò�Ʒ�а汾������õİ汾�ȽϷ�����
							//if (version.compareTo(s) > 0) {
							//System.out.println(version+"=============="+part.getPartNumber()+"============="+s);
							if(getPublishFlag(version,s)){
								//CCEnd by liunan 2008-09-02
								Element ele1 = new Element();
								ele1.addAtt(new Att("revise", numBeforeTrim));
								groupOut.addElement(ele1);
								resultLogBuffer.append(numBeforeTrim + "(revise),");
								reviseNum++;
							}
							//CCBegin by liunan 2009-01-21
							//����汾һ������Ƚ���������״̬������Ϊǰ׼�����Ϊ����׼�ҷ�ǰ׼���򷢲�����ʵ�ʱ���ʱֻ��״̬�Ͳ��á�����֪ͨ�������޸ġ�							
							//CCBegin by liunan 2012-05-14 ��������״̬���ٰ���׼״̬�������������⡣
							//else if(version.equals(s)&&!partState.equals("PREPARING")&&lifecyclestate.equals("PREPARE"))
							else if(version.equals(s)&&!partState.equals("PREPARING")&&!partState.equals("MANUFACTURING")&&lifecyclestate.equals("PREPARE"))
							//CCEnd by liunan 2012-05-14
							{
								Element ele1 = new Element();
								ele1.addAtt(new Att("revise", numBeforeTrim));
								groupOut.addElement(ele1);
								resultLogBuffer.append(numBeforeTrim + "(revise),");
								reviseNum++;
							}
							//CCBegin by liunan 2011-08-01
							//����汾һ������Ƚ���������״̬������Ϊǰ׼�����Ϊ����׼�ҷ�ǰ׼���򷢲�����ʵ�ʱ���ʱֻ��״̬�Ͳ��á�����֪ͨ�������޸ġ�							
							else if(version.equals(s)&&!partState.equals("PREPARING")&&!partState.equals("ADVANCEPREPARE")&&lifecyclestate.equals("ADVANCEPREPARE"))
							{
								Element ele1 = new Element();
								ele1.addAtt(new Att("revise", numBeforeTrim));
								groupOut.addElement(ele1);
								resultLogBuffer.append(numBeforeTrim + "(revise),");
								reviseNum++;
							}
							//CCEnd by liunan 2011-08-01
							//����汾һ������Ƚ���������״̬������Ϊ��׼�����Ϊ��׼���ҽ�ŵġ��汾ע�͡����Ժ��С�ǰ׼���������򷢲�����ʵ�ʱ���ʱֻ��״̬�Ͳ��á�����֪ͨ�������޸ġ�
							//CCBegin by liunan 2011-04-22 ǰ׼�ж���>�ĳ�>=
							else if(version.equals(s)&&partState.equals("PREPARING")&&partnote!=null&&partnote.indexOf("ǰ׼")>=0&&lifecyclestate.equals("PREPARE"))
							{
								Element ele1 = new Element();
								ele1.addAtt(new Att("revise", numBeforeTrim));
								groupOut.addElement(ele1);
								resultLogBuffer.append(numBeforeTrim + "(revise),");
								reviseNum++;
							}
							//CCEnd by liunan 2009-01-21
							//CCBegin SS3
							//����汾һ������Ƚ���������״̬������Ϊ���ƣ����Ϊ����׼�ҷ�ǰ׼���������򷢲�����ʵ�ʱ���ʱֻ��״̬�Ͳ��á�����֪ͨ�������޸ġ�							
							else if(version.equals(s)&&!partState.equals("PREPARING")&&!partState.equals("ADVANCEPREPARE")&&!partState.equals("MANUFACTURING")&&!partState.equals("SHIZHI")&&lifecyclestate.indexOf("TEST")!=-1)
							{
								System.out.println("anan ��֤Ҫ�������㲿����"+numBeforeTrim);
								Element ele1 = new Element();
								ele1.addAtt(new Att("revise", numBeforeTrim));
								groupOut.addElement(ele1);
								resultLogBuffer.append(numBeforeTrim + "(revise),");
								reviseNum++;
							}
							//CCEnd SS3
							else {
								resultLogBuffer.append(numBeforeTrim
										+ "(notPublish),");
								note1 = true;
							}
						}
					}
					if (!flag) {
						Element ele2 = new Element();
						ele2.addAtt(new Att("revise", numBeforeTrim));
						groupOut.addElement(ele2);
						resultLogBuffer.append(numBeforeTrim + "(revise),");
						reviseNum++;
					}
				}
			} catch (QMException e) {
				String msg = "��ѯ���ʧ��,�㲿����ţ�" + numBeforeTrim;
				errorLog(msg);
				errorLog(e);
			}
		}
		log(resultLogBuffer);
		log("�㲿����֤���ͳ��, ����:" + groupIn.getElementCount() + ",��Ҫ�������㲿������:"
				+ createNum + ",��Ҫ�޶����㲿������:" + reviseNum);
		return groupOut;
	}

	private void log(Object msg) {

		PublishPartsLog.log(msg);
	}

	private void errorLog(Object msg) {
		if (msg instanceof Throwable) {
			PublishPartsLog.log((Throwable) msg);
		} else {
			PublishPartsLog.log("*****ERROR: " + msg);
		}
	}

        /**
         * �Ƚ������汾�Ĵ�С�������Ƿ񷢲��˶���
         * @param s1 String �������°汾��
         * @param s2 String ���pdm�м�¼�ĸö����Ӧ���еİ汾��
         * @return boolean ��Ҫ����ʱ����true�����򷵻�false��
         * @author liunan 2008-09-02
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

}
