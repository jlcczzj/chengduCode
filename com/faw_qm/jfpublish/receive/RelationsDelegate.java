/**
 * SS1 解决结构重复问题，现在不再发布全结构，也不再需要处理孤儿件问题，所有取消对关联是否存在的判断。 liunan 2013-9-5
 * SS2 修改日志显示。 liunan 2014-4-14
 * SS3 技术中心发零部件发布到中心设计视图 刘家坤 2014-7-8
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
	 * 创建发布过来的零部件结构（新版－全状态发布后的修改）。
	 * 主要用于发布所有结构，避免孤儿结构的出现。
	 * 总体思路：
	 * 1、父件是发布的零部件，其关联全部新建。
	 * 2、父件不是发布的零部件，添加其关联时首先检查关联是否存在，无则新建关联。
	 * 具体处理：
	 * 1、从新建零部件和修订零部件两个集合中提取出本次发布的零部件集合。
	 * 2、创建关联时如果父件在发布零部件的集合中则直接创建，否则检查关联是否存在，不存在才创建。
	 * @param links
	 *            Group
	 * @return ResultReport
	 */
	public ResultReport savePartUsageLinks(Group links) throws QMException {
		ResultReport result = new ResultReport();
		userLog("##UsageLink");
		if (links == null || links.getElementCount() == 0) {
			log("没有需要创建的零部件结构关联");
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
		log("零部件结构关联信息:");

		//anan
		System.out.println("发布件集合的个数是："+this.myHelper.partVector.size());
		System.out.println("发布件集合的内容是："+this.myHelper.partVector);
				
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
			// 如果是错误恢复，
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
				//CCBegin by liunan 2008-12-19 去掉对视图的判断，视图在获得父件的时候先做判断。
				//源码如下:
				/*if (parentPart == null || parentPart.getViewName() == null
						|| !(parentPart.getViewName().trim().equals("工程视图"))) {*/
				if (parentPart == null || parentPart.getViewName() == null) {
				//CCEnd by liunan 2008-12-19
					String msg = "at save PartUsageLink 零部件(parent):" + parent
							+ ",原版本：" + parentVersion + "is null ";
					result.failureOne();
					transaction.rollback();
					errorLog(msg);
					continue;
				}

				QMPartMasterInfo childMaster = null;
				QMPartIfc childPart = PublishHelper.getPartInfoByNumber(child);
				if (childPart == null) {
					String msg = "at save PartUsageLink 子零部件(child=" + child
							+ ") is null ";
					result.failureOne();
					transaction.rollback();
					errorLog(msg);

					continue;

				}
				childMaster = (QMPartMasterInfo) childPart.getMaster();
				//CCBegin by liunan 2008-12-18 添加关联是否存在的判断。
				//以前由于都是发布零部件的关联才发布过来，所以所有过来的关联都是需要新建的，
				//现在全状态发布，并且是发布所有关联，所有要判断关联是否存在，把以前的孤儿关联添加到系统中。
				PersistService ps = (PersistService) PublishHelper.getEJBService("PersistService");
				//CCBegin SS1
				//Collection linkColl = ps.findLinkValueInfo("PartUsageLink", childMaster.getBsoID(), "uses", parentPart.getBsoID());
				//CCEnd SS1
				//CCBegin by liunan 2008-12-22 判断当前关联的父件是否是需要发布的零部件，即是否在发布件集合中。
				//System.out.println("发布件集合的个数是："+this.myHelper.partVector.size());
				//System.out.println("发布件集合的内容是："+this.myHelper.partVector);
				if(!this.myHelper.partVector.contains(parent))
				{
				  //CCBegin SS1
				  //if(linkColl != null && linkColl.size() > 0)
				  //CCEnd SS1
				  {
				  	//System.out.println(childMaster.getBsoID()+"==============="+parentPart.getBsoID());
			    	//userLog(parent + "," + child + ",已存在");
				    log("零部件 " + parent + " （父）与零部件 " + child + " （子）的结构关联已存在");
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
				// 设置默认单位(使用方式)
				if (unit == null || unit.trim().length() == 0) {
					usageLink.setDefaultUnit(Unit.getUnitDefault());
				} else {
					// 读取属性文件，获得默认单位（使用方式）对应值
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
				userLog(parent + "," + child + ",成功");
				log("创建零部件 " + parent + " （父）与零部件 " + child + " （子）的结构关联成功");
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
				userLog(parent + "," + child + "," + "失败" + ","
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
				userLog(parent + "," + child + "," + "失败" + ","
						+ ex.getLocalizedMessage());
				continue;
			}
		}
		return result;
	}

	/**
	 * 保存零部件与文档的关联关系
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
			log("没有需要创建的零部件描述信息");
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
		log("零部件描述关联信息:");
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
					String msg = "at save PartDescribeLink 零部件(partNum="
							+ partNum + ") is null ";
					errorLog(msg);
					result.failureOne();
					transaction.rollback();
					continue;
				}
				QMPartInfo part = null;
				Collection coll = (Collection) versiobSer
						.allVersionsOf(partMaster);
				// 获得master下的所有小版本，按最新版序排列，
				// 则第一个元素即为最新版本版序的零部件对象
				Iterator iterator = coll.iterator();
				while (iterator.hasNext()) {
					part = (QMPartInfo) iterator.next();
					//CCBegin SS3
//					if (part.getViewName() != null
//							&& part.getViewName().trim().equals("工程视图")) {
//						break;
//					}
					if (part.getViewName() != null
							&& part.getViewName().trim().equals("中心设计视图")) {
						break;
					}
					//CCEnd SS3
				}

				if (part == null) {
					String msg = "at save PartDescribeLink 零部件(partNum="
							+ partNum + ") is null ";
					errorLog(msg);
					result.failureOne();
					transaction.rollback();
					continue;
				}

				doc = PublishHelper.getDocInfoByNumber(docNum);

				if (doc == null) {
					String msg = "at save PartDescribeLink 文档(docNum=" + docNum
							+ ") is null ";
					errorLog(msg);
					result.failureOne();
					transaction.rollback();
					continue;
				}

				// 检查是否存在描述关系
				if (PublishHelper.hasPartDocDesc(part, doc)) {
					result.failureOne();
					String msg = "at save PartDescribeLink (partNum=" + partNum
							+ " docNum=" + docNum + " ) ERROR:描述关联已经存在,跳过";
					transaction.rollback();
					errorLog(msg);
					continue;
				}

				PartDescribeLinkInfo describeLink = new PartDescribeLinkInfo();
				describeLink.setRightBsoID(doc.getBsoID());
				describeLink.setLeftBsoID(part.getBsoID());
				PartDescribeLinkInfo link = (PartDescribeLinkInfo) ser
						.savePartDescribeLink(describeLink);
				log("创建零部件（" + partNum + "）与文档（" + docNum + "）的描述关系成功");
				userLog(partNum + "," + docNum + "," + "成功");
				result.successOne();
				// String successMsg =
				// partNum+","+docNum+","+doc.getDocName()+","+doc.getVersionValue()+","+doc.getDocCfName()+",成功";
				String successMsg = partNum + "," + docNum + ","
						+ doc.getDocName() + "," + doc.getVersionValue() + ","
						+ doc.getDocCfName() + ",成功";
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
						+ doc.getDocCfName() + ",失败";
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
						+ doc.getDocCfName() + ",失败";
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
	 * 创建发布过来的零部件替换件关联
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
			log("没有需要创建的零部件替换件关联");
			return result;
		}
		Enumeration enumeration = links.getElements();
		Element ele = null;
		String part = null;//零部件
                String alternate = null;//替换件
		QMTransaction transaction = null;
		log("零部件替换件关联信息:");
		while (enumeration.hasMoreElements()) {
			this.myHelper.altzong++;
			ele = (Element) enumeration.nextElement();
			part = ((String) ele.getValue("partNum")).trim();
			alternate = ((String) ele.getValue("AlternatNum")).trim();
			System.out.println(part+"=========================================="+alternate);
			// 如果是错误恢复，
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
					String msg = "at save AlternatesLink 零部件(part):" + part +"零部件(alternatePart):" + alternate + "替换件不存在";
                                        this.myHelper.faileAlternate.add(new Object[]{part, alternate});
					result.failureOne();
					userLog(part + "," + alternate + ",失败,替换件" + alternate + "不存在");
					transaction.rollback();
					errorLog(msg);
					continue;
				}
				if (partMaster == null)
                                {
					String msg = "at save AlternatesLink 零部件(part):" + part +"零部件(alternatePart):" + alternate + "原零部件不存在";
                                        this.myHelper.faileAlternate.add(new Object[]{part, alternate});
					result.failureOne();
					userLog(part + "," + alternate + ",失败,原零部件件" + part + "不存在");
					transaction.rollback();
					errorLog(msg);
					continue;
				}
                                //System.out.println(alternatePart+"=========================================="+partMaster);
				PersistService ps = (PersistService) PublishHelper.getEJBService("PersistService");
                                //检查该结构替换关联是否存在，存在则不处理，否则创建。
                                Collection collAlternates = ps.findLinkValueInfo("PartAlternateLink", alternatePart.getBsoID(), "alternates", partMaster.getBsoID());
                                if(collAlternates != null && collAlternates.size()>0)
                                {
                                	userLog(part + "," + alternate + ",已存在");
                                	log("零部件 " + part + " 与替换件 " + alternate + " 的关联已存在");
					                        transaction.rollback();
                                  continue;
                                }

				PartAlternateLinkInfo alternateLink = new PartAlternateLinkInfo();
                                alternateLink.setLeftBsoID(alternatePart.getBsoID());
                                alternateLink.setRightBsoID(partMaster.getBsoID());
				ps.saveValueInfo(alternateLink);
				userLog(part + "," + alternate + ",成功");
				log("创建零部件 " + part + " 与替换件 " + alternate + " 关联成功");
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
				userLog(part + "," + alternate + "," + "失败" + "," + ex.getClientMessage());
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
				userLog(part + "," + alternate + "," + "失败" + "," + ex.getLocalizedMessage());
				continue;
			}
		}
		return result;
	}

	/**
	 * 创建发布过来的零部件结构替换件关联
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
			log("没有需要创建的零部件结构替换件关联");
			return result;
		}
		Enumeration enumeration = links.getElements();
                Element ele = null;
                String parentPart = null;  //父件
                String version = null;  //父件版本，汽研的版本
		String part = null;  //零部件（子件）
                String substitutes = null;  //结构替换件
		QMTransaction transaction = null;
		log("零部件结构替换件关联信息:");
		while (enumeration.hasMoreElements()) {
			this.myHelper.subzong++;
			ele = (Element) enumeration.nextElement();
			//System.out.println(ele.getValue("parentNum")+"======================"+ele.getValue("childNum")+"===================="+ele.getValue("parentVersion")+"===================="+ele.getValue("substituteNum"));
			parentPart = ((String) ele.getValue("parentNum")).trim();
                        part = ((String) ele.getValue("childNum")).trim();
                        version = ((String) ele.getValue("parentVersion")).trim();
                        substitutes = ((String) ele.getValue("substituteNum")).trim();

			// 如果是错误恢复，
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

                                QMPartMasterInfo partMaster = (QMPartMasterInfo)PublishHelper.getPartMasterByNumber(part);  //子件
                                QMPartInfo parentPartIfc = PublishHelper.getPartInfoByOrigInfo(parentPart, version);  //父件
				QMPartMasterInfo substitutesPart = (QMPartMasterInfo)PublishHelper.getPartMasterByNumber(substitutes);  //结构替换件
				//System.out.println(partMaster+"$$$$$$$$$$$$$$$$"+parentPartIfc+"$$$$$$$$$$$$$$$"+substitutesPart);
				if (parentPartIfc == null || partMaster == null || substitutesPart == null)
                                {
					String msg = "at save SubstitutesLink 父件(parentPart):" + parentPart +"子件(part):" + part + "结构替换件(substitutes)"+substitutes;
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
                                        String msg = "at save SubstitutesLink 父件(parentPart):" + parentPart +"子件(part):" + part + "结构替换件(substitutes)"+substitutes;
                                        this.myHelper.faileSubstitutes.add(new Object[]{parentPart, part,version, substitutes});
                                        result.failureOne();
                                        transaction.rollback();
                                        errorLog(msg);
                                        continue;
                                }
                                //检查该结构替换关联是否存在，存在则不处理，否则创建。
                                Collection collSubstitutes = ps.findLinkValueInfo("PartSubstituteLink", substitutesPart.getBsoID(), "substitutes", usagelink.getBsoID());
                                if(collSubstitutes != null && collSubstitutes.size()>0)
                                {
                                	userLog(parentPart + "," + version + "," + part + "," + substitutes + ",已存在");
                                	log("零部件 " + part + " 与结构替换件 " + substitutes + "在结构" + parentPart + "(" + version + ") 替换关联已存在");
                                	transaction.rollback();
                                  continue;
                                }

				PartSubstituteLinkInfo substitutesLink = new PartSubstituteLinkInfo();
                                substitutesLink.setLeftBsoID(substitutesPart.getBsoID());
                                substitutesLink.setRightBsoID(usagelink.getBsoID());

				ps.saveValueInfo(substitutesLink);
				userLog(parentPart + "," + version + "," + part + "," + substitutes + ",成功");
				log("创建零部件 " + part + " 与结构替换件 " + substitutes + "在结构" + parentPart + "(" + version + ") 关联成功");
				result.successOne();
				transaction.commit();
				this.myHelper.subnum++;
			} catch (QMException ex) {
				ex.printStackTrace();
				transaction.rollback();
				result.failureOne();
				String msg = "at save SubstitutessLink (part=" + part + " substitutes=" + substitutes
                                                + "在结构" + parentPart + "(" + version + ")"
						+ ") ERROR:" + ex.getClientMessage();
				errorLog(msg);
				errorLog(ex);
                                this.myHelper.faileSubstitutes.add(new Object[]{parentPart, part,version, substitutes});
                                userLog(part + "," + substitutes + "," + "在结构" + parentPart + "(" + version + ")," + "失败" + "," + ex.getClientMessage());
                                userLog(parentPart + "," + version + "," + part + "," + substitutes + ",失败" + "," + ex.getClientMessage());
				continue;
			} catch (Exception ex) {
				ex.printStackTrace();
				transaction.rollback();
				result.failureOne();
                                String msg = "at save SubstitutessLink (part=" + part + " substitutes=" + substitutes
                                                + "在结构" + parentPart + "(" + version + ")"
						+ ") ERROR:" + ex.getLocalizedMessage();
				errorLog(msg);
				errorLog(ex);
                                this.myHelper.faileSubstitutes.add(new Object[]{parentPart, part,version, substitutes});
				                        userLog(part + "," + substitutes + "," + "在结构" + parentPart + "(" + version + ")," + "失败" + "," + ex.getLocalizedMessage());
				continue;
			}
		}
		return result;
	}
	

}
