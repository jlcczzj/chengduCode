/**
 * SS1 ����ṹ�ظ����⣬���ڲ��ٷ���ȫ�ṹ��Ҳ������Ҫ����¶������⣬����ȡ���Թ����Ƿ���ڵ��жϡ� liunan 2013-9-5
 * SS2 �޸���־��ʾ�� liunan 2014-4-14
 * SS3 �������ķ��㲿�����������������ͼ ������ 2014-7-8
 */

package com.faw_qm.jfpublish.receive;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.part.ejb.standardService.StandardPartService;
import com.faw_qm.part.model.PartDescribeLinkInfo;
import com.faw_qm.part.model.PartUsageLinkInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.part.util.QMQuantity;
import com.faw_qm.part.util.Unit;
import com.faw_qm.pcfg.family.model.GenericPartInfo;
import com.faw_qm.pcfg.family.model.GenericPartUsageLinkInfo;
import com.faw_qm.persist.ejb.service.PersistService;
import com.faw_qm.version.ejb.service.VersionControlService;
//CCBegin by liunan 2008-12-01
import com.faw_qm.part.model.PartAlternateLinkInfo;
import com.faw_qm.part.ejb.entity.PartUsageLink;
import com.faw_qm.part.model.PartSubstituteLinkInfo;
//CCEnd by liunan 2008-12-01

public class RelationsDelegate extends AbstractStoreDelegate {

	public RelationsDelegate(PublishLoadHelper helper) {
		this.myHelper = helper;
	}

	public ResultReport process() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * ���������������㲿���ṹ���°棭ȫ״̬��������޸ģ���
	 * ��Ҫ���ڷ������нṹ������¶��ṹ�ĳ��֡�
	 * ����˼·��
	 * 1�������Ƿ������㲿���������ȫ���½���
	 * 2���������Ƿ������㲿������������ʱ���ȼ������Ƿ���ڣ������½�������
	 * ���崦��
	 * 1�����½��㲿�����޶��㲿��������������ȡ�����η������㲿�����ϡ�
	 * 2����������ʱ��������ڷ����㲿���ļ�������ֱ�Ӵ���������������Ƿ���ڣ������ڲŴ�����
	 * @param links
	 *            Group
	 * @return ResultReport
	 */
	public ResultReport savePartUsageLinks(Group links) throws QMException {
		ResultReport result = new ResultReport();
		userLog("##UsageLink");
		if (links == null || links.getElementCount() == 0) {
			log("û����Ҫ�������㲿���ṹ����");
			return result;
		}
		VersionControlService versiobSer = (VersionControlService) PublishHelper
				.getEJBService("VersionControlService");
		StandardPartService ser = (StandardPartService) PublishHelper
				.getEJBService("StandardPartService");

		Enumeration enumeration = links.getElements();
		Element ele = null;
		String parent = null;
		String parentVersion = null;
		String child = null;
		float quantity = 0.0f;
		String unit = null;
		String id = null;
		QMTransaction transaction = null;
		boolean newcreate = false;
		HashMap linkmap = new HashMap();
		log("�㲿���ṹ������Ϣ:");

		//anan
		System.out.println("���������ϵĸ����ǣ�"+this.myHelper.partVector.size());
		System.out.println("���������ϵ������ǣ�"+this.myHelper.partVector);
				
		while (enumeration.hasMoreElements()) {

			this.myHelper.linkzong++;
			ele = (Element) enumeration.nextElement();
			parent = ((String) ele.getValue("parent")).trim();
			parentVersion = ((String) ele.getValue("parentversion")).trim();
			child = ((String) ele.getValue("child")).trim();
			/*
			 * if (this.myHelper.partsCannotStore.contains(parent) ||
			 * this.myHelper.partsCannotStore.contains(child)) { String msg =
			 * "at save PartUsageLink ,parent=" + parent + ",child=" + child +
			 * ",parnet or child create failed!"; result.failureOne();
			 * errorLog(msg); continue; }
			 */
			// ����Ǵ���ָ���
			if (this.myHelper instanceof PublishErrorProcessor) {
				if (this.myHelper.failedParts.contains(parent)
						|| this.myHelper.failedParts.contains(child)) {
					continue;
				}
			//CCBegin SS2
			//} else if (this.myHelper.partsCannotStore.contains(parent)) {
			}
			if (this.myHelper.partsCannotStore.contains(parent)) {
				//String msg = "at save PartUsageLink ,parent=" + parent
				//		+ ",child=" + child + ",parnet or child create failed!";
				String msg = "at save PartUsageLink ,parent=" + parent + "  is in partsCannotStore!";
				//CCEnd SS2
				result.failureOne();
				errorLog(msg);
				continue;
			}
			id = (String) ele.getValue("id");
			unit = (String) ele.getValue("unit");
			try {
				quantity = Float.valueOf((String) ele.getValue("quantity"))
						.floatValue();
			} catch (Exception ex) {
				ex.printStackTrace();
				quantity = 1.0F;
			}
			try {
				transaction = new QMTransaction();
				transaction.begin();

				QMPartInfo parentPart = PublishHelper.getPartInfoByOrigInfo(
						parent, parentVersion);
				//CCBegin by liunan 2008-12-19 ȥ������ͼ���жϣ���ͼ�ڻ�ø�����ʱ�������жϡ�
				//Դ������:
				/*if (parentPart == null || parentPart.getViewName() == null
						|| !(parentPart.getViewName().trim().equals("������ͼ"))) {*/
				if (parentPart == null || parentPart.getViewName() == null) {
				//CCEnd by liunan 2008-12-19
					String msg = "at save PartUsageLink �㲿��(parent):" + parent
							+ ",ԭ�汾��" + parentVersion + "is null ";
					result.failureOne();
					transaction.rollback();
					errorLog(msg);
					continue;
				}

				QMPartMasterInfo childMaster = null;
				QMPartIfc childPart = PublishHelper.getPartInfoByNumber(child);
				if (childPart == null) {
					String msg = "at save PartUsageLink ���㲿��(child=" + child
							+ ") is null ";
					result.failureOne();
					transaction.rollback();
					errorLog(msg);

					continue;

				}
				childMaster = (QMPartMasterInfo) childPart.getMaster();
				//CCBegin by liunan 2008-12-18 ��ӹ����Ƿ���ڵ��жϡ�
				//��ǰ���ڶ��Ƿ����㲿���Ĺ����ŷ����������������й����Ĺ���������Ҫ�½��ģ�
				//����ȫ״̬�����������Ƿ������й���������Ҫ�жϹ����Ƿ���ڣ�����ǰ�Ĺ¶�������ӵ�ϵͳ�С�
				PersistService ps = (PersistService) PublishHelper.getEJBService("PersistService");
				//CCBegin SS1
				//Collection linkColl = ps.findLinkValueInfo("PartUsageLink", childMaster.getBsoID(), "uses", parentPart.getBsoID());
				//CCEnd SS1
				//CCBegin by liunan 2008-12-22 �жϵ�ǰ�����ĸ����Ƿ�����Ҫ�������㲿�������Ƿ��ڷ����������С�
				//System.out.println("���������ϵĸ����ǣ�"+this.myHelper.partVector.size());
				//System.out.println("���������ϵ������ǣ�"+this.myHelper.partVector);
				if(!this.myHelper.partVector.contains(parent))
				{
				  //CCBegin SS1
				  //if(linkColl != null && linkColl.size() > 0)
				  //CCEnd SS1
				  {
				  	//System.out.println(childMaster.getBsoID()+"==============="+parentPart.getBsoID());
			    	//userLog(parent + "," + child + ",�Ѵ���");
				    log("�㲿�� " + parent + " ���������㲿�� " + child + " ���ӣ��Ľṹ�����Ѵ���");
					  result.successOne();
					  transaction.rollback();
					  continue;
				  }
		  	}
		  	//CCEnd by liunan 2008-12-22
				//CCEnd by liunan 2008-12-18
				PartUsageLinkInfo usageLink = null;
				/*
				 * if (parentPart instanceof GenericPartInfo) { usageLink = new
				 * GenericPartUsageLinkInfo(); } else { usageLink = new
				 * PartUsageLinkInfo(); }
				 */
				if (id != null) {
					usageLink = new GenericPartUsageLinkInfo();
					((GenericPartUsageLinkInfo) usageLink).setId(id);
				} else {
					usageLink = new PartUsageLinkInfo();
				}
				usageLink.setLeftBsoID(childMaster.getBsoID());
				usageLink.setRightBsoID(parentPart.getBsoID());
				QMQuantity quan = new QMQuantity();
				quan.setDefaultUnit(Unit.toUnit(unit) != null ? Unit
						.toUnit(unit) : Unit.getUnitDefault());
				quan.setQuantity(quantity);
				usageLink.setQMQuantity(quan);
				usageLink.setQuantity(quantity);
				// ����Ĭ�ϵ�λ(ʹ�÷�ʽ)
				if (unit == null || unit.trim().length() == 0) {
					usageLink.setDefaultUnit(Unit.getUnitDefault());
				} else {
					// ��ȡ�����ļ������Ĭ�ϵ�λ��ʹ�÷�ʽ����Ӧֵ
					unit = this.myHelper.mapPro.getProperty(
							"part.unit." + unit, null);
					if (unit == null) {
						unit = this.myHelper.mapPro.getProperty(
								"part.unit.default", null);
					}
					Unit unitT = Unit.toUnit(unit);
					if (unitT == null) {
						usageLink.setDefaultUnit(Unit.getUnitDefault());
					} else {

						usageLink.setDefaultUnit(unitT);
					}
				}
				//CCBegin by liunan 2008-12-18
				//PersistService ps = (PersistService) PublishHelper
						//.getEJBService("PersistService");
			  //CCEnd by liunan 2008-12-18
				ps.saveValueInfo(usageLink);
				userLog(parent + "," + child + ",�ɹ�");
				log("�����㲿�� " + parent + " ���������㲿�� " + child + " ���ӣ��Ľṹ�����ɹ�");
				result.successOne();
				transaction.commit();

				this.myHelper.linksuc++;
			} catch (QMException ex) {
				transaction.rollback();
				result.failureOne();
				String msg = "at save PartUsageLink (parent=" + parent
						+ " parentversion=" + parentVersion + " child=" + child
						+ ") ERROR:" + ex.getClientMessage();

				errorLog(msg);
				// ex.printStackTrace();
				errorLog(ex);
				userLog(parent + "," + child + "," + "ʧ��" + ","
						+ ex.getClientMessage());
				continue;
			} catch (Exception ex) {
				transaction.rollback();
				result.failureOne();
				String msg = "at save PartUsageLink (parent=" + parent
						+ " parentversion=" + parentVersion + " child=" + child
						+ ") ERROR:" + ex.getLocalizedMessage();

				errorLog(msg);
				errorLog(ex);
				userLog(parent + "," + child + "," + "ʧ��" + ","
						+ ex.getLocalizedMessage());
				continue;
			}
		}
		return result;
	}

	/**
	 * �����㲿�����ĵ��Ĺ�����ϵ
	 *
	 * @param links
	 *            Group
	 * @param locale
	 *            Locale
	 * @return ResultReport
	 */
	public ResultReport savePartDescribeLinks(Group links) throws QMException {
		userLog("##DescriptionLink");
		ResultReport result = new ResultReport();
		if (links == null || links.getElementCount() == 0) {
			log("û����Ҫ�������㲿��������Ϣ");
			return result;
		}
		StandardPartService ser = null;
		try {
			ser = (StandardPartService) PublishHelper
					.getEJBService("StandardPartService");
		} catch (QMException ex) {
			result.setFailureCount(links.getElementCount());
			String msg = "ERROR:" + ex.getLocalizedMessage();
			errorLog(msg);
			errorLog(ex);
			return result;
		} catch (Exception ex) {
			result.setFailureCount(links.getElementCount());
			String msg = "ERROR:" + ex.getLocalizedMessage();

			errorLog(msg);
			errorLog(ex);
			return result;
		}
		VersionControlService versiobSer = (VersionControlService) PublishHelper
				.getEJBService("VersionControlService");

		Enumeration enumeration = links.getElements();
		Element ele = null;
		String partNum = null;
		String docNum = null;
		QMTransaction transaction = null;
		log("�㲿������������Ϣ:");
		DocInfo doc = null;
		Element node = null;
		while (enumeration.hasMoreElements()) {
			ele = (Element) enumeration.nextElement();
			partNum = ((String) ele.getValue("partNum")).trim();
			docNum = ((String) ele.getValue("docNum")).trim();
			if (this.myHelper instanceof PublishErrorProcessor) {
				if (this.myHelper.failedParts.contains(partNum)
						|| this.myHelper.failedDocs.contains(docNum)) {
					continue;
				}
			} else if (this.myHelper.partsCannotStore.contains(partNum)
					|| this.myHelper.failedDocs.contains(docNum)) {
				continue;
			}
			try {
				transaction = new QMTransaction();
				transaction.begin();

				QMPartMasterIfc partMaster = PublishHelper
						.getPartMasterByNumber(partNum);
				if (partMaster == null) {
					String msg = "at save PartDescribeLink �㲿��(partNum="
							+ partNum + ") is null ";
					errorLog(msg);
					result.failureOne();
					transaction.rollback();
					continue;
				}
				QMPartInfo part = null;
				Collection coll = (Collection) versiobSer
						.allVersionsOf(partMaster);
				// ���master�µ�����С�汾�������°������У�
				// ���һ��Ԫ�ؼ�Ϊ���°汾������㲿������
				Iterator iterator = coll.iterator();
				while (iterator.hasNext()) {
					part = (QMPartInfo) iterator.next();
					//CCBegin SS3
//					if (part.getViewName() != null
//							&& part.getViewName().trim().equals("������ͼ")) {
//						break;
//					}
					if (part.getViewName() != null
							&& part.getViewName().trim().equals("���������ͼ")) {
						break;
					}
					//CCEnd SS3
				}

				if (part == null) {
					String msg = "at save PartDescribeLink �㲿��(partNum="
							+ partNum + ") is null ";
					errorLog(msg);
					result.failureOne();
					transaction.rollback();
					continue;
				}

				doc = PublishHelper.getDocInfoByNumber(docNum);

				if (doc == null) {
					String msg = "at save PartDescribeLink �ĵ�(docNum=" + docNum
							+ ") is null ";
					errorLog(msg);
					result.failureOne();
					transaction.rollback();
					continue;
				}

				// ����Ƿ����������ϵ
				if (PublishHelper.hasPartDocDesc(part, doc)) {
					result.failureOne();
					String msg = "at save PartDescribeLink (partNum=" + partNum
							+ " docNum=" + docNum + " ) ERROR:���������Ѿ�����,����";
					transaction.rollback();
					errorLog(msg);
					continue;
				}

				PartDescribeLinkInfo describeLink = new PartDescribeLinkInfo();
				describeLink.setRightBsoID(doc.getBsoID());
				describeLink.setLeftBsoID(part.getBsoID());
				PartDescribeLinkInfo link = (PartDescribeLinkInfo) ser
						.savePartDescribeLink(describeLink);
				log("�����㲿����" + partNum + "�����ĵ���" + docNum + "����������ϵ�ɹ�");
				userLog(partNum + "," + docNum + "," + "�ɹ�");
				result.successOne();
				// String successMsg =
				// partNum+","+docNum+","+doc.getDocName()+","+doc.getVersionValue()+","+doc.getDocCfName()+",�ɹ�";
				String successMsg = partNum + "," + docNum + ","
						+ doc.getDocName() + "," + doc.getVersionValue() + ","
						+ doc.getDocCfName() + ",�ɹ�";
				result.addSuccessMsg(successMsg);
				transaction.commit();
			} catch (QMException ex) {
				transaction.rollback();
				result.failureOne();
				String msg = "at save PartDescribeLink (partNum=" + partNum
						+ " docNum=" + docNum + ") ERROR:"
						+ ex.getLocalizedMessage();

				errorLog(msg);
				errorLog(ex);
				userLog(partNum + "," + docNum + "," + "failure" + ","
						+ ex.getClientMessage());
				String errorMsg = partNum + "," + docNum + "," + doc.getName()
						+ "," + doc.getVersionValue() + ","
						+ doc.getDocCfName() + ",ʧ��";
				result.addSuccessMsg(errorMsg);
				// ex.printStackTrace();
			} catch (Exception ex) {
				transaction.rollback();
				result.failureOne();
				String msg = "at save PartDescribeLink (partNum=" + partNum
						+ " docNum=" + docNum + ") ERROR:"
						+ ex.getLocalizedMessage();

				errorLog(msg);
				errorLog(ex);
				userLog(partNum + "," + docNum + "," + "failure" + ","
						+ ex.getLocalizedMessage());
				String errorMsg = partNum + "," + docNum + "," + doc.getName()
						+ "," + doc.getVersionValue() + ","
						+ doc.getDocCfName() + ",ʧ��";
				result.addSuccessMsg(errorMsg);
				// ex.printStackTrace();
			}

		}
		return result;
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	/**
	 * ���������������㲿���滻������
	 *
	 * @param links
	 *            Group
	 * @param locale
	 *            Locale
	 * @return ResultReport
	 * @author liunan 2008-12-01
	 */
	public ResultReport savePartAlternatesLinks(Group links) throws QMException {
		ResultReport result = new ResultReport();
		userLog("##AlternatesLinks");
		if (links == null || links.getElementCount() == 0) {
			log("û����Ҫ�������㲿���滻������");
			return result;
		}
		Enumeration enumeration = links.getElements();
		Element ele = null;
		String part = null;//�㲿��
                String alternate = null;//�滻��
		QMTransaction transaction = null;
		log("�㲿���滻��������Ϣ:");
		while (enumeration.hasMoreElements()) {
			this.myHelper.altzong++;
			ele = (Element) enumeration.nextElement();
			part = ((String) ele.getValue("partNum")).trim();
			alternate = ((String) ele.getValue("AlternatNum")).trim();
			System.out.println(part+"=========================================="+alternate);
			// ����Ǵ���ָ���
			if (this.myHelper instanceof PublishErrorProcessor)
                        {
				if (this.myHelper.faileAlternate.contains(new Object[]{part, alternate}))
                                {
					continue;
				}
			}
			try {
				transaction = new QMTransaction();
				transaction.begin();
				QMPartMasterInfo alternatePart = (QMPartMasterInfo)PublishHelper.getPartMasterByNumber(alternate);
                                QMPartMasterInfo partMaster = (QMPartMasterInfo)PublishHelper.getPartMasterByNumber(part);
				if (alternatePart == null)
                                {
					String msg = "at save AlternatesLink �㲿��(part):" + part +"�㲿��(alternatePart):" + alternate + "�滻��������";
                                        this.myHelper.faileAlternate.add(new Object[]{part, alternate});
					result.failureOne();
					userLog(part + "," + alternate + ",ʧ��,�滻��" + alternate + "������");
					transaction.rollback();
					errorLog(msg);
					continue;
				}
				if (partMaster == null)
                                {
					String msg = "at save AlternatesLink �㲿��(part):" + part +"�㲿��(alternatePart):" + alternate + "ԭ�㲿��������";
                                        this.myHelper.faileAlternate.add(new Object[]{part, alternate});
					result.failureOne();
					userLog(part + "," + alternate + ",ʧ��,ԭ�㲿����" + part + "������");
					transaction.rollback();
					errorLog(msg);
					continue;
				}
                                //System.out.println(alternatePart+"=========================================="+partMaster);
				PersistService ps = (PersistService) PublishHelper.getEJBService("PersistService");
                                //���ýṹ�滻�����Ƿ���ڣ������򲻴������򴴽���
                                Collection collAlternates = ps.findLinkValueInfo("PartAlternateLink", alternatePart.getBsoID(), "alternates", partMaster.getBsoID());
                                if(collAlternates != null && collAlternates.size()>0)
                                {
                                	userLog(part + "," + alternate + ",�Ѵ���");
                                	log("�㲿�� " + part + " ���滻�� " + alternate + " �Ĺ����Ѵ���");
					                        transaction.rollback();
                                  continue;
                                }

				PartAlternateLinkInfo alternateLink = new PartAlternateLinkInfo();
                                alternateLink.setLeftBsoID(alternatePart.getBsoID());
                                alternateLink.setRightBsoID(partMaster.getBsoID());
				ps.saveValueInfo(alternateLink);
				userLog(part + "," + alternate + ",�ɹ�");
				log("�����㲿�� " + part + " ���滻�� " + alternate + " �����ɹ�");
				result.successOne();
				transaction.commit();
				this.myHelper.altnum++;
			} catch (QMException ex) {
				ex.printStackTrace();
				transaction.rollback();
				result.failureOne();
				String msg = "at save AlternatesLink (part=" + part + " alternate=" + alternate
						+ ") ERROR:" + ex.getClientMessage();
				errorLog(msg);
				errorLog(ex);
                                this.myHelper.faileAlternate.add(new Object[]{part, alternate});
				userLog(part + "," + alternate + "," + "ʧ��" + "," + ex.getClientMessage());
				continue;
			} catch (Exception ex) {
				ex.printStackTrace();
				transaction.rollback();
				result.failureOne();
				String msg = "at save AlternatesLink (part=" + part + " alternate=" + alternate
						+ ") ERROR:" + ex.getLocalizedMessage();
				errorLog(msg);
				errorLog(ex);
                                this.myHelper.faileAlternate.add(new Object[]{part, alternate});
				userLog(part + "," + alternate + "," + "ʧ��" + "," + ex.getLocalizedMessage());
				continue;
			}
		}
		return result;
	}

	/**
	 * ���������������㲿���ṹ�滻������
	 *
	 * @param links
	 *            Group
	 * @param locale
	 *            Locale
	 * @return ResultReport
	 * @author liunan 2008-12-01
	 */
	public ResultReport savePartSubtitutesLinks(Group links) throws QMException {
		ResultReport result = new ResultReport();
                userLog("##SubtitutesLinks");
		if (links == null || links.getElementCount() == 0) {
			log("û����Ҫ�������㲿���ṹ�滻������");
			return result;
		}
		Enumeration enumeration = links.getElements();
                Element ele = null;
                String parentPart = null;  //����
                String version = null;  //�����汾�����еİ汾
		String part = null;  //�㲿�����Ӽ���
                String substitutes = null;  //�ṹ�滻��
		QMTransaction transaction = null;
		log("�㲿���ṹ�滻��������Ϣ:");
		while (enumeration.hasMoreElements()) {
			this.myHelper.subzong++;
			ele = (Element) enumeration.nextElement();
			//System.out.println(ele.getValue("parentNum")+"======================"+ele.getValue("childNum")+"===================="+ele.getValue("parentVersion")+"===================="+ele.getValue("substituteNum"));
			parentPart = ((String) ele.getValue("parentNum")).trim();
                        part = ((String) ele.getValue("childNum")).trim();
                        version = ((String) ele.getValue("parentVersion")).trim();
                        substitutes = ((String) ele.getValue("substituteNum")).trim();

			// ����Ǵ���ָ���
			if (this.myHelper instanceof PublishErrorProcessor)
                        {
				if (this.myHelper.faileSubstitutes.contains(new Object[]{parentPart, part,version, substitutes}))
                                {
					continue;
				}
			}
			try {
				transaction = new QMTransaction();
				transaction.begin();

                                QMPartMasterInfo partMaster = (QMPartMasterInfo)PublishHelper.getPartMasterByNumber(part);  //�Ӽ�
                                QMPartInfo parentPartIfc = PublishHelper.getPartInfoByOrigInfo(parentPart, version);  //����
				QMPartMasterInfo substitutesPart = (QMPartMasterInfo)PublishHelper.getPartMasterByNumber(substitutes);  //�ṹ�滻��
				//System.out.println(partMaster+"$$$$$$$$$$$$$$$$"+parentPartIfc+"$$$$$$$$$$$$$$$"+substitutesPart);
				if (parentPartIfc == null || partMaster == null || substitutesPart == null)
                                {
					String msg = "at save SubstitutesLink ����(parentPart):" + parentPart +"�Ӽ�(part):" + part + "�ṹ�滻��(substitutes)"+substitutes;
                                        this.myHelper.faileSubstitutes.add(new Object[]{parentPart, part,version, substitutes});
					result.failureOne();
					transaction.rollback();
					errorLog(msg);
					continue;
				}
                                PersistService ps = (PersistService) PublishHelper.getEJBService("PersistService");
                                Collection coll = ps.findLinkValueInfo("PartUsageLink", partMaster.getBsoID(), "uses", parentPartIfc.getBsoID());
                                PartUsageLinkInfo usagelink = null;
                                if(coll != null && coll.size() > 0)
                                {
                                  Iterator iterator = coll.iterator();
                                  if(iterator.hasNext())
                                  {
                                    usagelink = (PartUsageLinkInfo)iterator.next();
                                  }
                                }
                                if(usagelink == null)
                                {
                                        String msg = "at save SubstitutesLink ����(parentPart):" + parentPart +"�Ӽ�(part):" + part + "�ṹ�滻��(substitutes)"+substitutes;
                                        this.myHelper.faileSubstitutes.add(new Object[]{parentPart, part,version, substitutes});
                                        result.failureOne();
                                        transaction.rollback();
                                        errorLog(msg);
                                        continue;
                                }
                                //���ýṹ�滻�����Ƿ���ڣ������򲻴������򴴽���
                                Collection collSubstitutes = ps.findLinkValueInfo("PartSubstituteLink", substitutesPart.getBsoID(), "substitutes", usagelink.getBsoID());
                                if(collSubstitutes != null && collSubstitutes.size()>0)
                                {
                                	userLog(parentPart + "," + version + "," + part + "," + substitutes + ",�Ѵ���");
                                	log("�㲿�� " + part + " ��ṹ�滻�� " + substitutes + "�ڽṹ" + parentPart + "(" + version + ") �滻�����Ѵ���");
                                	transaction.rollback();
                                  continue;
                                }

				PartSubstituteLinkInfo substitutesLink = new PartSubstituteLinkInfo();
                                substitutesLink.setLeftBsoID(substitutesPart.getBsoID());
                                substitutesLink.setRightBsoID(usagelink.getBsoID());

				ps.saveValueInfo(substitutesLink);
				userLog(parentPart + "," + version + "," + part + "," + substitutes + ",�ɹ�");
				log("�����㲿�� " + part + " ��ṹ�滻�� " + substitutes + "�ڽṹ" + parentPart + "(" + version + ") �����ɹ�");
				result.successOne();
				transaction.commit();
				this.myHelper.subnum++;
			} catch (QMException ex) {
				ex.printStackTrace();
				transaction.rollback();
				result.failureOne();
				String msg = "at save SubstitutessLink (part=" + part + " substitutes=" + substitutes
                                                + "�ڽṹ" + parentPart + "(" + version + ")"
						+ ") ERROR:" + ex.getClientMessage();
				errorLog(msg);
				errorLog(ex);
                                this.myHelper.faileSubstitutes.add(new Object[]{parentPart, part,version, substitutes});
                                userLog(part + "," + substitutes + "," + "�ڽṹ" + parentPart + "(" + version + ")," + "ʧ��" + "," + ex.getClientMessage());
                                userLog(parentPart + "," + version + "," + part + "," + substitutes + ",ʧ��" + "," + ex.getClientMessage());
				continue;
			} catch (Exception ex) {
				ex.printStackTrace();
				transaction.rollback();
				result.failureOne();
                                String msg = "at save SubstitutessLink (part=" + part + " substitutes=" + substitutes
                                                + "�ڽṹ" + parentPart + "(" + version + ")"
						+ ") ERROR:" + ex.getLocalizedMessage();
				errorLog(msg);
				errorLog(ex);
                                this.myHelper.faileSubstitutes.add(new Object[]{parentPart, part,version, substitutes});
				                        userLog(part + "," + substitutes + "," + "�ڽṹ" + parentPart + "(" + version + ")," + "ʧ��" + "," + ex.getLocalizedMessage());
				continue;
			}
		}
		return result;
	}
	

}
