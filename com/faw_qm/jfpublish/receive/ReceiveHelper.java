package com.faw_qm.jfpublish.receive;
//SS1 技术中心发零部件发布到中心设计视图 刘家坤 2014-7-8
import java.util.Collection;
import java.util.Iterator;

import com.faw_qm.integration.model.Element;
import com.faw_qm.integration.model.Group;
import com.faw_qm.integration.model.Att;
import com.faw_qm.jfpublish.receive.RelationsDelegate;
import com.faw_qm.version.ejb.service.VersionControlService;
import com.faw_qm.framework.exceptions.QMException;
import com.faw_qm.framework.service.QMTransaction;
import com.faw_qm.doc.model.DocInfo;
import com.faw_qm.epm.build.model.EPMBuildHistoryInfo;
import com.faw_qm.epm.build.model.EPMBuildLinksRuleInfo;
import com.faw_qm.epm.epmdocument.model.EPMDocumentInfo;
import com.faw_qm.part.model.QMPartIfc;
import com.faw_qm.part.model.QMPartInfo;
import com.faw_qm.part.model.QMPartMasterIfc;
import com.faw_qm.part.model.QMPartMasterInfo;
import com.faw_qm.persist.ejb.service.PersistService;


public class ReceiveHelper 
{
	public String saveEPMDocPartLinks(String partNum, String docNum) throws QMException
	{
		System.out.println("##ReceiveHelper.saveEPMDocPartLinks");

		VersionControlService versiobSer = (VersionControlService) PublishHelper.getEJBService("VersionControlService");
		QMTransaction transaction = null;
		EPMDocumentInfo doc = null;
		String docType = null;
			try {
				transaction = new QMTransaction();
				transaction.begin();

				QMPartMasterIfc partMaster = PublishHelper
						.getPartMasterByNumber(partNum);
				if (partMaster == null) {
					System.out.println("at save 零部件与EPM文档关联 零部件(partNum=" + partNum + ") is null ");
					transaction.rollback();
					return "at save 零部件与EPM文档关联 零部件(partNum=" + partNum + ") is null ";
				}
				QMPartInfo part = null;
				Collection coll = (Collection) versiobSer.allVersionsOf(partMaster);
				// 获得master下的所有小版本，按最新版序排列，
				// 则第一个元素即为最新版本版序的零部件对象
				Iterator iterator = coll.iterator();
				while (iterator.hasNext()) 
				{
					part = (QMPartInfo) iterator.next();
					//CCBegin SS1
					//if (part.getViewName() != null && part.getViewName().trim().equals("工程视图")) 
					if (part.getViewName() != null && part.getViewName().trim().equals("中心设计视图"))
						//CCEnd SS1
					{
						break;
					}
				}

				if (part == null) {
					System.out.println("at save 零部件与EPM文档关联 零部件(partNum=" + partNum + ") is null ");
					transaction.rollback();
					return "at save 零部件与EPM文档关联 零部件(partNum=" + partNum + ") is null ";
				}

				doc = PublishHelper.getEPMDocInfoByNumber(docNum);

				if (doc == null) {
					System.out.println("at save 零部件与EPM文档关联 EPM文档(docNum=" + docNum + ") is null ");
					transaction.rollback();
					return "at save 零部件与EPM文档关联 EPM文档(docNum=" + docNum + ") is null ";
				}
				docType = doc.getAuthoringApplication().getDisplay();
				// 检查是否存在关联关系
				//CCBegin by liunan 2009-11-09 改成通过EPMBuildHistory表来查找关联是否存在。
				//源代码
				//if (PublishHelper.hasBuildLinkRule(part, doc)) {
				if (PublishHelper.hasBuildHistory(part, doc)) 
				{
					System.out.println("at save 零部件与EPM文档关联 (partNum=" + partNum + " docNum=" + docNum + " ) ERROR:关联已经存在,跳过");
					transaction.rollback();
					return "at save 零部件与EPM文档关联 (partNum=" + partNum + " docNum=" + docNum + " ) ERROR:关联已经存在,跳过";
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
				transaction.commit();
				System.out.println("创建EPM文档（" + docNum + "）与零部件（" + partNum + "）的描述关系成功");
				return "创建EPM文档（" + docNum + "）与零部件（" + partNum + "）的描述关系成功";

			} catch (QMException ex) {
				System.out.println("at save PartDescribeLink (partNum=" + partNum
						+ " docNum=" + docNum + ") ERROR:"
						+ ex.getLocalizedMessage());
				transaction.rollback();
				return "at save PartDescribeLink (partNum=" + partNum
						+ " docNum=" + docNum + ") ERROR:"
						+ ex.getLocalizedMessage();
			} catch (Exception ex) {
				System.out.println("at save PartDescribeLink (partNum=" + partNum
						+ " docNum=" + docNum + ") ERROR:"
						+ ex.getLocalizedMessage());
				transaction.rollback();
				return "at save PartDescribeLink (partNum=" + partNum
						+ " docNum=" + docNum + ") ERROR:"
						+ ex.getLocalizedMessage();
			}
	}
}
